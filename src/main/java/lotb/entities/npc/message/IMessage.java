package lotb.entities.npc.message;

import lotb.entities.npc.AbstractNPCEntity;

/**
 * If you want to create a message, implement this interface
 */
public interface IMessage  {

    int SIMPLE_MESSAGE = 1;
    int ASK_FOR_ITEM = 2;


    /**
     * @return the text that will be send
     */
    String getText();

    void setText(String text);

    /**
     * This method will be executed when the message was received.
     *
     * @param sender  the sender npc
     * @param message the message object himself
     * @param valid   before you start coding, check if the message is valid or not.
     *                If the boolean valid is true, then the message is ok, otherwise not
     */
    void onReceive(AbstractNPCEntity sender, IMessage message, boolean valid);

    /**
     * This method will be executed when the message was send
     *
     * @param target  the target npc, who receive the message
     * @param message the message object himself
     * @return true if the message is valid, or false if not.
     * This return value, is the "valid" value in the onReceive method
     */
    boolean onSend(AbstractNPCEntity sender, AbstractNPCEntity target, IMessage message);

    int getType();


}
