package lotb.entities.npc.ai.brain.tasks;

import com.google.common.collect.ImmutableMap;
import lotb.entities.ai.ModMemories;
import lotb.entities.npc.AbstractNPCEntity;
import lotb.entities.npc.NPCFoodManager;
import lotb.entities.npc.NPCInventory;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;

public class LookForFoodTask extends Task<AbstractNPCEntity> {

    public LookForFoodTask() {
        super(ImmutableMap.of(ModMemories.NEEDS_FOOD, MemoryModuleStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, AbstractNPCEntity owner) {
        NPCFoodManager foodManager = owner.getFoodManager();
        return foodManager.needFood();
    }

    @Override
    protected void startExecuting(ServerWorld worldIn, AbstractNPCEntity entityIn, long gameTimeIn) {
        Brain<AbstractNPCEntity> brain = entityIn.getBrain();
        if (brain.hasMemory(ModMemories.NEEDS_FOOD)) {
            NPCInventory npcInventory = entityIn.getNpcInventory();
            int slot = npcInventory.containsFood();
            if (slot != -1) {
                NPCFoodManager foodManager = entityIn.getFoodManager();
                ItemStack food = npcInventory.getStackInSlot(slot);
                assert food.getItem().getFood() != null : "Food is null";
                foodManager.setFoodLevel(foodManager.getFoodLevel() + food.getItem().getFood().getHealing());
                food.shrink(1);
            }
        }

    }
}
