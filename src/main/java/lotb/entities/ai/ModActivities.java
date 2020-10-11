package lotb.entities.ai;


import lotb.LotbMod;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModActivities extends Activity {
	public static final DeferredRegister<Activity> ACTIVITIES = new DeferredRegister<>(ForgeRegistries.ACTIVITIES,LotbMod.MODID);
	
	public static final Activity FREE 	= register("free");
	public static final Activity PATROL	= register("patrol");
	public static final Activity GUARD	= register("guard");
	public static final Activity WORK	= register("work");
	
	/*========registry stuff=========*/
	public ModActivities(String key) {
		super(key);
	}
    private static Activity register(String key) {
    	Activity activity = new Activity(key);
    	ACTIVITIES.register( key, () -> activity);
    	return activity;
    }
}
