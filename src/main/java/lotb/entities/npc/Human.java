package lotb.entities.npc;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class Human extends AbstractNpcEntity {

	public Human(EntityType<? extends Human> type, World _world) {
		super(type,_world);
	}

	/*@Override public AgeableEntity createChild(AgeableEntity ageable) {
	Hobbit child = new Hobbit(ModEntities.HOBBIT,world);
	child.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(child)), SpawnReason.BREEDING, null, null);
	return child;
}*/

}
