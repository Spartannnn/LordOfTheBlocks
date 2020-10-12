package lotb.world.capabilities;

import net.minecraft.world.World;

import java.util.Map;

public interface ICapabilityData<T, U> {

    void setData(Map<T, U> data);

    void loadData(World world);

}
