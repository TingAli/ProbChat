package com.bluelead.probchat.Models;

/**
 * Created by Imdad Ali on 11/02/2017.
 */

public class Message {
    private int Id;
    private String Message;
    private boolean Documented;

    public Message(int id, String message, boolean documented) {
        Id = id;
        Message = message;
        Documented = documented;
    }

    public int getId() {
        return Id;
    }

    public String getMessage() {
        return Message;
    }

    public boolean getDocumented() {
        return Documented;
    }

    public void setDocumented(boolean documented) {
        Documented = documented;
    }
}
