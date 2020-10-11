package lotb.entities.npc.ai.brain.sensors;

import com.google.common.collect.ImmutableSet;
import lotb.entities.npc.AbstractNPCEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;
import java.util.Set;

public class NearbyHostilesSensor extends Sensor<AbstractNPCEntity> {
	private static final ImmutableSet<EntityType<?>> HOSTILES = ImmutableSet.<EntityType<?>>builder()
			.add(EntityType.SPIDER)
			.add(EntityType.CAVE_SPIDER)
			.add(EntityType.ILLUSIONER)
			.add(EntityType.VINDICATOR)
			.add(EntityType.PILLAGER)
			.add(EntityType.WITCH)
			.add(EntityType.VEX)
			.add(EntityType.EVOKER)
			.add(EntityType.RAVAGER)
			.add(EntityType.ILLUSIONER)
			.build();
			/*.put(EntityType.DROWNED)
			.put(EntityType.EVOKER, 12.0F)
			.put(EntityType.HUSK, 8.0F)
			.put(EntityType.ILLUSIONER, 12.0F)
			.put(EntityType.PILLAGER, 15.0F)
			.put(EntityType.RAVAGER, 12.0F)
			.put(EntityType.VEX, 8.0F)
			.put(EntityType.VINDICATOR, 10.0F)
			.put(EntityType.ZOMBIE, 8.0F)
			.put(EntityType.ZOMBIE_VILLAGER, 8.0F).build();*/


	@Override public Set<MemoryModuleType<?>> getUsedMemories() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_HOSTILE);
	}

	@Override protected void update(ServerWorld worldIn, AbstractNPCEntity owner) {
		owner.getBrain().setMemory(MemoryModuleType.NEAREST_HOSTILE, filterEntities(owner));
	}

	private Optional<LivingEntity> filterEntities(AbstractNPCEntity owner) {
		return owner.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).flatMap((entities) -> {
			return entities.stream().filter((suspect) ->{
				return this.isHostile(owner, suspect);
			}).min((hostile1, hostile2) -> {
				return this.getDistance(owner, hostile1, hostile2);
			});
		});
	}

	private int getDistance(AbstractNPCEntity owner, LivingEntity hostile1, LivingEntity hostile2) {
		return MathHelper.floor(hostile1.getDistanceSq(owner) - hostile2.getDistanceSq(owner));
	}

	private boolean isHostile(AbstractNPCEntity owner, LivingEntity suspect) {
		/*Pair<Integer,Byte> relation = owner.getCharacterRelation(suspect);
		if (relation == null)
			return HOSTILES.contains(suspect.getType());
		return relation.getFirst() < -10;*/
		return false;
	}
}
