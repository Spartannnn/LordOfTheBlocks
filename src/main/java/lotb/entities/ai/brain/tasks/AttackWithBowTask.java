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
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.server.ServerWorld;

public class AttackWithBowTask extends Task<AbstractNpcEntity> {
	private final float runSpeed;
	private final float attackreach;
	protected int attackTick=-1;
	private int seeTime=0;
	private boolean strafingClockwise=false;
	private boolean strafingBackwards=false;
	private int strafingTime = -1;

	public AttackWithBowTask(float _runspeed, float _range) {
		super(ImmutableMap.of(
				MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, 
				ModMemories.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT));
		runSpeed = _runspeed;
		attackreach=_range*_range;
	}
	//---------------checks---------------
	@Override
	protected boolean shouldExecute(ServerWorld worldIn, AbstractNpcEntity attacker) {
		Optional<LivingEntity> target = attacker.getBrain().getMemory(ModMemories.ATTACK_TARGET);
		return target.isPresent() && checkAlive(attacker,target) && holdingRightItems(attacker);
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
	@Override protected void updateTask(ServerWorld worldIn, AbstractNpcEntity attacker, long gameTime) {
		LivingEntity target = attacker.getBrain().getMemory(ModMemories.ATTACK_TARGET).get();
		if (target != null) {
			BrainUtil.lookAt(attacker, target);
			boolean cansee = attacker.getEntitySenses().canSee(target);
			
			if (cansee ^ seeTime > 0) seeTime = 0;	//if can see and see time <= 0, set see time to 0, 
			if (cansee) seeTime++;					//if can't see and see time > 0, set see time to 0
			else seeTime--;							//if in sight increment can see time, if out decrement
			
			if (isCloseEnough(attacker,target,0.75f) && this.seeTime >= 20)
				attacker.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
			else
				attacker.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(target.getPositionVec(), runSpeed, 1));
			

			if (attacker.isHandActive()) {
				if (!cansee && this.seeTime < -60)
					attacker.resetActiveHand();
				else if (cansee) {
					int i = attacker.getItemInUseMaxCount();
					if (i >= 20) {
						attacker.resetActiveHand();
						attacker.attackEntityWithRangedAttack(target, BowItem.getArrowVelocity(i));
						attackTick = attacker.getArcherySpeed();
					}
				}
			} else if (--attackTick <= 0 && this.seeTime >= -60) {
				attacker.setActiveHand(ProjectileHelper.getHandWith(attacker, Items.BOW));
			}
		}
	}
	protected void updateTaskWithStrafe(ServerWorld worldIn, AbstractNpcEntity attacker, long gameTime) {
		LivingEntity target = attacker.getBrain().getMemory(ModMemories.ATTACK_TARGET).get();
		if (target != null) {
			BrainUtil.lookAt(attacker, target);
			boolean cansee = attacker.getEntitySenses().canSee(target);
			
			if (cansee ^ seeTime > 0) seeTime = 0;	//if can see and see time <= 0, set see time to 0, 
			if (cansee) seeTime++;					//if can't see and see time > 0, set see time to 0
			else seeTime--;//if in sight increment can see time, if out decrement
			if (isCloseEnough(attacker,target,0.75f) && this.seeTime >= 20) {
				++this.strafingTime;	//if is close enough and has seen for 20 seconds
				attacker.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
			} else {
				attacker.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(target.getPositionVec(), runSpeed, 1));
				this.strafingTime = -1; //else try to move to target and decrease strafing time
			}

			if (this.strafingTime >= 20) { //if strafing time exceeds 20
				if (attacker.getRNG().nextFloat() < 0.3D)
					this.strafingClockwise = !this.strafingClockwise; //randomly stop strafing clockwise
				if (attacker.getRNG().nextFloat() < 0.3D)
					this.strafingBackwards = !this.strafingBackwards; //randomly stop strafing backwards
				this.strafingTime = 0;
			}

			if (this.strafingTime > -1) { //if strafe time exceeds -1, check in strafe bounds and then strafe
				if (isCloseEnough(attacker,target,0.75f))
					this.strafingBackwards = false;
				else if (isCloseEnough(attacker,target,0.25f))
					this.strafingBackwards = true;
				attacker.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
				attacker.faceEntity(target, 30.0F, 30.0F);
			}

			if (attacker.isHandActive()) {
				if (!cansee && this.seeTime < -60)
					attacker.resetActiveHand();
				else if (cansee) {
					int i = attacker.getItemInUseMaxCount();
					if (i >= 20) {
						attacker.resetActiveHand();
						attacker.attackEntityWithRangedAttack(target, BowItem.getArrowVelocity(i));
						attackTick = attacker.getArcherySpeed();
					}
				}
			} else if (--attackTick <= 0 && this.seeTime >= -60) {
				attacker.setActiveHand(ProjectileHelper.getHandWith(attacker, Items.BOW));
			}
		}
	}
	
	/*--------------------------------functions--------------------------------*/
	private boolean holdingRightItems(AbstractNpcEntity attacker) {
		return attacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof BowItem 
			&& attacker.getItemStackFromSlot(EquipmentSlotType.OFFHAND).getItem().isIn(ItemTags.ARROWS);
	}
	private boolean isCloseEnough(AbstractNpcEntity attacker, LivingEntity target, float mul) {
		return attacker.getDistanceSq(target.getPosX(), target.getPosY(), target.getPosZ()) <= attackreach*mul;
	}
	private boolean checkAlive(AbstractNpcEntity attacker, Optional<LivingEntity> target) {
		if (target.get().isAlive()) return true;
		attacker.getBrain().removeMemory(ModMemories.ATTACK_TARGET);
		return false;
	}
}
