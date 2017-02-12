package com.bluelead.probchat.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Imdad Ali on 12/02/2017.
 */

public class MessageServerResponse {
    @Expose
    @SerializedName("msgs")
    private ArrayList<Message> msgs;

    private String action;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() { return this.action; }

    public ArrayList<Message> getMsgs() {
        return msgs;
    }
}
