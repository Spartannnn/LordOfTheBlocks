package lotb.entities.npc;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class Orc extends AbstractNpcEntity {

	public Orc(EntityType<? extends Orc> type, World worldIn) {
		super(type,worldIn);
	}

	/*@Override public AgeableEntity createChild(AgeableEntity ageable) {
	Hobbit child = new Hobbit(ModEntities.HOBBIT,world);
	child.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(child)), SpawnReason.BREEDING, null, null);
	return child;
}*/

}
