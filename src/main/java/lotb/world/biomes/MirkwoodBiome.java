package lotb.world.biomes;

import lotb.world.ModOreGen;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MirkwoodBiome extends Biome {

	static final SurfaceBuilderConfig surface = new SurfaceBuilderConfig(Blocks.PODZOL.getDefaultState(),
																		 Blocks.DIRT.getDefaultState(),
																		 Blocks.GRAVEL.getDefaultState());
	
	public MirkwoodBiome() {
		super(new Builder()
				.surfaceBuilder(new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilder.DEFAULT, surface))
				.precipitation(RainType.RAIN).downfall(0.6f)
				.category(Category.PLAINS)
				.depth(0.1f)
				.scale(0.2f)
				.temperature(0.45f)
				.waterColor(0x6653C9)
				.waterFogColor(0x0f0424)
				.parent(null));
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.addJungleGrass(this);
		DefaultBiomeFeatures.addTallGrass(this);
		DefaultBiomeFeatures.addTaigaLargeFerns(this);
		DefaultBiomeFeatures.addMushrooms(this);
		DefaultBiomeFeatures.addReedsAndPumpkins(this);
		DefaultBiomeFeatures.addBerryBushes(this);
		ModOreGen.addModOres(this);  
		addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, 
				Feature.DARK_OAK_TREE.withConfiguration(DefaultBiomeFeatures.DARK_OAK_TREE_CONFIG)
									 .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(24, 0.2F, 4))));
  
		
		//this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(ModEntities.BADGER,100,1,5));
	}
	@OnlyIn(Dist.CLIENT)
	@Override public int getGrassColor(double posX, double posZ) {
		int i = super.getGrassColor(posX, posZ);
		return (i & 16711422) + 2634762 >> 1;
	}
}
