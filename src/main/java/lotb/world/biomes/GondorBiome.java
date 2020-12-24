package lotb.world.biomes;

import lotb.registries.ModEntities;
import lotb.world.ModOreGen;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class GondorBiome extends Biome {
	static final SurfaceBuilderConfig surface = new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(),
			 															 Blocks.DIRT.getDefaultState(),
			 															 Blocks.SAND.getDefaultState());
	public GondorBiome() {
		super(new Builder()
				.surfaceBuilder(new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilder.DEFAULT, surface))
				.precipitation(RainType.SNOW).downfall(0.8f)
				.category(Category.PLAINS)
				.depth(0.2f)
				.scale(0.1f)
				.temperature(0.8f)
				.waterColor(0x459BED)
				.waterFogColor(0x060e2e)
				.parent(null));
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.addJungleGrass(this);
		DefaultBiomeFeatures.addTallGrass(this);
		DefaultBiomeFeatures.addTaigaLargeFerns(this);
		DefaultBiomeFeatures.addMushrooms(this);
		DefaultBiomeFeatures.addBirchTrees(this);
		DefaultBiomeFeatures.addExtraReedsAndPumpkins(this);
		ModOreGen.addModOres(this);
		
		this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(ModEntities.BADGER,100,1,5));
	}
}