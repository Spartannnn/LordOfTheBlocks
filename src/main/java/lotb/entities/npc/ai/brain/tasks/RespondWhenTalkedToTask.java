package lotb.entities.npc.ai.brain.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class RespondWhenTalkedToTask extends Task<LivingEntity> {
	public RespondWhenTalkedToTask() {
		super(ImmutableMap.of(
				MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.VALUE_PRESENT, 
				MemoryModuleType.LOOK_TARGET,MemoryModuleStatus.REGISTERED));
	}

	@Override
	protected void startExecuting(ServerWorld _world, LivingEntity _enitity, long gameTimeIn) {
		Brain<?> brain = _enitity.getBrain();
		if (brain.hasMemory(MemoryModuleType.INTERACTION_TARGET)) {
			BrainUtil.lookAt(_enitity, brain.getMemory(MemoryModuleType.INTERACTION_TARGET).get());
		}
	}
}
