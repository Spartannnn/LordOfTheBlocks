package lotb.world.capabilities;

import lotb.util.Valid;
import lotb.world.capabilities.relationship.IPlayerNpcRelationShip;
import lotb.world.capabilities.relationship.PlayerNpcRelationShipImpl;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nonnull;

public class CapabilityAccessor {


    /**
     * Use this method to get the IPlayerNpcRelationShip which hold all data
     *
     * @param playerEntity the player entity
     * @return the {@link IPlayerNpcRelationShip} instance OR a new instance from {@link IPlayerNpcRelationShip} if the player has no capability
     */
    @Nonnull
    public static IPlayerNpcRelationShip getPlayerNpcRelationShipCapability(PlayerEntity playerEntity) {
        Valid.notNull(playerEntity, "PlayerEntity is null");
        return playerEntity.getCapability(IPlayerNpcRelationShip.Capability.PLAYER_NPC_RELATION_SHIP).orElse(new PlayerNpcRelationShipImpl());
    }

}
