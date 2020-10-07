package lotb.registries;


import lotb.LotbMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS,LotbMod.MODID);

	//entity
	public static final RegistryObject<SoundEvent> DEER_AMBIENT = register("deer_ambient");
	public static final RegistryObject<SoundEvent> DEER_HURT = register("deer_hurt");
	public static final RegistryObject<SoundEvent> DEER_DEATH = register("deer_death");

	//----------------registering----------------
	private static RegistryObject<SoundEvent> register(String key) {
		return SOUNDS.register(key, () -> new SoundEvent(new ResourceLocation(key)));
	}
}
