package lotb.entities.npc;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class NPCInventory extends ItemStackHandler {

    public NPCInventory() {
        super(8);
    }

    public int containsFood() {
        for(int i = 0; i < this.getSlots(); i++) {
            ItemStack stack = this.getStackInSlot(i);
            if(!stack.isEmpty() && stack.isFood())
                return i;
        }
        return -1;
    }

    public boolean addItem(ItemStack stack) {
        for(int i = 0; i < this.getSlots(); i++) {
            ItemStack invStack = this.getStackInSlot(i);
            if(invStack.isEmpty()) {
                this.setStackInSlot(i, stack);
                return true;
            }
            if(stack.isItemEqual(invStack)) {
                if(invStack.getCount() < invStack.getMaxStackSize()) {
                    this.insertItem(i, stack, false);
                    return true;
                }
            }
        }
        return false;
    }

}
