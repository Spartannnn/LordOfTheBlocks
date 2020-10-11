package lotb.entities.npc;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Dynamic;
import lotb.LotbMod;
import lotb.entities.ai.ModMemories;
import lotb.entities.ai.ModSensors;
import lotb.entities.npc.profesion.IProfession;
import lotb.entities.npc.relationship.NPCRelationShipManager;
import lotb.entities.npc.relationship.RelationShip;
import lotb.lore.Faction;
import lotb.util.ModInventoryUtils;
import lotb.util.TCHelper;
import lotb.world.capabilities.CapabilityAccessor;
import lotb.world.capabilities.relationship.IPlayerNpcRelationShip;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTables;

import javax.annotation.Nullable;
import java.util.Arrays;

public class AbstractNPCEntity extends CreatureEntity implements INPC, IRangedAttackMob, ICrossbowUser {

    private static final DataParameter<Boolean> DATA_CHARGING_STATE = EntityDataManager.createKey(AbstractNPCEntity.class, DataSerializers.BOOLEAN);
    private static final ImmutableList<SensorType<? extends Sensor<? super AbstractNPCEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, SensorType.INTERACTABLE_DOORS, ModSensors.NEARBY_HOSTILES);
    private static final ImmutableList<MemoryModuleType<?>> MEMORIES = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.PATH, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.INTERACTABLE_DOORS, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, ModMemories.ATTACK_TARGET, ModMemories.NEEDS_FOOD);

    private float equipmentDropChance;
    private Faction faction;
    private final NPCInventory npcInventory = new NPCInventory();
    public boolean weildingOffHand;
    private NPCFoodManager foodManager;
    private NPCRelationShipManager npcRelationShipManager;
    private final String realName;
    private IProfession profession;

    protected AbstractNPCEntity(EntityType<? extends CreatureEntity> type, World worldIn, String realName, IProfession profession) {
        super(type, worldIn);
        this.equipmentDropChance = 0.15F * (4 - world.getDifficulty().getId());
        GroundPathNavigator gpn = (GroundPathNavigator) this.getNavigator();
        gpn.setBreakDoors(true);
        gpn.setCanSwim(true);
        setCanPickUpLoot(true);
        this.profession = profession;
        resetBrain((ServerWorld) worldIn);
        this.faction = Faction.GONDOR;
        Arrays.fill(this.inventoryArmorDropChances, this.equipmentDropChance / 2);
        Arrays.fill(this.inventoryHandsDropChances, this.equipmentDropChance);
        this.foodManager = new NPCFoodManager();
        this.npcRelationShipManager = new NPCRelationShipManager();
        this.realName = realName;
        if (!worldIn.isRemote)
            this.resetBrain((ServerWorld) worldIn);
        //TODO removing later
        boolean b = this.npcInventory.addItem(new ItemStack(Items.WHITE_BED));
        LotbMod.LOGGER.debug("Bed added? {}", b);
        this.setCustomNameVisible(true);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2);
        getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(0);
        getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0);
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.7);
        getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setEquipmentBasedOnDifficulty(difficultyIn);
        this.setEnchantmentBasedOnDifficulty(difficultyIn);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_CHARGING_STATE, false);
    }

    @Override
    protected Brain<?> createBrain(Dynamic<?> _past) {
        return new Brain<AbstractNPCEntity>(MEMORIES, SENSORS, _past);
    }

    @Override
    public Brain<AbstractNPCEntity> getBrain() {
        return (Brain<AbstractNPCEntity>) super.getBrain();
    }

    public NPCInventory getNpcInventory() {
        return npcInventory;
    }

    public void resetBrain(ServerWorld serverWorldIn) {
        Brain<AbstractNPCEntity> brain = getBrain();
        brain.stopAllTasks(serverWorldIn, this);
        this.brain = brain.copy();
        initBrain();
    }

    @SuppressWarnings("unchecked")
    public void initBrain() {
        brain = profession.registerActivitiesOntoBrain(this, (Brain<AbstractNPCEntity>) brain);
        brain.updateActivity(this.world.getDayTime(), this.world.getGameTime());
    }

    @Override
    protected boolean processInteract(PlayerEntity player, Hand hand) {
        if(profession.onRightClick(this, player.getHeldItem(hand), player, world) || super.processInteract(player, hand)) {
            brain.setMemory(MemoryModuleType.INTERACTION_TARGET, player);
            return true;
        }
        return false;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (isAlive()) {
            this.profession.tick(this, world);
        }
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            this.foodManager.tick(this);
        }
        super.tick();
    }

    @Override
    public void livingTick() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION)) {
            if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 == 0)
                this.heal(1.0F);

            if (this.foodManager.needFood() && this.ticksExisted % 10 == 0)
                this.foodManager.setFoodLevel(this.foodManager.getFoodLevel() + 1);
        }

        super.livingTick();
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        Entity attacker = cause.getTrueSource();
        if (attacker instanceof PlayerEntity) {
            IPlayerNpcRelationShip playerNpcRs = CapabilityAccessor.getPlayerNpcRelationShipCapability((PlayerEntity) attacker);
            if (playerNpcRs.hasRelationShip(this)) {
                playerNpcRs.changeRelationShip(this, RelationShip.nextBad(playerNpcRs.getRelationShip(this)));
            }
        } else if (attacker instanceof AbstractNPCEntity) {
            AbstractNPCEntity other = (AbstractNPCEntity) attacker;
            if (this.npcRelationShipManager.hasRelationShip(other))
                this.npcRelationShipManager.changeRelationShip(other, RelationShip.nextBad(npcRelationShipManager.getRelationShip(other)));
            else
                this.npcRelationShipManager.addRelationShip(other, RelationShip.BAD);
        }
        this.profession.kill(this, world);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        Entity attacker = source.getTrueSource();
        if (attacker instanceof PlayerEntity) {
            IPlayerNpcRelationShip playerNpcRs = CapabilityAccessor.getPlayerNpcRelationShipCapability((PlayerEntity) attacker);
            if (playerNpcRs.hasRelationShip(this)) {
                playerNpcRs.changeRelationShip(this, RelationShip.nextBad(playerNpcRs.getRelationShip(this)));
            }
            this.profession.onAttacked(this, (PlayerEntity) attacker, world);
        } else if (attacker instanceof AbstractNPCEntity) {
            AbstractNPCEntity other = (AbstractNPCEntity) attacker;
            if (this.npcRelationShipManager.hasRelationShip(other))
                this.npcRelationShipManager.changeRelationShip(other, RelationShip.nextBad(npcRelationShipManager.getRelationShip(other)));
            else
                this.npcRelationShipManager.addRelationShip(other, RelationShip.BAD);
        }
        LotbMod.LOGGER.debug("We are under attack");
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("NPCInventory", this.npcInventory.serializeNBT());
        ListNBT list = this.npcRelationShipManager.write();
        compound.put("NPCRelationShipToNPC", list);
        compound.putInt("NPCRelListType", list.getTagType());
        compound.putInt("Faction", this.faction.ordinal());
        this.foodManager.write(compound);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.npcInventory.deserializeNBT(compound);
        this.npcRelationShipManager = new NPCRelationShipManager();
        this.npcRelationShipManager.setRelationShips(NPCRelationShipManager.read(compound.getList("NPCRelationShipToNPC", compound.getInt("NPCRelListType"))));
        this.faction = Faction.VALUES[compound.getInt("Faction")];
        this.foodManager.read(compound);
    }

    @Override
    public ITextComponent getDisplayName() {
        StringTextComponent name = TCHelper.string("");
        name.appendSibling(new StringTextComponent("[" + TCHelper.normalizeEnum(faction) + "]").applyTextStyle(TextFormatting.RED));
        name.appendSibling(new StringTextComponent(" " + this.realName).applyTextStyle(TextFormatting.AQUA));
        return name;
    }

    public String getRealName() {
        return this.realName;
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);

        ResourceLocation lootTableRl = LootTables.CHESTS_VILLAGE_VILLAGE_BUTCHER;
        if (lootTableRl != null && !this.world.isRemote)
            ModInventoryUtils.fillInventoryWithLoot(world, rand, npcInventory, lootTableRl,
                    new LootContext.Builder((ServerWorld) world).withSeed(rand.nextLong()).withParameter(LootParameters.POSITION, getPosition())
                            .build(LootParameterSets.CHEST));

        boolean b = rand.nextBoolean();
        ItemStack[] bySlot = new ItemStack[]{
                b ? new ItemStack(Items.BOW) : new ItemStack(Items.IRON_SWORD),
                b ? new ItemStack(Items.ARROW, 32) : new ItemStack(Items.SHIELD),
                new ItemStack(Items.IRON_BOOTS),
                new ItemStack(Items.IRON_LEGGINGS),
                new ItemStack(Items.IRON_CHESTPLATE),
                new ItemStack(Items.IRON_HELMET)
        };
        for (int i = 0; i < 6; i++)
            if (i < 2 || rand.nextInt(3) == 0)
                setItemStackToSlot(EquipmentSlotType.values()[i], bySlot[i]);

    }

    public int getAttackSpeed() {
        return 15;
    }

    public int getArcherySpeed() {
        return 25;
    }

    public boolean canUseOffhand() {
        return false;
    }

    public float getArrowVelocity() {
        return 1.6f;
    }

    public float getArrowAccuracy() {
        return 14 - world.getDifficulty().getId() * 4;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_VILLAGER_TRADE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_VILLAGER_CELEBRATE, 0.15F, 1.0F);
    }

    public SoundEvent getTradeYesSound() {
        return SoundEvents.ENTITY_VILLAGER_YES;
    }

    @Override
    public void setCharging(boolean isCharging) {
        this.dataManager.set(DATA_CHARGING_STATE, isCharging);
    }

    public Faction getFaction() {
        return faction;
    }

    private Vector3f calculateBulletDirection(Vec3d direction, float angle) {
        Vec3d vec3d = direction.normalize();
        Vec3d vec3d1 = vec3d.crossProduct(new Vec3d(0.0D, 1.0D, 0.0D));
        if (vec3d1.lengthSquared() <= 1.0E-7D)
            vec3d1 = vec3d.crossProduct(this.getUpVector(1.0F));
        Quaternion quaternion = new Quaternion(new Vector3f(vec3d1), 90.0F, true);
        Vector3f vector3f = new Vector3f(vec3d);
        vector3f.transform(quaternion);
        Quaternion quaternion1 = new Quaternion(vector3f, angle, true);
        Vector3f vector3f1 = new Vector3f(vec3d);
        vector3f1.transform(quaternion1);
        return vector3f1;
    }


    @Override
    public void shoot(LivingEntity target, ItemStack p_213670_2_, IProjectile projectile, float projectileAngle) {
        Entity projectileEntity = (Entity) projectile;
        double xdist = target.getPosX() - getPosX();
        double zdist = target.getPosZ() - getPosZ();

        double disthyp = MathHelper.sqrt(xdist * xdist + zdist * zdist);
        double aimheight = target.getPosYHeight(0.3333333333333333D) - projectileEntity.getPosY() + disthyp * 0.2F;
        Vector3f direction = calculateBulletDirection(new Vec3d(xdist, aimheight, zdist), projectileAngle);
        projectile.shoot(direction.getX(), direction.getY(), direction.getZ(), getArrowVelocity(), getArrowAccuracy());
    }

    @Override
    protected void updateAITasks() {
        world.getProfiler().startSection("brain");
        getBrain().tick((ServerWorld) world, this);
        world.getProfiler().endSection();
        //super.updateAITasks();
    }

    public NPCFoodManager getFoodManager() {
        return foodManager;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        for (int i = 0; i < npcInventory.getSlots(); i++) {
            ItemStack stack = npcInventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                int count = stack.getCount() * (int) equipmentDropChance * looting + 1;
                stack.setCount(Math.min(rand.nextInt(count), count) + rand.nextInt(2));
            }
            entityDropItem(stack);
        }
    }


    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack bow = this.getHeldItem(Hand.MAIN_HAND);
        ItemStack arrow = this.getHeldItem(Hand.OFF_HAND);
        if (bow.getItem() instanceof CrossbowItem) {
            if (bow.isEmpty() || arrow.isEmpty())
                return;
            CrossbowItem.fireProjectiles(world, this, Hand.MAIN_HAND, bow, getArrowVelocity(), getArrowAccuracy());
        } else if (bow.getItem() instanceof BowItem) {
            AbstractArrowEntity arrowEntity = ProjectileHelper.fireArrow(this, arrow, distanceFactor);
            arrowEntity = ((BowItem) Items.BONE).customeArrow(arrowEntity);
            shoot(target, bow, arrowEntity, 0.0f);
            playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.addEntity(arrowEntity);
        }
    }

}
