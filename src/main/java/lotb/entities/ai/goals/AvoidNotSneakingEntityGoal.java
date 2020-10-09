package lotb.entities.ai.goals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.util.math.Vec3d;

public class AvoidNotSneakingEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
    public AvoidNotSneakingEntityGoal(CreatureEntity _owner, Class entityToAvoid, float _avoidDistance, double _farSpeed, double _nearSpeed) {
        super(_owner, entityToAvoid, _avoidDistance, _farSpeed, _nearSpeed);
    }
    public boolean shouldExecute() {
        return super.shouldExecute() && !this.avoidTarget.isDiscrete();
    }
}
