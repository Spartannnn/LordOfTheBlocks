package lotb.entities.npc;

import lotb.util.BiConsumerCallback;
import lotb.util.Valid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class NPCInventory extends ItemStackHandler {

    public NPCInventory() {
        super(8);
    }

    public int containsFood() {
        for (int i = 0; i < this.getSlots(); i++) {
            ItemStack stack = this.getStackInSlot(i);
            if (!stack.isEmpty() && stack.isFood())
                return i;
        }
        return -1;
    }

    public boolean addItem(ItemStack stack) {
        for (int i = 0; i < this.getSlots(); i++) {
            ItemStack invStack = this.getStackInSlot(i);
            if (invStack.isEmpty()) {
                this.setStackInSlot(i, stack);
                return true;
            }
            if (stack.isItemEqual(invStack)) {
                if (invStack.getCount() < invStack.getMaxStackSize()) {
                    this.insertItem(i, stack, false);
                    return true;
                }
            }
        }
        return false;
    }

    public void clearInventory() {
        this.stacks.clear();
    }

    /**
     * Looks for a item with the given tag, and return the first item that the method find
     *
     * @param tag the tag
     * @return the first item, that be found
     */
    public ItemStack lookFor(Tag<Item> tag) {
        for(int i = 0; i < this.getSlots(); i++) {
            ItemStack invStack = this.getStackInSlot(i);
            Set<ResourceLocation> tags = invStack.getItem().getTags();
            if(tags.contains(tag.getId()))
                return invStack;
        }
        return ItemStack.EMPTY;
    }

    public boolean contains(Tag<Item> tag) {
        Valid.notNull(tag, "Tag is null");

        AtomicBoolean found = new AtomicBoolean(false);
        this.iterateThroughInventory((slot, invStack) -> {
            Set<ResourceLocation> tags = invStack.getItem().getTags();
            found.set(tags.contains(tag.getId()));
            return found.get();
        });
        return found.get();
    }

    public boolean contains(ItemStack stack) {
        Valid.notNull(stack, "ItemStack is null");

        //You have to use AtomicBoolean because a AtomicBoolean is thread save, so we can
        //change the boolean val in the anonymous class.
        AtomicBoolean found = new AtomicBoolean(false);
        this.iterateThroughInventory((slot, invStack) -> {
            //Set the value from the AtomicBoolean
            found.set(invStack.isItemEqual(stack));
            //Return the value with the method: "get()". If the item is equal then it will return true and the loop will be interrupted.
            //So we have not to loop through everything. A little performance boost
            return found.get();
        });

        return found.get();
    }

    /**
     * This method iterate through the inventory.
     * The first element "Integer" from the BiConsumer is the slot,
     * and the second element "ItemStack" is the stack on that slot.
     * <p>
     * if you want to break the iteration just return true, otherwise return false
     * See the contains method for a example
     *
     * @param consumer the bi consumer
     */
    public void iterateThroughInventory(BiConsumerCallback<Integer, ItemStack> consumer) {
        for (int i = 0; i < this.getSlots(); i++)
            if (consumer.consume(i, this.getStackInSlot(i)))
                break;
    }

}
