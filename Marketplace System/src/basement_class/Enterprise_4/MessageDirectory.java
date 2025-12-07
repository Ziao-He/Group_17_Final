/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.UserAccount;
import java.util.ArrayList;

/**
 *
 * @author yujie-liang
 */
public class MessageDirectory {

    private ArrayList<Message> messageList;

    public MessageDirectory() {
        this.messageList = new ArrayList<>();
    }

    public void addMessage(Message message) {
        if (message != null) {
            messageList.add(message);
        }
    }

    public Message addMessage(UserAccount sender, UserAccount receiver, String content) {
        if (sender == null || receiver == null || content == null || content.trim().isEmpty()) {
            return null;
        }
        Message msg = new Message(sender, receiver, content.trim());
        messageList.add(msg);
        return msg;
    }

    public ArrayList<Message> getMessagesBetween(UserAccount a, UserAccount b) {
        ArrayList<Message> result = new ArrayList<>();
        if (a == null || b == null) return result;

        for (Message m : messageList) {
            boolean ab = m.getSender().equals(a) && m.getReceiver().equals(b);
            boolean ba = m.getSender().equals(b) && m.getReceiver().equals(a);
            if (ab || ba) {
                result.add(m);
            }
        }
        return result;
    }

    public ArrayList<Message> getMessagesFromTo(UserAccount sender, UserAccount receiver) {
        ArrayList<Message> result = new ArrayList<>();
        if (sender == null || receiver == null) return result;

        for (Message m : messageList) {
            if (m.getSender().equals(sender) && m.getReceiver().equals(receiver)) {
                result.add(m);
            }
        }
        return result;
    }

    public ArrayList<Message> getAllMessages() {
        return messageList;
    }
}