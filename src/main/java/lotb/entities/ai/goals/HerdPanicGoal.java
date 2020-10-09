package lotb.entities.ai.goals;

import lotb.entities.Deer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class HerdPanicGoal extends PanicGoal {
    public HerdPanicGoal(Deer _creature, double _speed) {
        super(_creature,_speed);
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }
    public void startExecuting() {
        super.startExecuting();
        this.alertOthers();
        ((Deer)creature).isPanicking = true;
    }
    public boolean shouldExecute() {
        return ((Deer)creature).isPanicking || super.shouldExecute();
    }
    public boolean shouldContinueExecuting() {
        boolean cont = super.shouldContinueExecuting();
        if (!cont && creature instanceof Deer)
            ((Deer)creature).isPanicking = false;
        return cont;
    }

    protected void alertOthers() {
        IAttributeInstance iattributeinstance = creature.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        double dist = iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
        List<MobEntity> nearbyEntities = creature.world.getLoadedEntitiesWithinAABB(creature.getClass(), (new AxisAlignedBB(creature.getPosX(), creature.getPosY(), creature.getPosZ(), creature.getPosX() + 1.0D, creature.getPosY() + 1.0D, creature.getPosZ() + 1.0D)).grow(dist, 10.0D, dist));
        Iterator iterator = nearbyEntities.iterator();
        while(true) {
            MobEntity suspect;
            while(true) {
                if (!iterator.hasNext()) return;
                suspect = (MobEntity)iterator.next();
                if (creature != suspect && suspect.getAttackTarget() == null&& (!(creature instanceof TameableEntity) || ((TameableEntity)creature).getOwner() == ((TameableEntity)suspect).getOwner())) {
                    if (suspect.getClass() == Deer.class) {
                        ((Deer)suspect).isPanicking = true;
                        break;
                    }
                }
            }
        }
    }
}
