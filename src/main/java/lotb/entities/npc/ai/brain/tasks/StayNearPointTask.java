package lotb.entities.npc.ai.brain.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;

public class StayNearPointTask extends Task<CreatureEntity> {
	private final MemoryModuleType<GlobalPos> posMemory;
	   private final float entitySpeed;
	   private final int posMaxDistance;
	   private final int targetRadiusFromPos;
	   private final int maxRunTime;

	public StayNearPointTask(MemoryModuleType<GlobalPos> _posMemory, float _speed, int _maxDist, int _targRad, int _duration) {
		super(ImmutableMap.of(
				MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleStatus.REGISTERED, 
				MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, 
				_posMemory, MemoryModuleStatus.VALUE_PRESENT));
		posMemory = _posMemory;
		entitySpeed = _speed;
		posMaxDistance = _maxDist;
		targetRadiusFromPos = _targRad;
		maxRunTime = _duration;
	}

	@Override
	protected void startExecuting(ServerWorld worldIn, CreatureEntity entityIn, long gameTimeIn) {
		Brain<?> brain = entityIn.getBrain();
		brain.getMemory(posMemory).ifPresent((pos) -> {
			if (taskTimedOut(worldIn, entityIn)) {
				this.stopTask(entityIn, gameTimeIn);
			} else if (outsideOfRangeOfPos(worldIn, entityIn, pos)) { //else if pos is out of range [D], try walk towards somewhere random near it
				Vec3d targetPos = null;
				int i;
				for(i = 0; i < 1000 && (targetPos == null || outsideOfRangeOfPos(worldIn, entityIn, GlobalPos.of(entityIn.dimension, new BlockPos(targetPos)))); ++i) {
					targetPos = RandomPositionGenerator.findRandomTargetBlockTowards(entityIn, 15, 7, new Vec3d(pos.getPos()));
				}
				if (i == 1000) {
					this.stopTask(entityIn, gameTimeIn);
					return;
				}
				brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, this.entitySpeed, this.posMaxDistance));
			} else if (!this.withinMaxDistance(worldIn, entityIn, pos)) {
				brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(pos.getPos(), this.entitySpeed, this.posMaxDistance));
			}

		});
	}


	private void stopTask(CreatureEntity entity, long time) {
		Brain<?> brain = entity.getBrain();
		//entity.func_213742_a(posMemory);
		brain.removeMemory(posMemory);
		brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
	}

	private boolean taskTimedOut(ServerWorld world, CreatureEntity entity) {
		Optional<Long> optional = entity.getBrain().getMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		if (optional.isPresent())
			return world.getGameTime() - optional.get() > maxRunTime;
			return false;
	}
	private boolean outsideOfRangeOfPos(ServerWorld world, CreatureEntity entity, GlobalPos pos) {
		return pos.getDimension() != world.getDimension().getType() || pos.getPos().manhattanDistance(new BlockPos(entity)) > targetRadiusFromPos;
	}

	private boolean withinMaxDistance(ServerWorld world, CreatureEntity entity, GlobalPos pos) {
		return pos.getDimension() == world.getDimension().getType() && pos.getPos().manhattanDistance(new BlockPos(entity)) <= posMaxDistance;
	}
}
