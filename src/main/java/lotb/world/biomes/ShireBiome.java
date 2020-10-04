package lotb.world.biomes;

import lotb.world.ModOreGen;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ShireBiome extends Biome {

	static final SurfaceBuilderConfig surface = new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(),
																		 Blocks.DIRT.getDefaultState(),
																		 Blocks.CLAY.getDefaultState());
	public ShireBiome() {
		super(new Builder()
				.surfaceBuilder(new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilder.DEFAULT, surface))
				.precipitation(RainType.RAIN).downfall(0.9f)
				.category(Category.PLAINS)
				.depth(0.1f)
				.scale(0.3f)
				.temperature(0.6f)
				.waterColor(0x3B6bED)
				.waterFogColor(0x070730)
				.parent(null));
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.addJungleGrass(this);
		DefaultBiomeFeatures.addTallGrass(this);
		DefaultBiomeFeatures.addTaigaLargeFerns(this);
		DefaultBiomeFeatures.addMushrooms(this);
		DefaultBiomeFeatures.addDefaultFlowers(this);
		DefaultBiomeFeatures.addReedsAndPumpkins(this);
		DefaultBiomeFeatures.addOakTreesFlowersGrass(this);
		DefaultBiomeFeatures.addBerryBushes(this);
		ModOreGen.addModOres(this);
		
		
		//this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(ModEntities.BADGER,100,1,5));
	}

}
