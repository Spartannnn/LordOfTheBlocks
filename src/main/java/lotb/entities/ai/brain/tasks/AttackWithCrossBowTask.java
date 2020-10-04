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
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

public class AttackWithCrossBowTask extends Task<AbstractNpcEntity> {
	//private final int attackRange;
	private final float runSpeed;
	private final float attackreach;
	protected int attackTick=-1;
	private int seeTime=0;
	private CrossbowState state = CrossbowState.UNCHARGED;

	public AttackWithCrossBowTask(float _runspeed, float _range) {
		super(ImmutableMap.of(
				MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, 
				ModMemories.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT));
		runSpeed = _runspeed;
		attackreach=_range;
	}
	//---------------checks---------------
	@Override
	protected boolean shouldExecute(ServerWorld worldIn, AbstractNpcEntity attacker) {
		Optional<LivingEntity> target = attacker.getBrain().getMemory(ModMemories.ATTACK_TARGET);
		return target.isPresent() && target.get().isAlive();
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
	}
	@Override
	protected void resetTask(ServerWorld worldIn, AbstractNpcEntity owner, long gameTimeIn) {
		owner.setAggroed(false);
		owner.getBrain().removeMemory(ModMemories.ATTACK_TARGET);
		owner.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
		owner.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
	}
	//---------------tick----------------
	@Override
	public void updateTask(ServerWorld worldIn, AbstractNpcEntity attacker, long gameTime) {
		LivingEntity target = attacker.getBrain().getMemory(ModMemories.ATTACK_TARGET).get();
		if (target != null) {
			boolean cansee = attacker.getEntitySenses().canSee(target);
			if (cansee != seeTime > 0) seeTime = 0;
			if (cansee) ++seeTime;
			else --seeTime;

			//double distance = attacker.getDistanceSq(target);
			boolean cant_attack = (!isCloseEnough(attacker,target) || seeTime < 5) && attackTick == 0;
			if (cant_attack)
				attacker.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(target.getPositionVec(), (float) (state == CrossbowState.UNCHARGED ? runSpeed : runSpeed * 0.5D), 1));
			else
				attacker.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);

			BrainUtil.lookAt(attacker, target);
			if (state == CrossbowState.UNCHARGED && !cant_attack) { 
				attacker.setActiveHand(Hand.MAIN_HAND); 
				state = CrossbowState.CHARGING;
				attacker.setCharging(true);
				
			} else if (state == CrossbowState.CHARGING) {
				if (!attacker.isHandActive()) state = CrossbowState.UNCHARGED;
				else if (attacker.getItemInUseMaxCount() >= CrossbowItem.getChargeTime(attacker.getActiveItemStack())) { 
					attacker.stopActiveHand();
					state = CrossbowState.CHARGED;
					attackTick = 20 + attacker.getRNG().nextInt(20);
					attacker.setCharging(false);
				}
				
			} else if (state == CrossbowState.CHARGED) {
				--attackTick;
				if (attackTick == 0)
					state = CrossbowState.READY_TO_ATTACK;
			} else if (state == CrossbowState.READY_TO_ATTACK && cansee) {
				attacker.attackEntityWithRangedAttack(target, 1.0F);
				ItemStack itemstack1 = attacker.getHeldItem(Hand.MAIN_HAND);
				CrossbowItem.setCharged(itemstack1, false);
				state = CrossbowState.UNCHARGED;
			}
		}
	}
	/*--------------------------------functions--------------------------------*/
	private boolean holdingRightItems(AbstractNpcEntity attacker) {
		return attacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof CrossbowItem 
			&& attacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem().isIn(ItemTags.ARROWS);
	}
	private boolean isCloseEnough(AbstractNpcEntity attacker, LivingEntity target) {
		return attacker.getDistanceSq(target.getPosX(), target.getPosY(), target.getPosZ()) <= attackreach;
	}
	static enum CrossbowState {
		UNCHARGED,
		CHARGING,
		CHARGED,
		READY_TO_ATTACK;
	}
}
