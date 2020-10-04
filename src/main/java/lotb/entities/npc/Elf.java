package lotb.entities.npc;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class Elf extends AbstractNpcEntity {
	public Elf(EntityType<Elf> type, World worldIn) {
		super(type,worldIn);
	}
	@Override
	public boolean canUseOffhand() {
		return true;
	}
	
	
}
