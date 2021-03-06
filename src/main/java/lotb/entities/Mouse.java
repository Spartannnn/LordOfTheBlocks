package lotb.entities;

import lotb.entities.ai.goals.EatCakeGoal;
import lotb.registries.ModBlocks;
import lotb.registries.ModEntities;
import lotb.registries.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class Mouse extends AnimalEntity {

    public Mouse(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28d);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.2D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, WolfEntity.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, FoxEntity.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Badger.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, MonsterEntity.class, 4.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(5, new EatCakeGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 10.0F));
    }
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        Mouse child = new Mouse(ModEntities.MOUSE, this.world);
        child.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(child)), SpawnReason.BREEDING, null, null);
        return child;
    }
    /*@Override protected SoundEvent getAmbientSound() { return ModSounds.MOUSE_AMBIENT.get(); }
    @Override protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.MOUSE_HURT.get();
    }
    @Override protected SoundEvent getDeathSound() { return ModSounds.MOUSE_DEATH.get(); }*/


}
