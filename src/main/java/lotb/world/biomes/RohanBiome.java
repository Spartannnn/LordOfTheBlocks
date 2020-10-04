package lotb.world.biomes;

import lotb.registries.ModStructures;
import lotb.world.ModOreGen;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class RohanBiome extends Biome {
	
	static final SurfaceBuilderConfig surface = new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(),
			 															 Blocks.DIRT.getDefaultState(),
			 															 Blocks.COARSE_DIRT.getDefaultState());
	
	public RohanBiome() {
		super(new Builder()
				.surfaceBuilder(new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilder.DEFAULT, surface))
				.precipitation(RainType.RAIN).downfall(0.6f)
				.category(Category.PLAINS)
				.depth(0.4f)
				.scale(0.2f)
				.temperature(0.7f)
				.waterColor(0x4377E0)
				.waterFogColor(0x070730)
				.parent(null));
		
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.addJungleGrass(this);
		DefaultBiomeFeatures.addTallGrass(this);
		DefaultBiomeFeatures.addTaigaLargeFerns(this);
		DefaultBiomeFeatures.addMushrooms(this);
		DefaultBiomeFeatures.addReedsAndPumpkins(this);
		DefaultBiomeFeatures.addScatteredOakTrees(this);
		ModOreGen.addModOres(this);
		ModStructures.addStructure(this,ModStructures.ROHAN_FORT_CAMP);
		
		//addSpawn(EntityClassification.CREATURE, new SpawnListEntry(ModEntities.BADGER,100,1,5));
	}

}
