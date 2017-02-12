package com.bluelead.probchat.Models;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by Imdad Ali on 11/02/2017.
 */

public class Message {
    @Expose
    private int id;
    @Expose
    private String message;
    @Expose
    private boolean documented;
    @Expose
    private long timestamp;

    private Date getDate() {
        Date date = new Date ();
        date.setTime((long)timestamp*1000);

        return date;
    }

    private String action;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() { return this.action; }

    public Message(int id, String message, boolean documented) {
        this.id = id;
        this.message = message;
        this.documented = documented;
    }

    public int getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean getDocumented() {
        return this.documented;
    }

    public void setDocumented(boolean documented) {
        this.documented = documented;
    }
}
