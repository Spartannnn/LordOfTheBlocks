package lotb.world.capabilities.relationship;

import lotb.LotbMod;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerNpcRelationShipSerializer implements ICapabilitySerializable<ListNBT> {

    private IPlayerNpcRelationShip playerNpcRelationShip;
    private World world;

    public PlayerNpcRelationShipSerializer(World world) {
        this.world = world;
        this.playerNpcRelationShip = new PlayerNpcRelationShipImpl();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return IPlayerNpcRelationShip.Capability.get().orEmpty(cap, LazyOptional.of(() -> playerNpcRelationShip));
    }

    @Override
    public ListNBT serializeNBT() {
        return this.playerNpcRelationShip.write();
    }



    @Override
    public void deserializeNBT(ListNBT nbt) {
        this.playerNpcRelationShip.setData(IPlayerNpcRelationShip.readData(nbt));
        this.playerNpcRelationShip.loadData(this.world);
        LotbMod.LOGGER.debug("Loaded player data: Player-NPC relationship");
    }

}
