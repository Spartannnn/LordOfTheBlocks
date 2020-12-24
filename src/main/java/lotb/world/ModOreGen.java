package lotb.world;

import lotb.registries.ModBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;

public class ModOreGen {
    @SuppressWarnings("rawtypes")
    public static void addModOres(Biome biome) {
        OreFeatureConfig prismarineOreFeature = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.PRISMARINE_ORE.get().getDefaultState(), 12);
        OreFeatureConfig quartzOreFeature = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.QUARTZ_ORE.get().getDefaultState(), 8);
        OreFeatureConfig glowstoneOreFeature = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.GLOWSTONE_ORE.get().getDefaultState(), 7);
        ConfiguredPlacement prismarinePlacement = Placement.COUNT_RANGE.configure(new CountRangeConfig(3, 0, 0, 43));
        ConfiguredPlacement quartzPlacement = Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 54));
        ConfiguredPlacement glowstonePlacement = Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 0, 0, 31));
        biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(prismarineOreFeature).withPlacement(prismarinePlacement));
        biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(quartzOreFeature).withPlacement(quartzPlacement));
        biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(glowstoneOreFeature).withPlacement(glowstonePlacement));
    }

    @SuppressWarnings("rawtypes")
    public static void addMithril(Biome biome) {
        OreFeatureConfig mithrilOreFeature = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.MITHRIL_ORE.get().getDefaultState(), 9);
        ConfiguredPlacement mithrilPlacement = Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 25));
        biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(mithrilOreFeature).withPlacement(mithrilPlacement));
    }
}
