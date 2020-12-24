package lotb.entities.npc.message;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class Need {

    public final Item item;
    public final int amount;

    public Need(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public CompoundNBT toNbt() {
        CompoundNBT nbt = new CompoundNBT();
        item.getDefaultInstance().write(nbt);
        nbt.putInt("Amount", this.amount);
        return nbt;
    }

    public static Need read(CompoundNBT nbt) {
        return new Need(ItemStack.read(nbt).getItem(), nbt.getInt("Amount"));
    }

}
