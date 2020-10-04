package lotb.lore;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CharacterData {
	Map<Faction,Integer> faction_reputations = new EnumMap<Faction,Integer>(Faction.class);
	byte notarity;
	
	@SubscribeEvent
    public static void playerDeath(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
        	PlayerEntity playerold = event.getOriginal();
        	PlayerEntity playernew = event.getPlayer();
        }
    }
	@SubscribeEvent
    public static void attachData(AttachCapabilitiesEvent<CharacterData> event) {
    }
}
