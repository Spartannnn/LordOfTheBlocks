package lotb.entities.npc;

import net.minecraft.world.World;

public interface IDataNPC {

    AbstractNPCEntity getNPCEntity(World world);

    class IDataNPCImpl implements IDataNPC {

        private final int entityId;

        public IDataNPCImpl(int entityId) {
            this.entityId = entityId;
        }

        @Override
        public AbstractNPCEntity getNPCEntity(World world) {
            return (AbstractNPCEntity) world.getEntityByID(this.entityId);
        }
    }


}
