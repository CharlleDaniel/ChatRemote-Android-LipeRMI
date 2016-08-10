package com.chatremotemsg.extras;

import com.chatremotemsg.conn.ConnLogin;
import com.server.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charlle Daniel on 09/06/2016.
 */
public class Conversation {
    private int idTo;
    private List<Message> messages;

    public Conversation(int idTo){
        this.idTo=idTo;
        this.messages= new ArrayList<>();
    }


    public int getIdTo() {
        return idTo;
    }

    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(Message message) {
        this.messages.add(message);
    }
}
