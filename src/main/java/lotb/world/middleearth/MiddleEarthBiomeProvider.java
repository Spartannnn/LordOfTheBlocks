package lotb.world.middleearth;

import java.util.Set;
import java.util.function.LongFunction;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.IslandLayer;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraft.world.storage.WorldInfo;

public class MiddleEarthBiomeProvider extends BiomeProvider{
	private final Layer genBiomes;
	private Biome[] biomes;

	protected MiddleEarthBiomeProvider(Set<Biome> _biomes,WorldInfo _worldinfo, OverworldGenSettings _settings) {
		//send _biomes to super class as set and then conver to array for our class
		super(_biomes);
		biomes = _biomes.toArray(new Biome[_biomes.size()]);
		genBiomes = createGenerationLayers(_worldinfo.getGenerator(), _settings,(seedModSupplier) -> {
	         return new LazyAreaLayerContext(25, _worldinfo.getSeed(), seedModSupplier);
	    });
	}
	
	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
	    return genBiomes.func_215738_a(x, z);
	}
	
	public Layer createGenerationLayers(WorldType worldType, OverworldGenSettings _settings, LongFunction<IExtendedNoiseRandom<LazyArea>> areaFactory) {
		//generating parent and biome layers
		IAreaFactory<LazyArea> parentLayer = IslandLayer.INSTANCE.apply(areaFactory.apply(1));
		IAreaFactory<LazyArea> biomeLayer = new MiddleEarthBiomeLayer().apply(areaFactory.apply(200), parentLayer);
		biomeLayer = ZoomLayer.NORMAL.apply(areaFactory.apply(2001L), biomeLayer);
		biomeLayer = ZoomLayer.NORMAL.apply(areaFactory.apply(2002L), biomeLayer);
		biomeLayer = ZoomLayer.NORMAL.apply(areaFactory.apply(2003L), biomeLayer);
		biomeLayer = LayerUtil.repeat(2001L, ZoomLayer.NORMAL, biomeLayer, 3, areaFactory);
		//init layer and return
		return new Layer(biomeLayer);
	}
	//layer generator for our custom biomes
	public class MiddleEarthBiomeLayer implements IC0Transformer{
		@Override @SuppressWarnings("deprecation")
		public int apply(INoiseRandom context, int value) {
			return Registry.BIOME.getId(biomes[context.random(biomes.length)]);
		}
		
	}

}
