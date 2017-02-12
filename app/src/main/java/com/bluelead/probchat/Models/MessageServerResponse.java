package com.bluelead.probchat.Models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Imdad Ali on 12/02/2017.
 */

public class MessageServerResponse {
    @Expose
    private ArrayList<Message> msgs;

    @Expose
    private String action;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() { return this.action; }

    public ArrayList<Message> getMsgs() {
        return msgs;
    }
}
