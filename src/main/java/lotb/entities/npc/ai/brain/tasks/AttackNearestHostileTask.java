package lotb.entities.npc.ai.brain.tasks;


import com.google.common.collect.ImmutableMap;
import lotb.entities.ai.ModMemories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class AttackNearestHostileTask extends Task<LivingEntity> {
	public AttackNearestHostileTask() {
		super(ImmutableMap.of(MemoryModuleType.NEAREST_HOSTILE, MemoryModuleStatus.VALUE_PRESENT, ModMemories.ATTACK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
	}
	@Override
	protected void startExecuting(ServerWorld _world, LivingEntity _enitity, long gameTimeIn) {
		Brain<?> brain = _enitity.getBrain();
		if (brain.hasMemory(MemoryModuleType.NEAREST_HOSTILE)) {
			brain.setMemory(ModMemories.ATTACK_TARGET, brain.getMemory(MemoryModuleType.NEAREST_HOSTILE).get());
		}
	}
}
