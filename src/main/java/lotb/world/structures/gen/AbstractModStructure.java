package lotb.world.structures.gen;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public abstract class AbstractModStructure extends Structure<NoFeatureConfig> {
	private int distance; //essentially a structures exclusion zone
	private int seperation;

	public AbstractModStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn,int _dist,int _sep) {
		super(configFactoryIn);
		distance= _dist;
		seperation=_sep;
	}

	/**should this structure spawn*/
	@Override
	public boolean func_225558_a_(BiomeManager manager, ChunkGenerator<?> generator, Random rand, int x, int z, Biome biome) {
		ChunkPos pos = getStartPositionForPosition(generator,rand,x,z,0,0);
		return pos.x==x && pos.z==z && generator.hasStructure(biome, this);
	}
	@Override
	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
		int k = x + distance * spacingOffsetsX;
		int l = z + distance * spacingOffsetsZ;
		int i1 = k < 0 ? k - distance + 1 : k;
		int j1 = l < 0 ? l - distance + 1 : l;
		int k1 = i1 / distance;
		int l1 = j1 / distance;
		((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, this.getSeedModifier());
		k1 = k1 * distance;
		l1 = l1 * distance;
		k1 = k1 + random.nextInt(distance - seperation);
		l1 = l1 + random.nextInt(distance - seperation);
		return new ChunkPos(k1, l1);
	}
	/*-------------registry stuff-------------*/
	@Override public String getStructureName() {
		return getRegistryName().toString();
	}
	@Override public int getSize() {
		return 1;
	}
	protected abstract int getSeedModifier();
}
