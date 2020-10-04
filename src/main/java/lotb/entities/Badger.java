package lotb.entities;

import javax.annotation.Nullable;

import lotb.registries.ModEntities;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Badger extends AnimalEntity {

	public Badger(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public AgeableEntity createChild(AgeableEntity ageable) {
		Badger child = new Badger(ModEntities.BADGER,this.world);
		child.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(child)), SpawnReason.BREEDING, null, null);
		return child;
	}
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this,1.25d));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.fromItems(Items.SWEET_BERRIES, Items.APPLE), false));
		this.goalSelector.addGoal(3, new BreedGoal(this,1.0d));
	    this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
	    this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 10.0F));
		//targets
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, 10, false, false, (entity) -> {
	         return entity instanceof HedgeHog || entity instanceof Mouse; }));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, 10, false, false, (entity) -> {
	         return entity instanceof RabbitEntity; }));
	    
	}
	@Nullable
	@Override
	public LivingEntity getAttackTarget() {
		LivingEntity candidate = super.getAttackTarget();
	    if (candidate != null)
	    		return candidate;
	    else {
	    	FoxEntity child = new FoxEntity(EntityType.FOX,this.world);
	    	child.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(child)), SpawnReason.BREEDING, null, null);
			return child;
	    	}
	   }
	/*@Override
	protected void updateAITasks() {
		super.updateAITasks();
	}
	@Override
	public void livingTick() {
		if (this.world.isRemote)
			this.timer = Math.max(0,timer -1);
		super.livingTick();
	}*/
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28d);
		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.1d);
		//this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(0.28d);
	}

}
