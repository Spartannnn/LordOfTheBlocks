package lotb.entities.npc;

import lotb.entities.npc.profesion.KnightProfession;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class DwarfNPCEntity extends AbstractNPCEntity {

    public DwarfNPCEntity(EntityType<? extends DwarfNPCEntity> type, World worldIn) {
        super(type, worldIn, "ISpartann", new KnightProfession());
    }
}
