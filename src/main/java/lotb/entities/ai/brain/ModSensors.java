package lotb.entities.ai.brain;

import java.util.function.Supplier;

import lotb.LotbMod;
import lotb.entities.ai.brain.sensors.NearbyHostilesSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ModSensors<U extends Sensor<?>> extends SensorType<U> {
	public static final SensorType<NearbyHostilesSensor> NEARBY_HOSTILES = register("nearby_hostiles", NearbyHostilesSensor::new);
	
	public ModSensors(Supplier<U> sensor) {
		super(sensor);
	}
	
	protected static <U extends Sensor<?>> SensorType<U> register(String key, Supplier<U> sensors) {
		return Registry.register(Registry.SENSOR_TYPE, new ResourceLocation(key), new SensorType<>(sensors ));
	}
	public static void init ( ) {
		LotbMod.LOGGER.debug("inited");
	}
}
