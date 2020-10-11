package lotb.entities.npc.profesion;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import lotb.entities.npc.AbstractNPCEntity;
import lotb.registries.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.DummyTask;
import net.minecraft.entity.ai.brain.task.FirstShuffledTask;
import net.minecraft.entity.ai.brain.task.LookAtEntityTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IProfession {

    boolean onRightClick(AbstractNPCEntity npc, ItemStack stack, PlayerEntity player, World world);

    void onAttacked(AbstractNPCEntity npc, PlayerEntity player, World world);

    void tick(AbstractNPCEntity npc, World world);

    void kill(AbstractNPCEntity npc, World world);

    Brain<AbstractNPCEntity> registerActivitiesOntoBrain(AbstractNPCEntity npc, Brain<AbstractNPCEntity> brain);

    default Pair<Integer, Task<LivingEntity>> lookAtAnyNpcs() {
        return Pair.of(5, new FirstShuffledTask<>(
                ImmutableList.of(
                        Pair.of(new LookAtEntityTask(EntityType.VILLAGER, 8.0F), 2),
                        /*Pair.of(new LookAtEntityTask(ModEntities.HOBBIT, 8.0F), 2),
                        Pair.of(new LookAtEntityTask(ModEntities.HUMAN, 8.0F), 2),
                        Pair.of(new LookAtEntityTask(ModEntities.ORC, 8.0F), 2),
                        Pair.of(new LookAtEntityTask(ModEntities.ELF, 8.0F), 2),*/
                        Pair.of(new LookAtEntityTask(ModEntities.DWARF, 8.0F), 2),
                        Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0F), 2),
                        Pair.of(new DummyTask(30, 60), 8))));
    }

}
