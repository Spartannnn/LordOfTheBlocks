package lotb.world.capabilities.relationship;

import com.google.common.collect.Maps;
import lotb.LotbMod;
import lotb.entities.npc.AbstractNPCEntity;
import lotb.entities.npc.IDataNPC;
import lotb.entities.npc.relationship.RelationShip;
import lotb.util.Valid;
import net.minecraft.world.World;

import java.util.Map;

public class PlayerNpcRelationShipImpl implements IPlayerNpcRelationShip {

    private Map<AbstractNPCEntity, RelationShip> relationShipMap;
    private Map<IDataNPC, RelationShip> data;

    public PlayerNpcRelationShipImpl() {
        this.relationShipMap = Maps.newHashMap();
    }


    @Override
    public Map<AbstractNPCEntity, RelationShip> getRelationShips() {
        return this.relationShipMap;
    }

    @Override
    public void setData(Map<IDataNPC, RelationShip> data) {
        this.data = data;
    }

    @Override
    public void loadData(World world) {
        Valid.notNull(data, "Can not load capability data, data is null");
        for(Map.Entry<IDataNPC, RelationShip> entry : this.data.entrySet()) {
            relationShipMap.put(entry.getKey().getNPCEntity(world), entry.getValue());
        }
        LotbMod.LOGGER.info("Loaded player capability data");
    }

    @Override
    public void addRelationShip(AbstractNPCEntity npc, RelationShip relationShip) {
        Valid.notNull(npc, "Can not add relation ship, npc is null");
        Valid.notNull(relationShip, "Can not add relation ship, relation ship object is null");
        if (this.relationShipMap.containsKey(npc))
            return;
        this.relationShipMap.put(npc, relationShip);
    }

    @Override
    public void setRelationShips(Map<AbstractNPCEntity, RelationShip> relationShips) {
        this.relationShipMap = relationShips;
    }

    @Override
    public void removeRelationShip(AbstractNPCEntity npc) {
        Valid.notNull(npc, "Can not remove relation ship, npc is null");
        if (!this.relationShipMap.containsKey(npc))
            return;
        this.relationShipMap.remove(npc);
    }

    @Override
    public void changeRelationShip(AbstractNPCEntity npc, RelationShip newRelationShip) {
        Valid.notNull(npc, "Can not change relation ship, npc is null");
        Valid.notNull(newRelationShip, "Can not change relation ship, relation ship object is null");
        if (!this.relationShipMap.containsKey(npc))
            return;
        this.relationShipMap.replace(npc, newRelationShip);
    }

    @Override
    public boolean hasRelationShip(AbstractNPCEntity npc) {
        Valid.notNull(npc, "Can not check if the player has a relation ship with the npc, npc is null");
        return this.relationShipMap.containsKey(npc);
    }

    @Override
    public RelationShip getRelationShip(AbstractNPCEntity npc) {
        Valid.notNull(npc, "Npc is null");
        return this.relationShipMap.get(npc);
    }


}
