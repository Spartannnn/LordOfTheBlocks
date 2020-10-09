package lotb.registries.materials;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
@SuppressWarnings("deprecation")
public class ModFoods {
	public static final Food VENISON 				= (new Food.Builder()).hunger(3).saturation(0.3F).meat().build();
	public static final Food COOKED_VENISON 		= (new Food.Builder()).hunger(8).saturation(0.8F).meat().build();
	public static final Food HARD_TACK				= (new Food.Builder()).hunger(4).saturation(1.0F).build();
	public static final Food LEMBAS					= (new Food.Builder()).hunger(4).saturation(1.29F).build();
	public static final Food CRAM					= (new Food.Builder()).hunger(7).saturation(1.0F).build();
	public static final Food SILVER_APPLE			= (new Food.Builder()).hunger(4).saturation(1.2F).effect(new EffectInstance(Effects.REGENERATION, 100, 1), 1.0F)
																									 .effect(new EffectInstance(Effects.ABSORPTION, 2400, 0), 1.0F).setAlwaysEdible().build();
	public static final Food ENCHANTED_SILVER_APPLE = (new Food.Builder()).hunger(4).saturation(1.2F).effect(new EffectInstance(Effects.REGENERATION, 400, 1), 1.0F)
																									 .effect(new EffectInstance(Effects.RESISTANCE, 6000, 0), 1.0F)
																									 .effect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000, 0), 1.0F)
																									 .effect(new EffectInstance(Effects.ABSORPTION, 2400, 3), 1.0F).setAlwaysEdible().build();;
}
