package lotb.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedItem extends Item {
	public EnchantedItem(Item.Properties properties) {
		super(properties);
	}

	/**
	 * Returns true if this item has an enchantment glint. By default, this returns <code>stack.isItemEnchanted()</code>,
	 * but other items can override it (for instance, written books always return true).
	 *  
	 * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
	 * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
	 */
	@Override public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
