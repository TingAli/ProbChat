package com.bluelead.probchat.Models;

import java.util.UUID;

/**
 * Created by Imdad Ali on 11/02/2017.
 */

public class Message {
    private UUID Id;
    private String Message;
    private boolean Documented;

    public Message(UUID id, String message, boolean documented) {
        Id = id;
        Message = message;
        Documented = documented;
    }

    public UUID getId() {
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
