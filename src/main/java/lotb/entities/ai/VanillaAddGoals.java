package lotb.entities.ai;

import java.util.function.Predicate;

import lotb.entities.HedgeHog;
import lotb.entities.Mouse;
import lotb.entities.Squirrel;
import lotb.entities.Badger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VanillaAddGoals {
	@SubscribeEvent
	public void onEntityConstructed(EntityEvent.EntityConstructing event){
		Entity entity = event.getEntity();
		if (entity instanceof CatEntity) {
			CatEntity cat = (CatEntity)entity;
			cat.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(cat, Mouse.class, 10, true, true, (Predicate<LivingEntity>)null));
		}else if (entity instanceof WolfEntity) {
			WolfEntity wolf = (WolfEntity)entity;
			wolf.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(wolf, Badger.class, 10, true, true, (Predicate<LivingEntity>)null));
			wolf.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(wolf, Squirrel.class, 10, true, true, (Predicate<LivingEntity>)null));
			wolf.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(wolf, Mouse.class, 10, true, true, (Predicate<LivingEntity>)null));
			wolf.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(wolf, HedgeHog.class, 10, true, true, (Predicate<LivingEntity>)null));
		}
    }
}
