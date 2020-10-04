package lotb.world.middleearth;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lotb.registries.ModBiomes;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;

public class MiddleEarth extends WorldType {

	public MiddleEarth() {
		super("MiddleEarth");
	}
	
	@Override
	public ChunkGenerator<?> createChunkGenerator(World _world) {
		OverworldGenSettings settings = new OverworldGenSettings();
		Set<Biome> biomeList = ImmutableSet.of(	ModBiomes.SHIRE,
												ModBiomes.ROHAN,
												ModBiomes.MORDOR,
												ModBiomes.GONDOR,
												ModBiomes.HARGONDOR,
												ModBiomes.MIRKWOOD,
												ModBiomes.MISTY_MOUNTAINS,
												Biomes.RIVER);

		//return new MiddleEarthChunkGenerator(_world, new SingleBiomeProvider(single), settings);
		return new MiddleEarthChunkGenerator(_world, new MiddleEarthBiomeProvider(biomeList,_world.getWorldInfo(),settings), settings);
	}

}
