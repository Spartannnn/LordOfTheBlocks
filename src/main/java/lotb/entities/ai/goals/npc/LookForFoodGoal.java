package lotb.entities.ai.goals.npc;

import lotb.entities.npc.AbstractNPCEntity;
import net.minecraft.entity.ai.goal.Goal;

public class LookForFoodGoal extends Goal {

    private AbstractNPCEntity npc;

    public LookForFoodGoal(AbstractNPCEntity npc) {
        this.npc = npc;
    }

    @Override
    public boolean shouldExecute() {
        return this.npc.getFoodManager().needFood();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldExecute();
    }

    @Override
    public void tick() {
    }
}
