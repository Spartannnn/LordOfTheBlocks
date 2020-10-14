package lotb.entities.npc.profesion;

import lotb.entities.npc.AbstractNPCEntity;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.world.World;

public abstract class AbstractProfession implements IProfession {

    protected Activity currentActivity;
    protected int tickTime;
    protected final int maxTickTime;

    public AbstractProfession(int maxTickTime) {
        this.maxTickTime = maxTickTime;
        this.tickTime = 0;
        this.currentActivity = null;
    }

    @Override
    public void tick(AbstractNPCEntity npc, World world) {
        /*this.tickTime--;
        if(this.tickTime == 0) {
            long gameTime = world.getGameTime();
            Activity byGameTime = Activity.getActivityByGameTime(gameTime);
            if(this.currentActivity != byGameTime)
                this.currentActivity = byGameTime;
            this.tickTime = maxTickTime;
        } else {
            this._tick(npc, world);
        }*/
        //TODO Change later
        _tick(npc, world);
    }

    public abstract void _tick(AbstractNPCEntity npc, World world);

    public enum Activity {

        SLEEPING(13000, 24000),
        FREE_TIME(0, 6000),
        ACTION(6001, 12999);

        public static final Activity[] VALUES = values();

        private final int startTime;
        private final int endTime;

        Activity(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public int getStartTime() {
            return startTime;
        }

        public static Activity getActivityByGameTime(long gameTime) {
            for(Activity activity : VALUES)
                if(gameTime > activity.startTime && gameTime < activity.endTime)
                    return activity;
            return FREE_TIME;
        }
    }
}
