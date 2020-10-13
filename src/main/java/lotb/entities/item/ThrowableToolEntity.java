package lotb.entities.item;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class ThrowableToolEntity extends Entity implements IProjectile, IRendersAsItem {
    private static final DataParameter<ItemStack> ITEMSTACK_DATA = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.ITEMSTACK);
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    protected boolean inGround;
    public int throwableShake;
    protected LivingEntity owner;
    private UUID ownerId;
    private Entity ignoreEntity;
    private int ignoreTime;
    private int ticksInGround;

    protected ThrowableToolEntity(EntityType<? extends ThrowableToolEntity> type, World worldIn) {
        super(type, worldIn);
    }
    protected ThrowableToolEntity(EntityType<? extends ThrowableToolEntity> type, double x, double y, double z, World worldIn) {
        this(type, worldIn);
        this.setPosition(x, y, z);
    }
    protected ThrowableToolEntity(EntityType<? extends ThrowableToolEntity> type, LivingEntity livingEntityIn, World worldIn) {
        this(type, livingEntityIn.getPosX(), livingEntityIn.getPosYEye() - (double)0.1F, livingEntityIn.getPosZ(), worldIn);
        this.owner = livingEntityIn;
        this.ownerId = livingEntityIn.getUniqueID();
    }
    protected void registerData() {
        this.getDataManager().register(ITEMSTACK_DATA, ItemStack.EMPTY);
    }

    //================================FUNCTIONS================================
    /**Sets throwable heading based on an entity that's throwing it*/
    public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
        float f = -MathHelper.sin(rotationYawIn * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitchIn * ((float)Math.PI / 180F));
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * ((float)Math.PI / 180F));
        float f2 = MathHelper.cos(rotationYawIn * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitchIn * ((float)Math.PI / 180F));
        this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        Vec3d vec3d = entityThrower.getMotion();
        this.setMotion(this.getMotion().add(vec3d.x, entityThrower.onGround ? 0.0D : vec3d.y, vec3d.z));
    }

    /**Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.*/
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy).scale((double)velocity);
        this.setMotion(vec3d);
        float f = MathHelper.sqrt(horizontalMag(vec3d));
        this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (double)(180F / (float)Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }
    /**Called when this EntityThrowable hits a block or entity.*/
    protected abstract float getAttackDamage();
    //================================TICK================================
    public void tick() {
        super.tick();
        if (this.throwableShake > 0) {
            --this.throwableShake;
        }
        AxisAlignedBB axisalignedbb = this.getBoundingBox().expand(this.getMotion()).grow(1.0D);

        for(Entity entity : this.world.getEntitiesInAABBexcluding(this, axisalignedbb, (_entity) -> {
            return !_entity.isSpectator() && _entity.canBeCollidedWith();
        })) {
            if (entity == this.ignoreEntity) {
                ++this.ignoreTime;
                break;
            }
            if (this.owner != null && this.ticksExisted < 2 && this.ignoreEntity == null) {
                this.ignoreEntity = entity;
                this.ignoreTime = 3;
                break;
            }
        }

        RayTraceResult raytraceresult = ProjectileHelper.rayTrace(this, axisalignedbb, (_target) -> {
            return !_target.isSpectator() && _target.canBeCollidedWith() && _target != this.ignoreEntity;
        }, RayTraceContext.BlockMode.OUTLINE, true);
        if (this.ignoreEntity != null && this.ignoreTime-- <= 0) {
            this.ignoreEntity = null;
        }

        if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK && this.world.getBlockState(((BlockRayTraceResult)raytraceresult).getPos()).getBlock() == Blocks.NETHER_PORTAL) {
                this.setPortal(((BlockRayTraceResult)raytraceresult).getPos());
            } else if (!net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)){
                if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                    EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) raytraceresult;
                    Entity entity = entityRayTraceResult.getEntity();
                    if (entity instanceof LivingEntity) {
                        entity.attackEntityFrom(DamageSource.GENERIC, getAttackDamage());
                    }
                    this.setMotion(0,-1,0);
                }else if (raytraceresult.getType() == RayTraceResult.Type.BLOCK){
                    this.inGround=true;
                }
            }
        }
        if (this.inGround) tickInGround();
        else{
            this.ticksInGround = 0;
            Vec3d vec3d = this.getMotion();
            double d0 = this.getPosX() + vec3d.x;
            double d1 = this.getPosY() + vec3d.y;
            double d2 = this.getPosZ() + vec3d.z;
            float f = MathHelper.sqrt(horizontalMag(vec3d));
            this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));

            for(this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (double)(180F / (float)Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
            while(this.rotationPitch - this.prevRotationPitch >= 180.0F)
                this.prevRotationPitch += 360.0F;
            while(this.rotationYaw - this.prevRotationYaw < -180.0F)
                this.prevRotationYaw -= 360.0F;
            while(this.rotationYaw - this.prevRotationYaw >= 180.0F)
                this.prevRotationYaw += 360.0F;

            this.rotationPitch = MathHelper.lerp(0.2F, this.prevRotationPitch, this.rotationPitch);
            this.rotationYaw = MathHelper.lerp(0.2F, this.prevRotationYaw, this.rotationYaw);
            float speed;
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i)
                    this.world.addParticle(ParticleTypes.BUBBLE, d0 - vec3d.x * 0.25D, d1 - vec3d.y * 0.25D, d2 - vec3d.z * 0.25D, vec3d.x, vec3d.y, vec3d.z);
                speed = 0.8F;
            } else
                speed = 0.99F;

            this.setMotion(vec3d.scale((double)speed));
            if (!this.hasNoGravity()) {
                Vec3d vec3d1 = this.getMotion();
                this.setMotion(vec3d1.x, vec3d1.y - (double)this.getGravityVelocity(), vec3d1.z);
            }
            this.setPosition(d0, d1, d2);
        }
    }
    protected void tickInGround() {
        ++this.ticksInGround;
        if (this.ticksInGround >= 1200) {
            this.remove();
        }
    }

    //------------------------------CLIENT SIDE------------------------------
    /**Updates the entity motion clientside, called by packets from the server*/
    @OnlyIn(Dist.CLIENT)
    public void setVelocity(double x, double y, double z) {
        this.setMotion(x, y, z);
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (double)(180F / (float)Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (double)(180F / (float)Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

    }
    /**Checks if the entity is in range to render.*/
    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    //================================SERIALIZATION================================
    public void writeAdditional(CompoundNBT compound) {
        compound.putInt("xTile", this.xTile);
        compound.putInt("yTile", this.yTile);
        compound.putInt("zTile", this.zTile);
        compound.putByte("shake", (byte)this.throwableShake);
        compound.putBoolean("inGround", this.inGround);
        if (this.ownerId != null) {
            compound.put("owner", NBTUtil.writeUniqueId(this.ownerId));
        }
        ItemStack itemstack = this.getItemStack();
        if (!itemstack.isEmpty()) {
            compound.put("Item", itemstack.write(new CompoundNBT()));
        }
    }
    /**(abstract) Protected helper method to read subclass entity data from NBT.*/
    public void readAdditional(CompoundNBT compound) {
        this.xTile = compound.getInt("xTile");
        this.yTile = compound.getInt("yTile");
        this.zTile = compound.getInt("zTile");
        this.throwableShake = compound.getByte("shake") & 255;
        this.inGround = compound.getBoolean("inGround");
        this.owner = null;
        if (compound.contains("owner", 10)) {
            this.ownerId = NBTUtil.readUniqueId(compound.getCompound("owner"));
        }
        ItemStack itemstack = ItemStack.read(compound.getCompound("Item"));
        this.setItem(itemstack);
    }
    //================================ITEM STUFF================================
    protected abstract Item getDefaultItem();

    protected ItemStack getItemStack() {
        return this.getDataManager().get(ITEMSTACK_DATA);
    }

    public void setItem(ItemStack stack) {
        if (stack.getItem() != this.getDefaultItem() || stack.hasTag()) {
            this.getDataManager().set(ITEMSTACK_DATA, Util.make(stack.copy(), (item) -> {
                item.setCount(1);
            }));
        }

    }
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        ItemStack itemstack = this.getItemStack();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }
    //================================SOME GETTERS================================
    @Nullable
    public LivingEntity getThrower() {
        if ((this.owner == null || this.owner.removed) && this.ownerId != null && this.world instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.world).getEntityByUuid(this.ownerId);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            } else {
                this.owner = null;
            }
        }

        return this.owner;
    }
    /**Gets the amount of gravity to apply to the thrown entity with each tick.*/
    protected float getGravityVelocity() {
        return 0.03F;
    }
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }
}
