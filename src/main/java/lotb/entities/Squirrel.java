package lotb.entities;

import lotb.entities.ai.goals.EatCakeGoal;
import lotb.registries.ModBlocks;
import lotb.registries.ModEntities;
import lotb.registries.ModSounds;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class Squirrel extends AnimalEntity {

	public Squirrel(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public AgeableEntity createChild(AgeableEntity ageable) {
		return ModEntities.SQUIRREL.create(this.world);
	}
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.235F);
	}
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.2D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.fromItems(Items.SWEET_BERRIES, ModBlocks.OAK_SAPLING.get(), ModBlocks.DARK_OAK_SAPLING.get(), ModBlocks.LEBETHRON_SAPLING.get()), false));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, WolfEntity.class, 10.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, FoxEntity.class, 10.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Badger.class, 10.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, MonsterEntity.class, 4.0F, 2.2D, 2.2D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 10.0F));
	}
	/*@Override protected SoundEvent getAmbientSound() { return ModSounds.SQUIRREL_AMBIENT.get(); }
	@Override protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSounds.SQUIRREL_HURT.get();
	}
	@Override protected SoundEvent getDeathSound() {
		return ModSounds.SQUIRREL_DEATH.get();
	}*/
}
