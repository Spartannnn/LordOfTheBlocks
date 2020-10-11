package lotb.entities.ai;

import java.util.Optional;

import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import lotb.LotbMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModMemories {
	public static final DeferredRegister<MemoryModuleType<?>> MEMORIES = new DeferredRegister<>(ForgeRegistries.MEMORY_MODULE_TYPES,LotbMod.MODID);
	
    public static final MemoryModuleType<LivingEntity> ATTACK_TARGET = register("attack_target");
    public static final MemoryModuleType<Boolean> NEEDS_WEAPON = register("needs_weapon");
    public static final MemoryModuleType<Boolean> NEEDS_BED = register("needs_bed");
    public static final MemoryModuleType<Boolean> NEEDS_FOOD = register("needs_food");


	/*========registry stuff=========*/
    private static <U> MemoryModuleType<U> register(String key, Function<Dynamic<?>, U> type) {
    	return MEMORIES.register( key, () -> new MemoryModuleType<U>(Optional.of(type))).get();
    }
    
	private static <U> MemoryModuleType<U> register(String key) {
        //return MEMORIES.register(key, () -> new MemoryModuleType<U>(Optional.empty())).get();
        return Registry.register(Registry.MEMORY_MODULE_TYPE, key, new MemoryModuleType<>(Optional.empty()));
    }
}

