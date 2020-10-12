package lotb.entities.npc.ai.brain.tasks;


import com.google.common.collect.ImmutableMap;
import lotb.entities.ai.ModMemories;
import lotb.entities.npc.AbstractNPCEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class RetaliateTask extends Task<AbstractNPCEntity> {
    boolean freindlyFire;

    public RetaliateTask(boolean _ff) {
        super(ImmutableMap.of(
                MemoryModuleType.HURT_BY_ENTITY, MemoryModuleStatus.VALUE_PRESENT,
                ModMemories.ATTACK_TARGET, MemoryModuleStatus.REGISTERED));
        freindlyFire = _ff;
    }

    public RetaliateTask() {
        this(false);
    }

    @Override
    protected void startExecuting(ServerWorld _world, AbstractNPCEntity _enitity, long gameTimeIn) {
        Brain<?> brain = _enitity.getBrain();
        if (brain.hasMemory(MemoryModuleType.HURT_BY_ENTITY)) {
            LivingEntity target = brain.getMemory(MemoryModuleType.HURT_BY_ENTITY).get();
            if (freindlyFire || !(target instanceof AbstractNPCEntity) || _enitity.getFaction() != ((AbstractNPCEntity) target).getFaction())
                brain.setMemory(ModMemories.ATTACK_TARGET, target);
            //brain.switchTo(Activity.PANIC);
        }
    }
}
