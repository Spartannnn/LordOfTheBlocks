package lotb.entities.npc.message.conversation;

import lotb.entities.npc.AbstractNPCEntity;
import lotb.entities.npc.message.IConversation;
import lotb.entities.npc.message.IMessage;
import lotb.util.IndexedQueue;

public class DialogConversation implements IConversation {

    private AbstractNPCEntity first;
    private AbstractNPCEntity second;
    private IndexedQueue<IMessage> messages;

    public DialogConversation(AbstractNPCEntity first, AbstractNPCEntity second) {
        this.first = first;
        this.second = second;
        this.messages = new IndexedQueue<>();
    }

    @Override
    public AbstractNPCEntity[] getParticipants() {
        return new AbstractNPCEntity[] {this.first, this.second};
    }

    @Override
    public IndexedQueue<IMessage> messages() {
        return this.messages;
    }

    @Override
    public void begin() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void end() {

    }
}
