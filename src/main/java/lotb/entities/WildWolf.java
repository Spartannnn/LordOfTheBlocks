package lotb.entities;

import lotb.entities.ai.goals.AvoidNotSneakingEntityGoal;
import lotb.entities.ai.goals.HerdPanicGoal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;

public class WildWolf extends CreatureEntity {
    protected WildWolf(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }
    @Override protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.fromItems(Items.SWEET_BERRIES, Items.WHEAT), true));
        this.goalSelector.addGoal(4, new AvoidNotSneakingEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.7d, 2.2D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }
    @Override protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.235F);
    }
}
