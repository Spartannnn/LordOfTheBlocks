package lotb.world.structures.config;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.feature.IFeatureConfig;

public class MiddleEarthStructureConfig implements IFeatureConfig {
	public int chance = 25;
	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return null;
	}
}
