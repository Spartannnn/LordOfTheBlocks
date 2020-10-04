package lotb.inventory;

import lotb.registries.ModBlocks;
import lotb.registries.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IWorldPosCallable;

import com.google.common.collect.Lists;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RohanWorkBenchContainer extends Container {
	protected static final Logger LOGGER = LogManager.getLogger();
	private final IWorldPosCallable worldPosCallable;
	private final IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
	private final World world;
	private List<StonecuttingRecipe> recipes = Lists.newArrayList();
	private ItemStack itemStackInput = ItemStack.EMPTY;
	private long lastOnTake;
	final Slot inputInventorySlot;
	final Slot outputInventorySlot;
	private Runnable inventoryUpdateListener = () -> {
	};
	public final IInventory inputInventory = new Inventory(1) {
		@Override public void markDirty() {
			super.markDirty();
			onCraftMatrixChanged(this);
			inventoryUpdateListener.run();
		}
	};
	/** The inventory that stores the output of the crafting recipe. */
	private final CraftResultInventory inventory = new CraftResultInventory();

	public RohanWorkBenchContainer(int windowIdIn, PlayerInventory playerInventoryIn) {
		this(windowIdIn, playerInventoryIn, IWorldPosCallable.DUMMY);
	}
	public RohanWorkBenchContainer(int windowIdIn, PlayerInventory playerInventoryIn, final IWorldPosCallable worldPosCallableIn) {
		super(ModContainers.ROHAN_WORKBENCH, windowIdIn);
		LOGGER.debug("new container made <<-------");
		worldPosCallable = worldPosCallableIn;
		world = playerInventoryIn.player.world;
		inputInventorySlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));
		outputInventorySlot = this.addSlot(new Slot(this.inventory, 1, 143, 33) {
			@Override public boolean isItemValid(ItemStack stack) {
				return false;
			}
			@Override public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
				ItemStack itemstack = RohanWorkBenchContainer.this.inputInventorySlot.decrStackSize(1);
				if (!itemstack.isEmpty()) {
					RohanWorkBenchContainer.this.updateRecipeResultSlot();
				}

				stack.getItem().onCreated(stack, thePlayer.world, thePlayer);
				worldPosCallableIn.consume((p_216954_1_, p_216954_2_) -> {
					long l = p_216954_1_.getGameTime();
					if (RohanWorkBenchContainer.this.lastOnTake != l) {
						p_216954_1_.playSound((PlayerEntity)null, p_216954_2_, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
						RohanWorkBenchContainer.this.lastOnTake = l;
					}

				});
				return super.onTake(thePlayer, stack);
			}
		});
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
		}
		trackInt(this.selectedRecipe);
	}

	@OnlyIn(Dist.CLIENT)
	public int getSelectedRecipe() {
		return selectedRecipe.get();
	} @OnlyIn(Dist.CLIENT)
	public List<StonecuttingRecipe> getRecipeList() {
		return recipes;
	} @OnlyIn(Dist.CLIENT)
	public int getRecipeListSize() {
		return recipes.size();
	} @OnlyIn(Dist.CLIENT)
	public boolean hasItemsinInputSlot() {
		return inputInventorySlot.getHasStack() && !this.recipes.isEmpty();
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(this.worldPosCallable, playerIn, ModBlocks.ROHAN_WORKBENCH);
	}

	/**
	 * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
	 */
	@Override
	public boolean enchantItem(PlayerEntity playerIn, int id) {
		if (id >= 0 && id < this.recipes.size()) {
			this.selectedRecipe.set(id);
			this.updateRecipeResultSlot();
		}
		return true;
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		ItemStack itemstack = this.inputInventorySlot.getStack();
		if (itemstack.getItem() != this.itemStackInput.getItem()) {
			this.itemStackInput = itemstack.copy();
			this.updateAvailableRecipes(inventoryIn, itemstack);
		}

	}

	private void updateAvailableRecipes(IInventory inventoryIn, ItemStack stack) {
		this.recipes.clear();
		this.selectedRecipe.set(-1);
		this.outputInventorySlot.putStack(ItemStack.EMPTY);
		if (!stack.isEmpty()) {
			this.recipes = this.world.getRecipeManager().getRecipes(IRecipeType.STONECUTTING, inventoryIn, this.world);
		}

	}

	private void updateRecipeResultSlot() {
		if (!this.recipes.isEmpty()) {
			StonecuttingRecipe stonecuttingrecipe = this.recipes.get(this.selectedRecipe.get());
			this.outputInventorySlot.putStack(stonecuttingrecipe.getCraftingResult(this.inputInventory));
		} else {
			this.outputInventorySlot.putStack(ItemStack.EMPTY);
		}

		this.detectAndSendChanges();
	}

	@Override
	public ContainerType<?> getType() {
		return ModContainers.ROHAN_WORKBENCH;
	}

	@OnlyIn(Dist.CLIENT)
	public void setInventoryUpdateListener(Runnable listenerIn) {
		this.inventoryUpdateListener = listenerIn;
	}

	/**
	 * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
	 * null for the initial slot that was double-clicked.
	 */
	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
		return slotIn.inventory != this.inventory && super.canMergeSlot(stack, slotIn);
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			Item item = itemstack1.getItem();
			itemstack = itemstack1.copy();
			if (index == 1) {
				item.onCreated(itemstack1, playerIn.world, playerIn);
				if (!this.mergeItemStack(itemstack1, 2, 38, true))
					return ItemStack.EMPTY;
				slot.onSlotChange(itemstack1, itemstack);
			} else if (index == 0) {
				if (!this.mergeItemStack(itemstack1, 2, 38, false))
					return ItemStack.EMPTY;
			} else if (this.world.getRecipeManager().getRecipe(IRecipeType.STONECUTTING, new Inventory(itemstack1), this.world).isPresent()) {
				if (!this.mergeItemStack(itemstack1, 0, 1, false))
					return ItemStack.EMPTY;
			} else if (index >= 2 && index < 29) {
				if (!this.mergeItemStack(itemstack1, 29, 38, false))
					return ItemStack.EMPTY;
			} else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false))
				return ItemStack.EMPTY;
			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			}
			slot.onSlotChanged();
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
			this.detectAndSendChanges();
		}
		return itemstack;
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		inventory.removeStackFromSlot(1);
		worldPosCallable.consume((p_217079_2_, p_217079_3_) -> {
			clearContainer(playerIn, playerIn.world, inputInventory);
		});
	}
}
