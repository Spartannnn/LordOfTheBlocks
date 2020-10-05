package lotb.world.biomes;

import lotb.registries.ModBlocks;
import lotb.world.ModOreGen;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MordorBiome extends Biome {

	static final SurfaceBuilderConfig surface = new SurfaceBuilderConfig(ModBlocks.MORDOR_ROCK.get().getDefaultState(),
																		 ModBlocks.MORDOR_ROCK.get().getDefaultState(),
																		 ModBlocks.MORDOR_ROCK.get().getDefaultState());
	public MordorBiome() {
		super(new Builder()
				.surfaceBuilder(new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilder.DEFAULT, surface))
				.precipitation(RainType.RAIN).downfall(0.01f)
				.category(Category.PLAINS)
				.depth(1.6f)
				.scale(0.01f)
				.temperature(1.0f)
				.waterColor(0x947c81)
				.waterFogColor(0x26050c)
				.parent(null));
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addOres(this);
		ModOreGen.addModOres(this);
		
		//this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(ModEntities.BADGER,100,1,5));
	}
	@Override
	public Biome getRiver() {
		return Biomes.RIVER;
	}
	@OnlyIn(Dist.CLIENT) @Override
	public int getSkyColor() {
		return 0xad877d;
	}
}
