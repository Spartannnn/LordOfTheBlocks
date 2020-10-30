package lotb.entities.npc.message;

import com.google.common.collect.Lists;
import lotb.entities.npc.AbstractNPCEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MessageChannel {

    private List<AbstractNPCEntity> connectedNpcs;
    private boolean open;

    public MessageChannel() {
        this.connectedNpcs = Lists.newArrayList();
        this.open = false;
    }

    public void connect(AbstractNPCEntity npc) {
        this.connectedNpcs.add(npc);
    }

    public void disconnect(AbstractNPCEntity npc) {
        this.connectedNpcs.remove(npc);
    }

    //Can be empty
    public List<AbstractNPCEntity> findNearby(World world, BlockPos pos, double radius) {
        List<AbstractNPCEntity> list = Lists.newArrayList();
        AxisAlignedBB aabb = new AxisAlignedBB(new BlockPos(pos.getX() - radius, pos.getY(), pos.getZ() - radius), new BlockPos(pos.getX() + radius, pos.getY(), pos.getZ() + radius));
        list.addAll(world.getEntitiesWithinAABB(AbstractNPCEntity.class, aabb, null));
        return list;
    }

    public void open() {
        if(open)
            return;
        this.open = true;
    }

    public void close() {
        if(!open)
            return;
        this.open = false;
    }

    public boolean isOpen() {
        return open;
    }
}
