package lotb.world.middleearth;


import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

public class MiddleEarthChunkGenerator extends NoiseChunkGenerator<GenerationSettings> {
	private final OctavesNoiseGenerator depthNoise;
	private static final float[] GRID_OF_POINT_DISTANCES = Util.make(new float[25], (field) -> {
		for (int x = -2; x <= 2; ++x) {
			for (int y = -2; y <= 2; ++y) {
				float distance_between_points_in_a_grid_in_range_0_to_10 = 10.0F / MathHelper.sqrt(x * x + y * y + 0.2F);
				field[x + 2 + (y + 2) * 5] = distance_between_points_in_a_grid_in_range_0_to_10;
			}
		}
	});

	public MiddleEarthChunkGenerator(IWorld worldIn, BiomeProvider provider, GenerationSettings settingsIn) {
		super(worldIn, provider, 4, 8, 256, settingsIn, true);
		this.randomSeed.skip(2620);
		this.depthNoise = new OctavesNoiseGenerator(this.randomSeed, 15, 0);
	}

	@Override protected void fillNoiseColumn(double[] noiseColumn, int noiseX, int noiseZ) {
		this.calcNoiseColumn(noiseColumn, noiseX, noiseZ, 684.412F, 684.412F, 8.555149841308594D,4.277574920654297D, 3, -10);
	}
	@Override protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ) {
		double[] return_pair = new double[2]; //returns chunk height and biome scale
		float total_biome_scale = 0.0F;
		float total_biome_depth = 0.0F;
		float total_point_depth = 0.0F;
		int sea_level = this.getSeaLevel();
		float origin_biome_depth = this.biomeProvider.getNoiseBiome(noiseX, sea_level, noiseZ).getDepth();

		for (int k = -2; k <= 2; ++k) {
			for (int l = -2; l <= 2; ++l) {
				Biome biome = this.biomeProvider.getNoiseBiome(noiseX + k, sea_level, noiseZ + l);
				float biome_depth = biome.getDepth();
				float biome_scale = biome.getScale();

				float point_depth_in_biome = GRID_OF_POINT_DISTANCES[k + 2 + (l + 2) * 5] / (biome_depth + 2.0F);
				if (biome.getDepth() > origin_biome_depth) {
					point_depth_in_biome /= 2.0F;
				}

				total_biome_scale  += biome_scale * point_depth_in_biome;
				total_biome_depth += biome_depth * point_depth_in_biome;
				total_point_depth += point_depth_in_biome;
			}
		}

		total_biome_scale /= total_point_depth;	//get average biome scale
		total_biome_depth /= total_point_depth; //get average biome depth
		total_biome_scale = total_biome_scale * 0.9F + 0.1F;
		total_biome_depth = (total_biome_depth * 4.0F - 1.0F) / 8.0F;
		return_pair[0] = total_biome_depth + this.getNoiseDepthAt(noiseX, noiseZ);
		return_pair[1] = total_biome_scale;
		return return_pair;
	}
	@Override protected double func_222545_a(double chunk_height, double biome_scale, int i) {
		double h = (i - (8.5D + chunk_height * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / biome_scale;
		if (h < 0.0D) h *= 4.0D;//multiply subzero values by 4?
		return h;
	}

	private double getNoiseDepthAt(int noiseX, int noiseZ) {
		double depth = depthNoise.getValue(noiseX * 200, 10.0D, noiseZ * 200, 1.0D, 0.0D, true)
					* 65535.0D / 8000.0D;		//get the noise depth at x*200, 10, y*200, multiply by 65535, divide by 8000
		if (depth < 0.0D)
			depth = -depth * 0.3D;			//clamp depth and make and invert?
		depth *= 3.0D; //scale and bias
		depth -= 2.0D;
		if (depth < 0.0D)  depth = depth / 28.0D;//compress sub zero
		 else {
			if (depth > 1.0D) depth = 1.0D; //clamp depth
			depth = depth / 40.0D;			//make sure depth is between 0 and 1/40
		}
		return depth;
	}

	//void generateBiomes(IChunk chunk);
	//void makeBase(IWorld world, IChunk chunk);
	//void generateSurface(WorldGenRegion world, IChunk chunk);
	//void func_225550_a_(BiomeManager biomes, IChunk chunk, GenerationStage.Carving type);
	//void decorate(WorldGenRegion region);
	//generateStructures(BiomeManager biomes, IChunk chunk, ChunkGenerator<?> generator, TemplateManager templates);
	//void generateStructureStarts(IWorld world, IChunk chunk);
	//void spawnMobs(ServerWorld worldIn, boolean hostile, boolean peaceful);
	//void spawnMobs(WorldGenRegion region);

	@Override public int getGroundHeight() {
		return this.world.getSeaLevel() + 1;
	}
	@Override public int getSeaLevel() {
		return 63;
	}
}
