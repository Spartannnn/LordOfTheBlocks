package lotb.entities.ai.brain.tasks;

import com.google.common.collect.ImmutableMap;

import lotb.LotbMod;
import lotb.entities.ai.brain.ModMemories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class LogAllMemoryStatesTask extends Task<LivingEntity> {
	public LogAllMemoryStatesTask() {
		super(ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.VALUE_PRESENT));
	}
	@Override
	protected void startExecuting(ServerWorld _world, LivingEntity _enitity, long gameTimeIn) {
		Brain<?> brain = _enitity.getBrain();
		logMemory(brain,MemoryModuleType.HOME);
		logMemory(brain,MemoryModuleType.JOB_SITE);
		logMemory(brain,MemoryModuleType.PATH);
		logMemory(brain,MemoryModuleType.LOOK_TARGET);
		logMemory(brain,MemoryModuleType.WALK_TARGET);
		logMemory(brain,MemoryModuleType.INTERACTION_TARGET);
		logMemory(brain,MemoryModuleType.INTERACTABLE_DOORS);
		logMemory(brain,MemoryModuleType.MOBS);
		logMemory(brain,MemoryModuleType.VISIBLE_MOBS);
		logMemory(brain,MemoryModuleType.NEAREST_HOSTILE);
		logMemory(brain,MemoryModuleType.NEAREST_PLAYERS);
		logMemory(brain,MemoryModuleType.NEAREST_VISIBLE_PLAYER);
		logMemory(brain,MemoryModuleType.HURT_BY);
		logMemory(brain,MemoryModuleType.HURT_BY_ENTITY);
		logMemory(brain,MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		logMemory(brain,ModMemories.ATTACK_TARGET);
		brain.removeMemory(MemoryModuleType.INTERACTION_TARGET);
	}
	private void logMemory(Brain<?> brain , MemoryModuleType<?> type) {
		if (brain.hasMemory(type, MemoryModuleStatus.REGISTERED))
			LotbMod.LOGGER.warn(type.toString()+" registered");
		if (brain.hasMemory(type, MemoryModuleStatus.VALUE_ABSENT))
			LotbMod.LOGGER.warn(type.toString()+" absent");
		else 
			LotbMod.LOGGER.warn(type.toString()+" present: "+brain.getMemory(type).toString());
	}
}
