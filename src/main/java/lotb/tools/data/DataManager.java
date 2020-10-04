package lotb.tools.data;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import lotb.LotbMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSet;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataManager {
	@SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
		LotbMod.LOGGER.error("gathering data");
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new ModLootTableProvider(generator));
    }
	
	public static class ModLootTableProvider extends LootTableProvider {
		private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> lootTableGenerators =
				ImmutableList.of(Pair.of(ModBlockDrops::new, LootParameterSets.BLOCK));
				
		public ModLootTableProvider(DataGenerator _gen) {
			super(_gen);
		}
		
		@Override protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
			return lootTableGenerators;
		}
	}
}
