package lotb.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import lotb.LotbMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.items.IItemHandler;

public class ModInventoryUtils {
	//-------------inventory functions-------------
	public static boolean addItem(IItemHandler inventory,@Nonnull ItemStack stack, boolean simulate) {
		for (int slot =0; slot<inventory.getSlots();slot++)
			stack=inventory.insertItem(slot, stack, simulate);
			if (stack==ItemStack.EMPTY)
				return true;
			return false;
	}
	
	public static boolean addItem(IItemHandler inventory,@Nonnull ItemStack stack) {
		return addItem(inventory,stack,false);
	}
	
	public static boolean hasAny(IItemHandler inventory,Set<Item> set) {
		for(int slot = 0; slot < inventory.getSlots(); ++slot) {
			ItemStack itemstack = inventory.getStackInSlot(slot);
			if (set.contains(itemstack.getItem()) && itemstack.getCount() > 0) {
				return true;
			}
		}return false;
	}

	public static int count(IItemHandler inventory, Item itemIn) {
		int i = 0;
		for(int slot = 0; slot < inventory.getSlots(); ++slot) {
			ItemStack itemstack = inventory.getStackInSlot(slot);
			if (itemstack.getItem().equals(itemIn)) {
				i += itemstack.getCount();
			}
		}
		return i;
	}

	//-------------the loot table one-------------
	public static void fillInventoryWithLoot(World world, Random rand, IItemHandler inventory, ResourceLocation location, LootContext context) {
		LootTable loottable = world.getServer().getLootTableManager().getLootTableFromLocation(location);
		List<ItemStack>stacks= loottable.generate(context);
		List<Integer> empty_slots = getEmptySlotsRandomized(inventory, rand);
		shuffleLootTable(stacks, empty_slots.size(), rand);

		for(ItemStack itemstack : stacks) {
			if (empty_slots.isEmpty()) {
				LotbMod.LOGGER.warn("Tried to over-fill a container");
				return;
			}
			if (itemstack.isEmpty())
				inventory.insertItem(empty_slots.remove(empty_slots.size() - 1), ItemStack.EMPTY, false);
			else
				inventory.insertItem(empty_slots.remove(empty_slots.size() - 1), itemstack, false);
		}
	}
	
	/*=====================================================================================================================*\
	|*======================================-----LOOT-TABLE-PORTED-FUNCTIONS-----==========================================*|
	\*=====================================================================================================================*/
	
	private static List<Integer> getEmptySlotsRandomized(IItemHandler inventory, Random rand) {
		List<Integer> list = Lists.newArrayList();
		for(int i = 0; i < inventory.getSlots(); ++i)
			if (inventory.getStackInSlot(i).isEmpty())
				list.add(i);
		Collections.shuffle(list, rand);
		return list;
	}
	private static void shuffleLootTable(List<ItemStack> stacks, int slots, Random rand) {
	      List<ItemStack> list = Lists.newArrayList();
	      Iterator<ItemStack> iterator = stacks.iterator();

	      while(iterator.hasNext()) {
	         ItemStack itemstack = iterator.next();
	         if (itemstack.isEmpty()) iterator.remove();
	         else if (itemstack.getCount() > 1) {
	            list.add(itemstack);
	            iterator.remove();
	         }
	      }

	      while(slots - stacks.size() - list.size() > 0 && !list.isEmpty()) {
	         ItemStack itemstack2 = list.remove(MathHelper.nextInt(rand, 0, list.size() - 1));
	         int i = MathHelper.nextInt(rand, 1, itemstack2.getCount() / 2);
	         ItemStack itemstack1 = itemstack2.split(i);
	         
	         if (itemstack2.getCount() > 1 && rand.nextBoolean()) list.add(itemstack2);
	         else stacks.add(itemstack2);

	         if (itemstack1.getCount() > 1 && rand.nextBoolean()) list.add(itemstack1);
	         else stacks.add(itemstack1);
	      }

	      stacks.addAll(list);
	      Collections.shuffle(stacks, rand);
	   }
}
