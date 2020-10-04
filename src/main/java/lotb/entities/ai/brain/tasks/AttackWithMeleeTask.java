package lotb.entities.ai.brain.tasks;

import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import lotb.entities.ai.brain.ModMemories;
import lotb.entities.npc.AbstractNpcEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.SwordItem;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.Hand;

public class AttackWithMeleeTask extends Task<AbstractNpcEntity> {
	//private final int attackRange;
	private final float runSpeed;
	protected int attackTick;
	private double attackreach = 1;

	public AttackWithMeleeTask(float _runspeed) {
		super(ImmutableMap.of(
				MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, 
				ModMemories.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT));
		runSpeed = _runspeed;
	}
	//---------------checks---------------
	@Override
	protected boolean shouldExecute(ServerWorld worldIn, AbstractNpcEntity attacker) {
		Optional<LivingEntity> target = attacker.getBrain().getMemory(ModMemories.ATTACK_TARGET);
		return target.isPresent() && target.get().isAlive() && holdingRightItems(attacker);
	}

	@Override
	protected boolean shouldContinueExecuting(ServerWorld worldIn, AbstractNpcEntity attacker, long gameTimeIn) {
		return shouldExecute(worldIn, attacker);
	}
	//-----------start and stop-----------
	@Override
	protected void startExecuting(ServerWorld worldIn, AbstractNpcEntity owner, long gameTimeIn) {
		LivingEntity target = owner.getBrain().getMemory(ModMemories.ATTACK_TARGET).get();
		owner.setAggroed(true);
		owner.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(target.getPositionVec(), runSpeed, 1));
		BrainUtil.lookAt(owner, target);
		attackreach = getAttackReachSqr(owner,target);
	}
	@Override
	protected void resetTask(ServerWorld worldIn, AbstractNpcEntity owner, long gameTimeIn) {
		owner.setAggroed(false);
		owner.getBrain().removeMemory(ModMemories.ATTACK_TARGET);
		owner.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
		owner.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
		attackreach =1;
	}
	//---------------tick----------------
	@Override
	protected void updateTask(ServerWorld worldIn, AbstractNpcEntity attacker, long gameTime) {
		LivingEntity target = attacker.getBrain().getMemory(ModMemories.ATTACK_TARGET).get();
		BrainUtil.lookAt(attacker, target);
		if (target!=null && isCloseEnough(attacker, target)) {
			if (attackTick <= 0) {
				attackTick = attacker.getAttackSpeed();
				attacker.swingArm(Hand.MAIN_HAND);
				attacker.attackEntityAsMob(target);
			}else if(isDuelWielding(attacker) && attackTick==attacker.getAttackSpeed()/2) {
				attacker.swingArm(Hand.OFF_HAND);
				attacker.attackEntityAsMob(target);
			}attackTick--;
		} else {
			attackTick = 0;
			attacker.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(target.getPositionVec(), runSpeed, 1));
		}
	}
	/*--------------------------------functions--------------------------------*/
	private boolean holdingRightItems(AbstractNpcEntity attacker) {
		return attacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof SwordItem;
		//return attacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem().isIn(ModItemTags.MELEE_WEAPONS);
	}
	private boolean isDuelWielding(AbstractNpcEntity attacker) {
		return attacker.weildingOffhand && attacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem() instanceof SwordItem;
		//return attacker.weildingOffhand && attacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem().isIn(ModItemTags.MELEE_WEAPONS);
	}
	private boolean isCloseEnough(AbstractNpcEntity attacker, LivingEntity target) {
		return attacker.getDistanceSq(target.getPosX(), target.getPosY(), target.getPosZ()) <= attackreach;
	}
	private double getAttackReachSqr(AbstractNpcEntity owner, LivingEntity target) {
		return owner.getWidth() * owner.getWidth() * 4.0F + target.getWidth();
	}
}
