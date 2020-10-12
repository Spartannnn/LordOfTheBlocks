package lotb.world.capabilities;

import lotb.LotbMod;
import lotb.world.capabilities.relationship.PlayerNpcRelationShipSerializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LotbMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEvents {

    @SubscribeEvent
    public static void onAttach(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            PlayerNpcRelationShipSerializer serializer = new PlayerNpcRelationShipSerializer(event.getObject().world);
            event.addCapability(LotbMod.newLocation("player_npc_relationships"), serializer);
        }
    }


}
