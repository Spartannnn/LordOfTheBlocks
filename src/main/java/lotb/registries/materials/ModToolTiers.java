package lotb.registries.materials;

import lotb.registries.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraftforge.common.util.LazyOptional;

public enum ModToolTiers implements IItemTier{
	
	MITHRIL(4 ,3028 ,11.0f ,5.0f ,25 , new LazyValue<>(() -> Ingredient.fromItems(ModItems.MITHRIL_INGOT.get())));

	private final int harvestLevel;
	private final int durability;
	private final float efficiency;
	private final float damage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairItem;
	
	private ModToolTiers(int _harvest,int _uses,float _eff,float _damage,int _enchant, LazyValue<Ingredient> _repair) {
		harvestLevel=_harvest;
		durability	=_uses;
		efficiency	=_eff;
		damage		=_damage;
		enchantability=_enchant;

		repairItem= _repair;
		
		
	}

	@Override
	public int getMaxUses() {
		return durability;
	}

	@Override
	public float getEfficiency() {
		return efficiency;
	}

	@Override
	public float getAttackDamage() {
		return damage;
	}

	@Override
	public int getHarvestLevel() {
		return harvestLevel;
	}
	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return repairItem.getValue();
	}

}
