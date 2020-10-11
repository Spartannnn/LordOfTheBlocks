package lotb.world.capabilities.relationship;

import lotb.util.Valid;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerNpcRelationShipStorage implements Capability.IStorage<IPlayerNpcRelationShip> {

    @Override
    public INBT writeNBT(Capability<IPlayerNpcRelationShip> capability, IPlayerNpcRelationShip instance, Direction side) {
        IPlayerNpcRelationShip playerNpcRelationShip = capability.getDefaultInstance();
        Valid.notNull(playerNpcRelationShip, "Capability instance is null for Player-NPC relationship");
        return playerNpcRelationShip.write();
    }

    @Override
    public void readNBT(Capability<IPlayerNpcRelationShip> capability, IPlayerNpcRelationShip instance, Direction side, INBT nbt) {
        IPlayerNpcRelationShip playerNpcRelationShip = capability.getDefaultInstance();
        Valid.notNull(playerNpcRelationShip, "Capability instance is null for Player-NPC relationship");
        playerNpcRelationShip.setRelationShips(IPlayerNpcRelationShip.read((ListNBT) nbt));
    }

}
