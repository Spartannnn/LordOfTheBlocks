package lotb.entities.npc.profesion;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import lotb.entities.ai.ModActivities;
import lotb.entities.ai.ModSchedules;
import lotb.entities.npc.AbstractNPCEntity;
import lotb.entities.npc.ai.brain.tasks.StayNearPointTask;
import lotb.entities.npc.ai.brain.tasks.*;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Collections;
import java.util.Set;

public abstract class SoldierProfession implements IProfession {

    public static final Set<Item> BEDS = Sets.newHashSet();
    public static final Set<Item> WEAPONS = Sets.newHashSet();

    static {
        Collections.addAll(BEDS, Items.WHITE_BED, Items.YELLOW_BED);
        Collections.addAll(WEAPONS, Items.IRON_SWORD);
    }

    @Override
    public Brain<AbstractNPCEntity> registerActivitiesOntoBrain(AbstractNPCEntity npc, Brain<AbstractNPCEntity> brain) {
        float npcWalkSpeed = (float) npc.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
        brain.setSchedule(ModSchedules.SOLDGIER);

        brain.registerActivity(Activity.CORE, core(npcWalkSpeed));
        brain.registerActivity(Activity.REST, sleep(npcWalkSpeed));
        brain.registerActivity(ModActivities.FREE, guard(npcWalkSpeed));
        brain.registerActivity(ModActivities.GUARD, guard(npcWalkSpeed));
        brain.registerActivity(ModActivities.PATROL, guard(npcWalkSpeed));

        return brain;
    }

    protected ImmutableList<Pair<Integer, ? extends Task<? super AbstractNPCEntity>>> core(float walkingSpeed) {
        float runningSpeed = walkingSpeed * 1.5F;
        return ImmutableList.of(
                Pair.of(0, new SwimTask(0.4F, 0.8F)),
                Pair.of(0, new LookTask(45, 90)),
                Pair.of(0, new InteractWithDoorTask()),
                Pair.of(0, new WakeUpTask()),
                Pair.of(0, new LogAllMemoryStatesTask()),
                Pair.of(1, new WalkToTargetTask(200)),
                Pair.of(1, new RetaliateTask()),
                Pair.of(1, new AttackWithCrossBowTask(runningSpeed, 32)),
                Pair.of(1, new AttackWithBowTask(runningSpeed, 32)),
                Pair.of(1, new AttackWithMeleeTask(runningSpeed)),
                Pair.of(1, new LookForFoodTask()));
    }

    protected ImmutableList<Pair<Integer, ? extends Task<? super AbstractNPCEntity>>> sleep(float walkingSpeed) {
        return ImmutableList.of(
                Pair.of(2, new StayNearPointTask(MemoryModuleType.HOME, walkingSpeed, 9, 100, 1200)),
                Pair.of(3, new SleepAtHomeTask()),
                Pair.of(99, new UpdateActivityTask())
                //if talked too randomly wake up and if they do, get annoyed
                //if alarm is raised, randomly wake up
        );
    }


    protected ImmutableList<Pair<Integer, ? extends Task<? super AbstractNPCEntity>>> free(float walkingSpeed) {
        return ImmutableList.of(
                lookAtAnyNpcs()
                //wander around aimlessly
                //if alarm is raised go out and fight
                //if weapon breaks, get a new one
                //if low on food, get some
                //if bed missing, go find one
                //talk to npcs, tell stories
                //if interacted with respond
        );
    }

    protected ImmutableList<Pair<Integer, ? extends Task<? super AbstractNPCEntity>>> guard(float walkingSpeed) {
        return ImmutableList.of(
                lookAtAnyNpcs(),
                //Pair.of(3, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)),
                Pair.of(4, new WalkRandomlyTask(walkingSpeed)),
                Pair.of(1, new AttackNearestHostileTask()),
                //Pair.of(2, new StayNearPointTask(MemoryModuleType.JOB_SITE, walkingSpeed, 9, 100, 1200)),
                Pair.of(99, new UpdateActivityTask())
                //stay in one place (face one dirction)
                //if enemy entity is nearby raise alarm
                //if enemy entity within short distance attack
                //if weapon breaks, get a new one
                //if interacted with respond
                //if neutral entity within radius, halt
                //if attacked retaliate
                //if nearby alley attacked, retaliate, raise alarm
        );
    }

    protected ImmutableList<Pair<Integer, ? extends Task<? super AbstractNPCEntity>>> train(float walkingSpeed) {
        return ImmutableList.of(
                lookAtAnyNpcs()
                //if alarm is raised go out and fight
                //train
                //if weapon breaks, get a new one
                //if interacted with stop what they're doing or talk to captain
        );
    }
}
