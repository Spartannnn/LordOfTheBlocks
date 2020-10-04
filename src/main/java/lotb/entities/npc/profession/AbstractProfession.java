package lotb.entities.npc.profession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import lotb.entities.npc.AbstractNpcEntity;
import lotb.registries.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.DummyTask;
import net.minecraft.entity.ai.brain.task.FirstShuffledTask;
import net.minecraft.entity.ai.brain.task.LookAtEntityTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class AbstractProfession {
	public static final ImmutableList<SensorType<? extends Sensor<? super AbstractNpcEntity>>> SENSORS = ImmutableList.of();
	public static final ImmutableList<MemoryModuleType<?>> MEMORIES = ImmutableList.of();
	protected static final Logger LOGGER = LogManager.getLogger();
	public World world;
	public AbstractNpcEntity entity;
	
	public AbstractProfession(AbstractNpcEntity _entity) {
		entity =_entity;
		world = _entity.world;
	}
	
	//interaction stuff
	public abstract boolean onRightClicked(PlayerEntity player, Hand hand);
	public abstract boolean onAttacked();
	public abstract void tick();
	public void kill() {
		entity = null;
	};
	
	//brain ai stuff
	public abstract Brain<AbstractNpcEntity> registerActivitiesOntoBrain (Brain<AbstractNpcEntity> brain);
	
	//tasks
	protected static Pair<Integer, Task<LivingEntity>> lookAtAnyNpcs() {
		return Pair.of(5, new FirstShuffledTask<>(
				ImmutableList.of(
						Pair.of(new LookAtEntityTask(EntityType.VILLAGER, 8.0F), 2), 
						Pair.of(new LookAtEntityTask(ModEntities.HOBBIT, 8.0F), 2), 
						Pair.of(new LookAtEntityTask(ModEntities.HUMAN, 8.0F), 2), 
						Pair.of(new LookAtEntityTask(ModEntities.ORC, 8.0F), 2), 
						Pair.of(new LookAtEntityTask(ModEntities.ELF, 8.0F), 2), 
						Pair.of(new LookAtEntityTask(ModEntities.DWARF, 8.0F), 2), 
						Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0F), 2), 
						Pair.of(new DummyTask(30, 60), 8))));
	}
}
