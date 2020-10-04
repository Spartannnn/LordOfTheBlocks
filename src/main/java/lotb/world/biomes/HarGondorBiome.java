package lotb.world.biomes;

import lotb.world.ModOreGen;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class HarGondorBiome extends Biome {

	static final SurfaceBuilderConfig surface = new SurfaceBuilderConfig(Blocks.SAND.getDefaultState(),
			 															 Blocks.DIRT.getDefaultState(),
			 															 Blocks.SAND.getDefaultState());
	public HarGondorBiome() {
		super(new Builder()
				.surfaceBuilder(new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilder.DEFAULT, surface))
				.precipitation(RainType.NONE).downfall(0.0f)
				.category(Category.PLAINS)
				.depth(0.2f)
				.scale(0.2f)
				.temperature(1.5f)
				.waterColor(0x45C1F2)
				.waterFogColor(0x06142e)
				.parent(null));
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.addDeadBushes(this);
		DefaultBiomeFeatures.addSparseGrass(this);
		DefaultBiomeFeatures.addDefaultFlowers(this);
		DefaultBiomeFeatures.addBamboo(this);
		DefaultBiomeFeatures.addSavannaTrees(this);
		ModOreGen.addModOres(this);  
		
		//this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(ModEntities.BADGER,100,1,5));
	}
}
