package lotb.entities.npc.relationship;

import com.google.common.collect.Maps;
import lotb.LotbMod;
import lotb.entities.npc.AbstractNPCEntity;
import lotb.util.Valid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.Map;

public class NPCRelationShipManager {

    private Map<AbstractNPCEntity, RelationShip> relationShips;

    public NPCRelationShipManager() {
        this.relationShips = Maps.newHashMap();
    }

    public void setRelationShips(Map<AbstractNPCEntity, RelationShip> relationShips) {
        this.relationShips = relationShips;
    }

    public void addRelationShip(AbstractNPCEntity other, RelationShip relationShip) {
        Valid.notNull(other, "Can not add relation ship, npc is null");
        Valid.notNull(relationShip, "Can not add relation ship, relation ship object is null");
        if(this.relationShips.containsKey(other))
            return;
        this.relationShips.put(other, relationShip);
    }

    public void removeRelationShip(AbstractNPCEntity other) {
        Valid.notNull(other, "Can not remove relation ship, npc is null");
        if(!this.relationShips.containsKey(other))
            return;
        this.relationShips.remove(other);
    }

    public void changeRelationShip(AbstractNPCEntity other, RelationShip newRelationShip) {
        Valid.notNull(other, "Can not change relation ship, npc is null");
        Valid.notNull(newRelationShip, "Can not change relation ship, relation ship object is null");
        if(!this.relationShips.containsKey(other))
            return;
        this.relationShips.replace(other, newRelationShip);
    }

    public boolean hasRelationShip(AbstractNPCEntity other) {
        Valid.notNull(other, "Can not check if the npc has a relation ship with the other npc, npc is null");
        return this.relationShips.containsKey(other);
    }

    public RelationShip getRelationShip(AbstractNPCEntity other) {
        Valid.notNull(other, "Npc is null");
        return this.relationShips.get(other);
    }

    public ListNBT write() {
        ListNBT list = new ListNBT();
        for (Map.Entry<AbstractNPCEntity, RelationShip> entry : this.relationShips.entrySet()) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("NpcID", entry.getKey().getEntityId());
            nbt.putInt("RelationShipOrdinal", entry.getValue().ordinal());
            list.add(nbt);
        }
        return list;

    }

    public static Map<AbstractNPCEntity, RelationShip> read(ListNBT list) {
        Map<AbstractNPCEntity, RelationShip> relationShips = Maps.newHashMap();
        ClientWorld world = Minecraft.getInstance().world;
        Valid.notNull(world, "Can not load player data, world is null. Class: NPCRelationShipManager");
        list.forEach(entry -> {
            if (!(entry instanceof CompoundNBT)) {
                LotbMod.LOGGER.error("Illegal entry in player data list, can not read entity data");
                throw new IllegalArgumentException();
            }
            CompoundNBT tag = (CompoundNBT) entry;
            int npcId = tag.getInt("NpcID");
            int relationShipOrdinal = tag.getInt("RelationShipOrdinal");
            relationShips.put((AbstractNPCEntity) world.getEntityByID(npcId), RelationShip.VALUES[relationShipOrdinal]);
        });
        return relationShips;
    }

}
