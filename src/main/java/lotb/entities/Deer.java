package lotb.entities;

import lotb.entities.ai.goals.AvoidNotSneakingEntityGoal;
import lotb.entities.ai.goals.HerdPanicGoal;
import lotb.registries.ModBlocks;
import lotb.registries.ModEntities;
import lotb.registries.ModSounds;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Deer extends AnimalEntity {
	public boolean isPanicking = false;
	public boolean isTamed;

	public Deer(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new HerdPanicGoal(this, 2.0D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.fromItems(Items.SWEET_BERRIES, Items.WHEAT), true));
		this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(4, new AvoidNotSneakingEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.7d, 2.2D));
		//this.goalSelector.addGoal(4, new AvoidNotSneakingEntityGoal<>(this, Orc.class, 8.0F, 1.7d, 2.2D));
		//this.goalSelector.addGoal(4, new AvoidNotSneakingEntityGoal<>(this, Dwarf.class, 8.0F, 1.7d, 2.2D));
		//this.goalSelector.addGoal(4, new AvoidNotSneakingEntityGoal<>(this, Human.class, 8.0F, 1.7d, 2.2D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		//think about adding some panicky goals (run if nearby is hit, run from player)
	}

	@Override
	public AgeableEntity createChild(AgeableEntity ageable) {
		return ModEntities.DEER.create(this.world);
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.235F);
	}
	/*@Override protected SoundEvent getAmbientSound() { return ModSounds.DEER_AMBIENT.get(); }
	@Override protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSounds.DEER_HURT.get();
	}
	@Override protected SoundEvent getDeathSound() {
		return ModSounds.DEER_DEATH.get();
	}*/
}
