package lotb.entities.npc.message.messages;

import lotb.LotbMod;
import lotb.entities.npc.AbstractNPCEntity;
import lotb.entities.npc.message.IMessage;
import lotb.entities.npc.message.Need;
import net.minecraft.item.ItemStack;

public class AskForItemMessage implements IMessage {

    private final Need need;
    private String text;

    public AskForItemMessage(Need need) {
        this.need = need;
        this.text = "Can i have this item?: " + need.item.getRegistryName().toString();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void onReceive(AbstractNPCEntity sender, IMessage message, boolean valid) {
        if (valid) {
            //TODO add relationship
            sender.getNpcInventory().addItem(new ItemStack(need.item, need.amount));
            LotbMod.LOGGER.debug("Added item successfully");
        }
    }

    @Override
    public boolean onSend(AbstractNPCEntity sender, AbstractNPCEntity target, IMessage message) {
        if (sender.getNpcInventory().contains(need.item.getDefaultInstance())) {
            LotbMod.LOGGER.debug("Message not valid, item exist in inventory");
            return false;
        }
        return true;
    }

    @Override
    public int getType() {
        return ASK_FOR_ITEM;
    }

    public Need getNeed() {
        return need;
    }
}
