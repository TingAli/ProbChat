package com.bluelead.probchat.DataConverters;

import com.bluelead.probchat.Models.LobbyServerResponse;
import com.bluelead.probchat.Models.Message;
import com.bluelead.probchat.Models.MessageServerResponse;
import com.bluelead.probchat.Models.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Imdad Ali on 12/02/2017.
 */
// com.bluelead.probchat.Models.Type

public abstract class JSONParser { // http://www.vogella.com/tutorials/JavaLibrary-Gson/article.html
    public static String typeToJson(com.bluelead.probchat.Models.Type type) {
        Gson gson = new Gson();
        java.lang.reflect.Type typeOfType = new TypeToken<com.bluelead.probchat.Models.Type>() {}.getType();
        String json = gson.toJson(type);

        return json;
    }

    public static ArrayList<Type> typeFromJson(String typesInJson) {
        Gson gson = new Gson();
        java.lang.reflect.Type typeOfType = new TypeToken<com.bluelead.probchat.Models.Type>() {}.getType();
        ArrayList<com.bluelead.probchat.Models.Type> fromJson = gson.fromJson(typesInJson, typeOfType);

        return fromJson;
    }

    public static LobbyServerResponse lobbyServerResponseFromJson(String lobbyServerResponseInJson) {
        Gson gson = new Gson();
        java.lang.reflect.Type typeOfLobbyServerResponse =
                new TypeToken<LobbyServerResponse>() {}.getType();
        LobbyServerResponse fromJson = gson.fromJson(lobbyServerResponseInJson,
                typeOfLobbyServerResponse);

        return fromJson;
    }

    public static String messageToJson(Message message) {
        Gson gson = new Gson();
        java.lang.reflect.Type typeOfMessage = new TypeToken<Message>() {}.getType();
        String json = gson.toJson(message);

        return json;
    }

    public static ArrayList<Message> messagesFromJson(String messagesInJson) {
        Gson gson = new Gson();
        java.lang.reflect.Type typeOfMessage = new TypeToken<MessageServerResponse>() {}.getType();
        MessageServerResponse messageServerResponsesFromJson =
                gson.fromJson(messagesInJson, typeOfMessage);
        ArrayList<Message> fromJson = new ArrayList<Message>();
        for(Message msg : messageServerResponsesFromJson.getMsgs()) {
            fromJson.add(msg);
        }

        return fromJson;
    }

    public static ArrayList<Message> messagesFromJsonSaved(String messagesInJson) {
        Gson gson = new Gson();
        java.lang.reflect.Type typeOfMessage = new TypeToken<Message>() {}.getType();
        Message messageFromJson =
                gson.fromJson(messagesInJson, typeOfMessage);
        ArrayList<Message> fromJson = new ArrayList<Message>();
        fromJson.add(messageFromJson);

        return fromJson;
    }

    public static String messagesToJsonSaved(ArrayList<Message> messagesArrayList) {
        Gson gson = new Gson();
        java.lang.reflect.Type typeOfMessage = new TypeToken<Message>() {}.getType();
        String messageFromJson =
                gson.toJson(messagesArrayList, typeOfMessage);

        return messageFromJson;
    }
}
