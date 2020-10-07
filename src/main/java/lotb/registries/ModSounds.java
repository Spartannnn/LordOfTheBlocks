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

	//blocks
	public static final RegistryObject<SoundEvent> DWARF_DOOR_OPEN = register("dwarf_door_opening");
	//items
	public static final RegistryObject<SoundEvent> MITHRIL_ARMOUR = register("mithril_armour");
	public static final RegistryObject<SoundEvent> FOX_ARMOUR = register("fox_armour");
	public static final RegistryObject<SoundEvent> WOLF_ARMOUR = register("wolf_armour");
	public static final RegistryObject<SoundEvent> BADGER_ARMOUR = register("badger_armour");
	//npcs
	//animals
	public static final RegistryObject<SoundEvent> DEER_AMBIENT = register("deer_ambient");
	public static final RegistryObject<SoundEvent> DEER_HURT = register("deer_hurt");
	public static final RegistryObject<SoundEvent> DEER_DEATH = register("deer_death");
	public static final RegistryObject<SoundEvent> BADGER_AMBIENT = register("badger_ambient");
	public static final RegistryObject<SoundEvent> BADGER_HURT = register("badger_hurt");
	public static final RegistryObject<SoundEvent> BADGER_DEATH = register("badger_death");
	public static final RegistryObject<SoundEvent> SQUIRREL_AMBIENT = register("squirrel_ambient");
	public static final RegistryObject<SoundEvent> SQUIRREL_HURT = register("squirrel_hurt");
	public static final RegistryObject<SoundEvent> SQUIRREL_DEATH = register("squirrel_death");
	public static final RegistryObject<SoundEvent> MOUSE_AMBIENT = register("mouse_ambient");
	public static final RegistryObject<SoundEvent> MOUSE_HURT = register("mouse_hurt");
	public static final RegistryObject<SoundEvent> MOUSE_DEATH = register("mouse_death");
	public static final RegistryObject<SoundEvent> HEDGEHOG_AMBIENT = register("hedgehog_ambient");
	public static final RegistryObject<SoundEvent> HEDGEHOG_HURT = register("hedgehog_hurt");
	public static final RegistryObject<SoundEvent> HEDGEHOG_DEATH = register("hedgehog_death");
	//monsters

	//----------------registering----------------
	private static RegistryObject<SoundEvent> register(String key) {
		return SOUNDS.register(key,() -> SoundEvents.BLOCK_ANVIL_BREAK);
		//return SOUNDS.register(key, () -> new SoundEvent(new ResourceLocation(key)));
	}
}
