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

}
