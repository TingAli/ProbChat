package com.bluelead.probchat.Models;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Imdad Ali on 11/02/2017.
 */

public class Message {
    private UUID Uuid;
    @Expose
    private int id;
    @Expose
    private String msg;
    @Expose
    private boolean documented;
    @Expose
    private long timestamp;

    public Date getDate() {
        Date date = new Date ();
        date.setTime((long)timestamp*1000);

        return date;
    }

    private Date date;

    public Date getDateClient() {
        return date;
    }

    public void setDateClient(Date date) {
        this.date = date;
    }

    public void setUuid() {
        Uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return Uuid;
    }

    @Expose
    private String action;

    private boolean isIncomingMessage;

    public void setIsIncomingMessage(boolean isIncomingMessage) {
        this.isIncomingMessage = isIncomingMessage;
    }

    public boolean getIsIncomingMessage() {
        return this.isIncomingMessage;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() { return this.action; }

    public Message(String message, boolean documented) {
        this.msg = message;
        this.documented = documented;
    }

    public int getId() {
        return this.id;
    }

    public String getMessage() {
        return this.msg;
    }

    public boolean getDocumented() {
        return this.documented;
    }

    public void setDocumented(boolean documented) {
        this.documented = documented;
    }
}
