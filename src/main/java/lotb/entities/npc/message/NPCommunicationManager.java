package lotb.entities.npc.message;

import com.google.common.collect.Lists;
import lotb.LotbMod;
import lotb.entities.npc.AbstractNPCEntity;
import lotb.entities.npc.message.messages.AskForItemMessage;
import lotb.entities.npc.message.messages.SimpleMessage;
import lotb.entities.npc.relationship.NPCRelationShipManager;
import lotb.entities.npc.relationship.RelationShip;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.List;

public class NPCommunicationManager {

    private final AbstractNPCEntity npc;
    private final List<IMessage> sendedMessages;
    private final List<IMessage> receivedMessages;
    private final MessageChannel messageChannel;

    public NPCommunicationManager(AbstractNPCEntity npc) {
        this.npc = npc;
        this.receivedMessages = Lists.newArrayList();
        this.sendedMessages = Lists.newArrayList();
        this.messageChannel = new MessageChannel();
    }

    /**
     * With this method you can send a message
     *
     * @param other   the target npc
     * @param message the message object
     */
    public void sendMessage(AbstractNPCEntity other, IMessage message) {
        other.getNpcCommunicationManager().receiveMessage(this.npc, message, message.onSend(this.npc, other, message));
        this.sendedMessages.add(message);
        LotbMod.LOGGER.debug("Message send with text {}, for entity with id {}", message.getText(), other.getEntityId());
    }

    /**
     * Send a message, and change the relation ship
     *
     * @param other   the target npc
     * @param message the message object
     * @param up      if true, then it will change to the next best, otherwise to the next bad
     */
    public void sendMessageWIthRelationship(AbstractNPCEntity other, IMessage message, boolean up) {
        RelationShip current = npc.getNpcRelationShipManager().getRelationShip(other);
        this.sendMessageWithRelationship(other, message, current == null ? (up ? RelationShip.GOOD : RelationShip.BAD) : (up ? RelationShip.nextBest(current) : RelationShip.nextBad(current)));
    }

    /**
     * Send a message, and change the relation ship
     *
     * @param other        the target npc
     * @param message      the message object
     * @param relationShip the new relation ship
     */
    public void sendMessageWithRelationship(AbstractNPCEntity other, IMessage message, RelationShip relationShip) {
        this.sendMessage(other, message);
        NPCRelationShipManager relationShipManager = npc.getNpcRelationShipManager();
        RelationShip current = relationShipManager.getRelationShip(other);
        if (current == null)
            relationShipManager.addRelationShip(other, relationShip);
        else
            relationShipManager.changeRelationShip(other, relationShip);
    }

    void receiveMessage(AbstractNPCEntity sender, IMessage message, boolean valid) {
        this.receivedMessages.add(message);
        message.onReceive(sender, message, valid);
        LotbMod.LOGGER.debug("Message received with text {}, for entity with id: {}", message.getText(), npc.getEntityId());
    }

    public CompoundNBT write() {
        ListNBT written = new ListNBT();
        if (!this.sendedMessages.isEmpty()) {
            this.sendedMessages.forEach(msg -> {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putString("Text", msg.getText());
                switch (msg.getType()) {
                    case IMessage.ASK_FOR_ITEM:
                        nbt.put("Need", ((AskForItemMessage) msg).getNeed().toNbt());
                        break;
                    case IMessage.SIMPLE_MESSAGE:
                    default:
                        break;
                }
                written.add(nbt);
            });
        }
        ListNBT received = new ListNBT();
        if (!this.receivedMessages.isEmpty()) {
            this.receivedMessages.forEach(msg -> {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putString("Text", msg.getText());
                nbt.putInt("Type", msg.getType());
                switch (msg.getType()) {
                    case IMessage.ASK_FOR_ITEM:
                        nbt.put("Need", ((AskForItemMessage) msg).getNeed().toNbt());
                        break;
                    case IMessage.SIMPLE_MESSAGE:
                    default:
                        break;
                }
                received.add(nbt);
            });
        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("Written", written);
        nbt.put("Received", received);
        return nbt;
    }

    public void read(CompoundNBT nbt) {
        ListNBT written = nbt.getList("Written", 10);
        written.forEach(listObj -> {
            CompoundNBT cnbt = (CompoundNBT) listObj;
            String text = cnbt.getString("Text");
            switch (nbt.getInt("Type")) {
                case IMessage.ASK_FOR_ITEM:
                    Need need = Need.read(nbt.getCompound("Need"));
                    AskForItemMessage afim = new AskForItemMessage(need);
                    afim.setText(text);
                    sendedMessages.add(afim);
                    break;
                case IMessage.SIMPLE_MESSAGE:
                default:
                    break;
            }

        });
        ListNBT received = nbt.getList("Received", 10);
        received.forEach(listObj -> {
            CompoundNBT cnbt = (CompoundNBT) listObj;
            String text = cnbt.getString("Text");
            switch (nbt.getInt("Type")) {
                case IMessage.ASK_FOR_ITEM:
                    Need need = Need.read(nbt.getCompound("Need"));
                    AskForItemMessage afim = new AskForItemMessage(need);
                    afim.setText(text);
                    receivedMessages.add(afim);
                    break;
                case IMessage.SIMPLE_MESSAGE:
                default:
                    break;
            }

        });

    }

}
