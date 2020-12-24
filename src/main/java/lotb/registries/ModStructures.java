package lotb.registries;

import lotb.LotbMod;
import lotb.world.structures.gen.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModStructures {
	public static final DeferredRegister<Feature<?>> STRUCTURES = new DeferredRegister<>(ForgeRegistries.FEATURES,LotbMod.MODID);
	
	public static final Structure<NoFeatureConfig> DWARVEN_MINESHAFT_STRUCTURE = register("dwarven_mineshaft", new DwarvenMineshaft(NoFeatureConfig::deserialize));
	public static final Structure<NoFeatureConfig> ROHAN_FORT_CAMP	 = register("rohan_fort_camp", new RohanFortCamp(NoFeatureConfig::deserialize));
	public static final Structure<NoFeatureConfig> ROHAN_VILLAGE	 = register("rohan_village", new RohanVillage(NoFeatureConfig::deserialize));
	public static final Structure<NoFeatureConfig> ROHAN_BASTEON	 = register("rohan_basteon", new RohanBasteon(NoFeatureConfig::deserialize));
	public static final Structure<NoFeatureConfig> HOBBIT_HOLE	 = register("hobbit_hole", new HobbitHole(NoFeatureConfig::deserialize));
	
	private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
		STRUCTURES.register(key,() -> value);
		return value;
	}
	public static void addStructure (Biome _biome,Structure<NoFeatureConfig> featureType) {
		ConfiguredFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> feature = featureType.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
		ConfiguredPlacement<NoPlacementConfig> placement = Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
		_biome.addStructure(feature);
		_biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, feature.withPlacement(placement));
	}
}
