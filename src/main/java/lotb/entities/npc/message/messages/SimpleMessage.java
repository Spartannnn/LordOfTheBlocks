package lotb.entities.npc.message.messages;

import lotb.entities.npc.AbstractNPCEntity;
import lotb.entities.npc.message.IMessage;

import java.util.function.BiConsumer;

public class SimpleMessage implements IMessage {

    private String message;
    private final BiConsumer<AbstractNPCEntity, IMessage> received;

    public SimpleMessage(String message, BiConsumer<AbstractNPCEntity, IMessage> received) {
        this.message = message;
        this.received = received;
    }

    @Override
    public String getText() {
        return this.message;
    }

    @Override
    public void setText(String text) {
        this.message = text;
    }

    @Override
    public void onReceive(AbstractNPCEntity sender, IMessage message, boolean valid) {
        if (valid) {
            this.received.accept(sender, message);
        }
    }

    @Override
    public boolean onSend(AbstractNPCEntity sender, AbstractNPCEntity target, IMessage message) {
        return true;
    }

    @Override
    public int getType() {
        return SIMPLE_MESSAGE;
    }
}
