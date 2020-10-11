package lotb.util.data;

import lotb.LotbMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataManager {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        LotbMod.LOGGER.error("gathering data");
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new ModLootTables(generator));
    }

}
