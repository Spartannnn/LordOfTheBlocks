package lotb.entities.npc.profession;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;

import lotb.entities.ai.brain.ModActivities;
import lotb.entities.ai.brain.ModSchedules;
import lotb.entities.ai.brain.tasks.AttackWithCrossBowTask;
import lotb.entities.ai.brain.tasks.AttackWithMeleeTask;
import lotb.entities.ai.brain.tasks.AttackNearestHostileTask;
import lotb.entities.ai.brain.tasks.AttackWithBowTask;
import lotb.entities.ai.brain.tasks.LogAllMemoryStatesTask;
import lotb.entities.ai.brain.tasks.RetaliateTask;
import lotb.entities.ai.brain.tasks.StayNearPointTask;
import lotb.entities.npc.AbstractNpcEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.InteractWithDoorTask;
import net.minecraft.entity.ai.brain.task.LookTask;
import net.minecraft.entity.ai.brain.task.SleepAtHomeTask;
import net.minecraft.entity.ai.brain.task.SwimTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.UpdateActivityTask;
import net.minecraft.entity.ai.brain.task.WakeUpTask;
import net.minecraft.entity.ai.brain.task.WalkRandomlyTask;
import net.minecraft.entity.ai.brain.task.WalkToTargetTask;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public abstract class AbstractSoldier extends AbstractProfession {
	//replace with a more sensible form type system
	public static final Set<Item> BEDS		= new HashSet<>();
	public static final Set<Item> WEAPONS	= new HashSet<>();
	static {
		Collections.addAll(BEDS, Items.WHITE_BED, Items.YELLOW_BED);
		Collections.addAll(WEAPONS, Items.IRON_SWORD);
	}

	public AbstractSoldier(AbstractNpcEntity _entity) {
		super(_entity);
	}
	 /**==================================================================
	  * ==================== ACTIVITY STATES ============================= */
	@Override
	public Brain<AbstractNpcEntity> registerActivitiesOntoBrain (Brain<AbstractNpcEntity> brain){
		float npcwalkspeed = (float)entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
        brain.setSchedule(ModSchedules.SOLDGIER);
		
        brain.registerActivity(Activity.CORE,core(npcwalkspeed) );
		brain.registerActivity(Activity.REST,sleep(npcwalkspeed) );
		brain.registerActivity(ModActivities.FREE,	guard(npcwalkspeed)	);
		brain.registerActivity(ModActivities.GUARD,	guard(npcwalkspeed)	);
		brain.registerActivity(ModActivities.PATROL,guard(npcwalkspeed));
		
		brain.setDefaultActivities(ImmutableSet.of(Activity.CORE));
		brain.setFallbackActivity(ModActivities.FREE);
		brain.switchTo(ModActivities.FREE);
		return brain;
	}
	
	protected ImmutableList<Pair<Integer, ? extends Task<? super AbstractNpcEntity>>> core(float walkingSpeed) {
		float runningspeed = walkingSpeed*1.5f;
		return ImmutableList.of(
	    		  Pair.of(0, new SwimTask(0.4F, 0.8F)), 
	    		  Pair.of(0, new LookTask(45, 90)), 
	    		  Pair.of(0, new InteractWithDoorTask()), 
	    		  Pair.of(0, new WakeUpTask()),
	    		  Pair.of(0, new LogAllMemoryStatesTask()),
	    		  Pair.of(1, new WalkToTargetTask(200)),
	    		  Pair.of(1, new RetaliateTask()),
	    		  Pair.of(1, new AttackWithCrossBowTask(runningspeed,32)),
	    		  Pair.of(1, new AttackWithBowTask(runningspeed,32)),
	    		  Pair.of(1, new AttackWithMeleeTask(runningspeed))
	    		  //drink potion if has it (make sure attack_target is absent and has potion in inventory)
	    		  //eat food if has it
	    );
	}
	protected ImmutableList<Pair<Integer, ? extends Task<? super AbstractNpcEntity>>> sleep(float walkingSpeed) {
		return ImmutableList.of(
	    		  Pair.of(2, new StayNearPointTask(MemoryModuleType.HOME, walkingSpeed, 9, 100, 1200)), 
	    		  Pair.of(3, new SleepAtHomeTask()),
	    		  Pair.of(99, new UpdateActivityTask())
	    		  //if talked too randomly wake up and if they do, get annoyed
	    		  //if alarm is raised, randomly wake up
	    );
	}
	protected ImmutableList<Pair<Integer, ? extends Task<? super AbstractNpcEntity>>> free(float walkingSpeed) {
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
	protected ImmutableList<Pair<Integer, ? extends Task<? super AbstractNpcEntity>>> guard(float walkingSpeed) {
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
	protected ImmutableList<Pair<Integer, ? extends Task<? super AbstractNpcEntity>>> train(float walkingSpeed) {
		return ImmutableList.of(
	    		  lookAtAnyNpcs()
	    		  //if alarm is raised go out and fight
	    		  //train
	    		  //if weapon breaks, get a new one
	    		  //if interacted with stop what they're doing or talk to captain
	    );
	}
}
