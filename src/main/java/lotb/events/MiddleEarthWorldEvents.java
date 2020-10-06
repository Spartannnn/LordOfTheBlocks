package lotb.events;

import lotb.LotbMod;
import lotb.world.middleearth.MiddleEarth;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LotbMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MiddleEarthWorldEvents {

    //Insert here the structure id. Default mc structure ids can be found in the "Features" class
    private static final String[] STRUCTURES_LIST = {"dwarven_mineshaft", "rohan_fort_camp"};

    @SubscribeEvent
    public static void onLivingSpawn(LivingSpawnEvent event) {
        if (MiddleEarth.isMiddleEarthWorld(event.getWorld())) {
            Class<? extends LivingEntity> clazz = event.getEntityLiving().getClass();
            if (MiddleEarth.BLACK_LIST_ENTITIES.contains(clazz))
                event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPortalSpawn(BlockEvent.PortalSpawnEvent event) {
        if (MiddleEarth.isMiddleEarthWorld(event.getWorld())) {
            NetherPortalBlock.Size size = event.getPortalSize();
            if (size.getWidth() == 2 && size.getHeight() == 3)
                event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onStructureSpawn(ChunkEvent event) {
        if (MiddleEarth.isMiddleEarthWorld(event.getWorld())) {
            if (event.getChunk().getStatus() == ChunkStatus.STRUCTURE_STARTS) {
                IChunk chunk = event.getChunk();
                for (String keys : chunk.getStructureStarts().keySet()) {
                    if (containsInStructureList(keys))
                        chunk.getStructureStarts().remove(keys);
                }
            }
        }
    }

    private static boolean containsInStructureList(String str) {
        for (String s : STRUCTURES_LIST)
            if (s.equals(str))
                return true;
        return false;
    }

}
