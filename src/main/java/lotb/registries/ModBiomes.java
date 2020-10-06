package lotb.registries;

import lotb.LotbMod;
import lotb.world.biomes.*;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, LotbMod.MODID);

    public static final RegistryObject<Biome> SHIRE = BIOMES.register("shire", ShireBiome::new);
    public static final RegistryObject<Biome> ROHAN = BIOMES.register("rohan", RohanBiome::new);
    public static final RegistryObject<Biome> MORDOR = BIOMES.register("mordor", MordorBiome::new);
    public static final RegistryObject<Biome> GONDOR = BIOMES.register("gondor", GondorBiome::new);
    public static final RegistryObject<Biome> HARGONDOR = BIOMES.register("hargondor", HarGondorBiome::new);
    public static final RegistryObject<Biome> MIRKWOOD = BIOMES.register("mirkwood", MirkwoodBiome::new);
    public static final RegistryObject<Biome> MISTY_MOUNTAINS = BIOMES.register("misty_mts", MistyMountainsBiome::new);

    public static void registerBiomes() {
        registerBiome(SHIRE.get(), BiomeDictionary.Type.PLAINS);
        registerBiome(ROHAN.get(), BiomeDictionary.Type.PLAINS);
        registerBiome(MORDOR.get(), BiomeDictionary.Type.PLAINS);
        registerBiome(GONDOR.get(), BiomeDictionary.Type.PLAINS);
        registerBiome(HARGONDOR.get(), BiomeDictionary.Type.PLAINS);
        registerBiome(MIRKWOOD.get(), BiomeDictionary.Type.PLAINS);
        registerBiome(MISTY_MOUNTAINS.get(), BiomeDictionary.Type.PLAINS);
        //TODO Change the types
    }

    private static void registerBiome(Biome biome, BiomeDictionary.Type... types) {
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
    }

}
