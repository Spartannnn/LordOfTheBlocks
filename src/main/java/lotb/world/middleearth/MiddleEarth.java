package lotb.world.middleearth;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import com.google.common.collect.Lists;
import lotb.registries.ModBiomes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;

public class MiddleEarth extends WorldType {

	public static final List<Class<? extends LivingEntity>> BLACK_LIST_ENTITIES = Lists.newArrayList(WanderingTraderEntity.class, TraderLlamaEntity.class, PillagerEntity.class);

	public MiddleEarth() {
		super("MiddleEarth");
	}
	
	@Override
	public ChunkGenerator<?> createChunkGenerator(World _world) {
		OverworldGenSettings settings = new OverworldGenSettings();
		Set<Biome> biomeList = ImmutableSet.of(	ModBiomes.SHIRE.get(),
												ModBiomes.ROHAN.get(),
												ModBiomes.MORDOR.get(),
												ModBiomes.GONDOR.get(),
												ModBiomes.HARGONDOR.get(),
												ModBiomes.MIRKWOOD.get(),
												ModBiomes.MISTY_MOUNTAINS.get(),
												Biomes.RIVER);

		//return new MiddleEarthChunkGenerator(_world, new SingleBiomeProvider(single), settings);
		return new MiddleEarthChunkGenerator(_world, new MiddleEarthBiomeProvider(biomeList,_world.getWorldInfo(),settings), settings);
	}

	public static boolean isMiddleEarthWorld(IWorld world) {
		if(world == null)
			return false;
		return world.getWorld().getWorldType() instanceof MiddleEarth;
	}

}
