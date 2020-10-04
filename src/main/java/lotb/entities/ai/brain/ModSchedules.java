package lotb.entities.ai.brain;

import lotb.LotbMod;
import net.minecraft.entity.ai.brain.schedule.Schedule;

public class ModSchedules extends Schedule {
	public static final Schedule SOLDGIER 		= register("soldgier")
													.add(0, ModActivities.FREE)
													.add(4000, ModActivities.GUARD)
													.add(8000, ModActivities.PATROL)
													.add(12000, ModActivities.REST).build();
	public static final Schedule SOLDGIER_NIGHT = register("soldgier_night")
													.add(0, ModActivities.IDLE).build();
	public static void init ( ) {
		LotbMod.LOGGER.debug("inited");
	}
}
