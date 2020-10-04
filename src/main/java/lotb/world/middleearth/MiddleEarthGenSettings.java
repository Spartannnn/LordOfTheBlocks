package lotb.world.middleearth;

import net.minecraft.world.gen.OverworldGenSettings;

public class MiddleEarthGenSettings extends OverworldGenSettings{
	@Override
	public int getBiomeSize() {
		return 3;
	}
	
	@Override
	public int getRiverSize() {
		return 1;
	}
	
	@Override
	public int getBiomeId() {
		return -1;
	}
	
	@Override
	public int getBedrockFloorHeight() {
		return 0;
	}
}
