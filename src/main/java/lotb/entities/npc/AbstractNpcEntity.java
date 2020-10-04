package lotb.entities.npc;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.util.Pair;

import lotb.LotbMod;
import lotb.entities.ai.brain.ModMemories;
import lotb.entities.ai.brain.ModSensors;
import lotb.entities.npc.profession.AbstractProfession;
import lotb.entities.npc.profession.ArcherProfession;
import lotb.lore.Faction;
import lotb.tools.ModInventoryUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.INPC;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class AbstractNpcEntity  extends CreatureEntity implements INPC, ICrossbowUser, IRangedAttackMob {
	//data
	private static final DataParameter<Boolean> DATA_CHARGING_STATE = EntityDataManager.createKey(PillagerEntity.class, DataSerializers.BOOLEAN);
	private final float EQUIPMENT_DROP_CHANCE;
	private Map<UUID, Pair<Integer,Byte>> PLAYER_RELATIONSHIPS = new HashMap<UUID,Pair<Integer,Byte>>();
	private AbstractProfession proffesion;
	private Faction faction;
	public final IItemHandler inventory = new ItemStackHandler(8);
	public boolean weildingOffhand = false;
	///ADD POSES
	
	//brain data
	private static final ImmutableList<SensorType<? extends Sensor<? super AbstractNpcEntity>>> SENSORS = ImmutableList.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.HURT_BY, 
			SensorType.INTERACTABLE_DOORS, 
			ModSensors.NEARBY_HOSTILES);
	private static final ImmutableList<MemoryModuleType<?>> MEMORIES = ImmutableList.of(
			MemoryModuleType.HOME,
			MemoryModuleType.JOB_SITE, 
			MemoryModuleType.PATH, 
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.INTERACTION_TARGET,
			MemoryModuleType.INTERACTABLE_DOORS,
			MemoryModuleType.MOBS,
			MemoryModuleType.VISIBLE_MOBS, 
			MemoryModuleType.NEAREST_PLAYERS,
			MemoryModuleType.NEAREST_VISIBLE_PLAYER,
			MemoryModuleType.NEAREST_HOSTILE,
			MemoryModuleType.HURT_BY, 
			MemoryModuleType.HURT_BY_ENTITY, 
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, 
			ModMemories.ATTACK_TARGET);
	

	/**===========================================================================================================================*\
	|*================================================----constructors and init---================================================*|
	\*============================================================================================================================*/
	protected AbstractNpcEntity(EntityType<? extends AbstractNpcEntity> type, World _world) {
		super(type, _world);
		EQUIPMENT_DROP_CHANCE = 0.15f * (4-world.getDifficulty().getId());
		GroundPathNavigator navigator = (GroundPathNavigator)this.getNavigator();
		navigator.setBreakDoors(true);
		navigator.setCanSwim(true);
		setCanPickUpLoot(true);
		setProfession(new ArcherProfession(this));
		initBrain();
		faction = Faction.GONDOR;
        Arrays.fill(this.inventoryArmorDropChances, EQUIPMENT_DROP_CHANCE/2);
        Arrays.fill(this.inventoryHandsDropChances, EQUIPMENT_DROP_CHANCE);
	}
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
	    getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		getAttribute(SharedMonsterAttributes.MAX_HEALTH			).setBaseValue(20);
		getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE		).setBaseValue(2);
		getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK	).setBaseValue(0);
		getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0);
		getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED		).setBaseValue(0.7);
		getAttribute(SharedMonsterAttributes.FOLLOW_RANGE		).setBaseValue(32);
	}
	@Override @Nullable
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setEquipmentBasedOnDifficulty(difficultyIn);
        this.setEnchantmentBasedOnDifficulty(difficultyIn);
        /*if (reason == SpawnReason.BREEDING)
        	set data to child
        	this.setVillagerData(this.getVillagerData().withProfession(VillagerProfession.NONE));
        	set data to random
        if (reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.SPAWNER || reason == SpawnReason.DISPENSER)
        	this.setVillagerData(this.getVillagerData().withType(IVillagerType.byBiome(worldIn.getBiome(new BlockPos(this)))));
        if (reason == SpawnReason.COMMAND || reason == SpawnReason.STRUCTURE)
         	set data to command data*/
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(DATA_CHARGING_STATE, false);
	}
	
	/**===========================================================================================================================*\
	|*================================================-------------brain----------================================================*|
	\*============================================================================================================================*/
	@Override protected Brain<?> createBrain(Dynamic<?> _past) {
		return new Brain<AbstractNpcEntity>(MEMORIES, SENSORS, _past);
	}
	public void resetBrain(ServerWorld serverWorldIn) {
		Brain<AbstractNpcEntity> brain = getBrain();
		brain.stopAllTasks(serverWorldIn, this);
		this.brain = brain.copy();
		initBrain();
	}
	@SuppressWarnings("unchecked")
	public void initBrain() {
		brain = proffesion.registerActivitiesOntoBrain((Brain<AbstractNpcEntity>) brain);
	    brain.updateActivity(this.world.getDayTime(), this.world.getGameTime());
	}

	/**===========================================================================================================================*\
	|*================================================---------interaction--------================================================*|
	\*============================================================================================================================*/
	@Override public boolean processInteract(PlayerEntity player, Hand hand){
		if( getProfession().onRightClicked(player,hand) || super.processInteract(player, hand)) {
			brain.setMemory(MemoryModuleType.INTERACTION_TARGET, player);
			for(Entry<UUID, Pair<Integer, Byte>> relation:PLAYER_RELATIONSHIPS.entrySet()) {
				LOGGER.error("relation:: UUID: "+relation.getKey().toString()+" terms: "+Integer.toString(relation.getValue().getFirst())+" notability: "+Integer.toString(relation.getValue().getSecond()));
			}
			return true;
		}return false;
    }
	@Override public void baseTick() {
		super.baseTick();
		if (isAlive())
			getProfession().tick();
	}
	@Override public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		Entity attacker = cause.getTrueSource();
		if (attacker != null && attacker instanceof PlayerEntity || attacker instanceof AbstractNpcEntity)
			addToCharacterRelation(attacker,-5,0);//this should actually affect the faction not you
		proffesion.kill();
	}
	@Override public boolean attackEntityFrom(DamageSource source, float amount){
		Entity trueSource = source.getTrueSource();
		if (trueSource != null && trueSource instanceof PlayerEntity || trueSource instanceof AbstractNpcEntity)
			addToCharacterRelation(trueSource,-5,0);
		LotbMod.LOGGER.error("we are under attack");
		return super.attackEntityFrom(source, amount);
	}
	@Override public void setPartying(BlockPos pos, boolean isPartying) {
	}

	/**===========================================================================================================================*\
	|*================================================--------serialisation-------================================================*|
	\*============================================================================================================================*/
	@Override public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		ListNBT inventorynbt = new ListNBT();
		for(int slot = 0; slot < inventory.getSlots(); ++slot) {
			ItemStack itemstack = inventory.getStackInSlot(slot);
			if (!itemstack.isEmpty())
				inventorynbt.add(itemstack.write(new CompoundNBT()));
		}
		
		ListNBT relationnbt = new ListNBT();
		for(Entry<UUID, Pair<Integer, Byte>> relation:PLAYER_RELATIONSHIPS.entrySet()) {
			CompoundNBT entry = new CompoundNBT();
			compound.putString("p", relation.getKey().toString());					//player
			compound.putInt("r", relation.getValue().getFirst());				//reputation
			compound.putByte("n",relation.getValue().getSecond());				//notability
			relationnbt.add(entry);
		}
		
		compound.put("Inv", inventorynbt);
		compound.put("Rel", relationnbt);
		compound.putInt("Fac", getFaction().ordinal());
	}
	@Override public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		faction = Faction.values()[compound.getInt("Fac")];
		
		ListNBT relationnbt = new ListNBT();
		for(CompoundNBT relation : relationnbt.toArray(new CompoundNBT[relationnbt.size()])) {
			UUID player = 	UUID.fromString(relation.getString("p"));
			int reputation = relation.getInt("r");
			byte knowledge = relation.getByte("n");
			PLAYER_RELATIONSHIPS.put(player, Pair.of(reputation, knowledge));
		}
		
		ListNBT listnbt = compound.getList("Inv", 10);
		for(int slot = 0; slot < listnbt.size(); ++slot) {
			ItemStack itemstack = ItemStack.read(listnbt.getCompound(slot));
			if (!itemstack.isEmpty()) {
				ModInventoryUtils.addItem(inventory,itemstack);
			}
		}
	}

	/**===========================================================================================================================*\
	|*================================================-------character data-------================================================*|
	\*============================================================================================================================*/
	protected String getNewName() {
		return "steve";
	}
	@Override protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
		
		//fill inventory
		ResourceLocation lootTable = LootTables.CHESTS_VILLAGE_VILLAGE_BUTCHER;
		if (lootTable != null && this.world.getServer() != null)
			ModInventoryUtils.fillInventoryWithLoot(world, rand, inventory, lootTable,
					new LootContext.Builder((ServerWorld)world)
									.withSeed(rand.nextLong())
									.withParameter(LootParameters.POSITION,getPosition())
									.build(LootParameterSets.CHEST)); 							//maybe use advancement?);
		
		ItemStack[] items_by_slot = new ItemStack[] {
				rand.nextBoolean() ? new ItemStack(Items.BOW):new ItemStack(Items.IRON_SWORD),
				rand.nextBoolean() ? new ItemStack(Items.ARROW,32):new ItemStack(Items.SHIELD),
				new ItemStack(Items.IRON_BOOTS),
				new ItemStack(Items.IRON_LEGGINGS),
				new ItemStack(Items.IRON_CHESTPLATE),
				new ItemStack(Items.IRON_HELMET)
		};
		for (int slot=0 ; slot<6 ; slot++)
			if (slot < 2 || rand.nextInt(3) == 0) {
				setItemStackToSlot(EquipmentSlotType.values()[slot], items_by_slot[slot]);
			}
	}
	public boolean createCharacterRelation(Entity entity, Pair<Integer,Byte> starting_relation) {
		if (entity instanceof PlayerEntity) {
			UUID player = entity.getUniqueID();
			boolean haditalready = PLAYER_RELATIONSHIPS.containsKey(player);
			PLAYER_RELATIONSHIPS.put(player, starting_relation);
			return haditalready;
		}else if (entity instanceof AbstractNpcEntity) {
			AbstractNpcEntity npc = (AbstractNpcEntity)entity;
		}
		return false;
	}
	@Nullable public Pair<Integer,Byte> getCharacterRelation(Entity entity) {
		boolean isplayer = entity instanceof PlayerEntity;	
		if (isplayer || entity instanceof AbstractNpcEntity) {
			UUID player = entity.getUniqueID();
			if (isplayer && PLAYER_RELATIONSHIPS.containsKey(player))
				return PLAYER_RELATIONSHIPS.get(player);
			//return faction function
			Pair<Integer,Byte> newRelation = Pair.of(0, (byte)0);
			createCharacterRelation(entity,newRelation);
			return newRelation;
		}return null;
	}
	public void addToCharacterRelation(Entity entity, int positivity, int notability) {
		if (entity instanceof PlayerEntity) {
			UUID player = entity.getUniqueID();
			if (PLAYER_RELATIONSHIPS.containsKey(player)) {
				Pair<Integer,Byte> old = PLAYER_RELATIONSHIPS.get(player);
				LOGGER.fatal("old: "+Integer.toString(old.getFirst())+" pos: "+Integer.toString(positivity)+" sum: "+Integer.toString(old.getFirst()+positivity));
				PLAYER_RELATIONSHIPS.put(player, Pair.of(old.getFirst()+positivity,(byte)(old.getSecond()+notability)));
			}else {
				createCharacterRelation(entity,Pair.of(positivity, (byte)notability));
			}
		}
	}
	
	/**===========================================================================================================================*\
	|*================================================---character skill getters--================================================*|
	\*============================================================================================================================*/
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
	
	/**===========================================================================================================================*\
	|*================================================--------sound getters-------================================================*|
	\*============================================================================================================================*/
	@Override protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_VILLAGER_AMBIENT;
	}
	@Override protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_VILLAGER_TRADE;
	}
	@Override protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_VILLAGER_DEATH;
	}
	@Override protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_VILLAGER_CELEBRATE, 0.15F, 1.0F);
	}
	public SoundEvent getTradeYesSound() { 
		return SoundEvents.ENTITY_VILLAGER_YES; 
	}
	
	/**===========================================================================================================================*\
	|*================================================-----getters and setters----================================================*|
	\*============================================================================================================================*/
	public AbstractProfession getProfession () {
		return proffesion;
	}
	public void setProfession (AbstractProfession _p) {
		proffesion = _p;
		if (world instanceof ServerWorld)
			resetBrain((ServerWorld)world);
	}
	@SuppressWarnings("unchecked")
	@Override public Brain<AbstractNpcEntity> getBrain(){
		return (Brain<AbstractNpcEntity>) super.getBrain();
	}
	@Override public void setCharging(boolean charged) {
		dataManager.set(DATA_CHARGING_STATE, charged);
	}
	@OnlyIn(Dist.CLIENT) public boolean isCharging() {
		return this.dataManager.get(DATA_CHARGING_STATE);
	}
	public Faction getFaction() {
		return faction;
	}
	
	/**===========================================================================================================================*\
	|*================================================---------calculators---------===============================================*|
	\*============================================================================================================================*/
	public int getTotalHeldFood() {
		int totalfood = 0;
		for (int i=0;i<inventory.getSlots();i++) {
			Item itemAtSlot = inventory.getStackInSlot(i).getItem();
			if (itemAtSlot.isFood())
				totalfood += itemAtSlot.getFood().getHealing();
		}
		return totalfood;
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
	@Override public void shoot(LivingEntity target, ItemStack stack, IProjectile projectile, float projectileAngle) {
		LotbMod.LOGGER.error("shooting a projectile");
		Entity projectileEntity = (Entity)projectile;
		double xdist = target.getPosX() - getPosX();
		double zdist = target.getPosZ() - getPosZ();
		
		double disthyp = MathHelper.sqrt(xdist * xdist + zdist * zdist);
		double aimheight = target.getPosYHeight(0.3333333333333333D) - projectileEntity.getPosY() + disthyp * 0.2F;
		Vector3f direction = calculateBulletDirection(new Vec3d(xdist, aimheight, zdist), projectileAngle);
		projectile.shoot(direction.getX(), direction.getY(), direction.getZ(), getArrowVelocity(),getArrowAccuracy());
	}

	/**===========================================================================================================================*\
	|*================================================------------misc-------------===============================================*|
	\*============================================================================================================================*/
	@Override protected void updateAITasks() {
		world.getProfiler().startSection("brain");
		getBrain().tick((ServerWorld)world, this);
		world.getProfiler().endSection();
		//super.updateAITasks();
	}
	@Override public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}
	/*@Override public void startSleeping(BlockPos p_213342_1_) {
		super.startSleeping(p_213342_1_);
		this.brain.setMemory(MemoryModuleType.LAST_SLEPT, LongSerializable.of(this.world.getGameTime()));
	}
	@Override public void wakeUp() {
		super.wakeUp();
		this.brain.setMemory(MemoryModuleType.field_226332_A_, LongSerializable.of(this.world.getGameTime()));
	}*/
	@Override protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
		for(int slot = 0; slot < inventory.getSlots(); ++slot) {
			ItemStack stack = inventory.getStackInSlot(slot);
			if (stack != ItemStack.EMPTY) {
				int count = (int)(stack.getCount()*EQUIPMENT_DROP_CHANCE*looting)+1;
				stack.setCount(Math.min(rand.nextInt(count),count)+rand.nextInt(2));
			}if (stack.getCount()==0) stack = ItemStack.EMPTY;
			entityDropItem(stack);
		}
	}
	@Override public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack bow = this.getHeldItem(Hand.MAIN_HAND);
		ItemStack arrow = this.getHeldItem(Hand.OFF_HAND);
		if ( bow.getItem() instanceof CrossbowItem) {
			LotbMod.LOGGER.error("shooting crossbow");
			if (bow.isEmpty()) LotbMod.LOGGER.error("you're b fucking fool jacob");
			if (arrow.isEmpty()) LotbMod.LOGGER.error("you're a fucking fool jacob");
			CrossbowItem.fireProjectiles(null, this, null, bow, getArrowVelocity(),getArrowAccuracy());
			//CrossbowItem.fireProjectiles(world, this, Hand.MAIN_HAND, bow, getArrowVelocity(),getArrowAccuracy());
			//playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		}else if (bow.getItem() instanceof BowItem) {
			AbstractArrowEntity abstractarrowentity = ProjectileHelper.fireArrow(this, arrow, distanceFactor);
			abstractarrowentity = ((BowItem)Items.BOW).customeArrow(abstractarrowentity);
			shoot(target,bow,abstractarrowentity,0.0f);
			playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
			this.world.addEntity(abstractarrowentity);
		}
	}
	public void switchOutGear(EquipmentSlotType gearSlot, int invSlot) {
		ItemStack gear  = this.getItemStackFromSlot(gearSlot);
		ItemStack item = inventory.getStackInSlot(invSlot);
		setItemStackToSlot(gearSlot, item);
		inventory.insertItem(invSlot, gear, false);
	}
}