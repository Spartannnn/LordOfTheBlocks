package lotb.registries;

import lotb.LotbMod;
import lotb.world.biomes.GondorBiome;
import lotb.world.biomes.HarGondorBiome;
import lotb.world.biomes.MirkwoodBiome;
import lotb.world.biomes.MistyMountainsBiome;
import lotb.world.biomes.MordorBiome;
import lotb.world.biomes.RohanBiome;
import lotb.world.biomes.ShireBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {
	public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES,LotbMod.MODID);
	
	public static final Biome SHIRE = new ShireBiome();
	public static final Biome ROHAN = new RohanBiome();
	public static final Biome MORDOR = new MordorBiome();
	public static final Biome GONDOR = new GondorBiome();
	public static final Biome HARGONDOR = new HarGondorBiome();
	public static final Biome MIRKWOOD = new MirkwoodBiome();
	public static final Biome MISTY_MOUNTAINS = new MistyMountainsBiome();
	
	public static void RegisterBiomes() {
		BIOMES.register("shire",() -> SHIRE);
		BIOMES.register("rohan",() -> ROHAN);
		BIOMES.register("mordor",() -> MORDOR);
		BIOMES.register("gondor",() -> GONDOR);
		BIOMES.register("hargondor",() -> HARGONDOR);
		BIOMES.register("mirkwood",() -> MIRKWOOD);
		BIOMES.register("misty_mts",() -> MISTY_MOUNTAINS);
	}
}
