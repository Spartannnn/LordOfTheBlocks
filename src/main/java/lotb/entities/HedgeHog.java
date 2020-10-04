package lotb.entities;

import lotb.registries.ModEntities;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HedgeHog extends AnimalEntity {

	public HedgeHog(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public AgeableEntity createChild(AgeableEntity ageable) {
		HedgeHog child = new HedgeHog(ModEntities.HEDGEHOG,this.world);
		child.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(child)), SpawnReason.BREEDING, null, null);
		return child;
	}
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
		this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(5.0D);
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
	    this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
	    this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 10.0F));
	}

}
