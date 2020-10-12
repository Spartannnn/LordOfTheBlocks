package lotb.world.capabilities;

import lotb.world.capabilities.relationship.IPlayerNpcRelationShip;
import lotb.world.capabilities.relationship.PlayerNpcRelationShipFactory;
import lotb.world.capabilities.relationship.PlayerNpcRelationShipStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityRegistering {

    public static void register() {
        CapabilityManager.INSTANCE.register(IPlayerNpcRelationShip.class, new PlayerNpcRelationShipStorage(), new PlayerNpcRelationShipFactory());
    }

}
