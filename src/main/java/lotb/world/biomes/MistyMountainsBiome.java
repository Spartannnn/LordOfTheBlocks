package lotb.world.biomes;

import lotb.registries.ModEntities;
import lotb.registries.ModStructures;
import lotb.world.ModOreGen;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class MistyMountainsBiome extends Biome {

	static final SurfaceBuilderConfig surface = SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG;
	
	public MistyMountainsBiome() {
		super(new Builder()
				.surfaceBuilder(new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilder.MOUNTAIN, surface))
				.precipitation(RainType.SNOW).downfall(0.3f)
				.category(Category.PLAINS)
				.depth(1.4f)
				.scale(0.5f)
				.temperature(0.2f)
				.waterColor(0x6681E3)
				.waterFogColor(0x0d0d2e)
				.parent(null));
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.addFreezeTopLayer(this);
		DefaultBiomeFeatures.addBlueIce(this);
		DefaultBiomeFeatures.addTaigaGrassAndMushrooms(this);
		DefaultBiomeFeatures.addSparseBerryBushes(this);
		DefaultBiomeFeatures.addScatteredSpruceTrees(this);
		ModOreGen.addModOres(this);
		ModOreGen.addMithril(this);
		ModStructures.addStructure(this,ModStructures.DWARVEN_MINESHAFT_STRUCTURE);
		
		addSpawn(EntityClassification.CREATURE, new SpawnListEntry(ModEntities.BADGER,100,1,5));
	}
}
