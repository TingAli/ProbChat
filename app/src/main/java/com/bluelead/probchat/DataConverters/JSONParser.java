package com.bluelead.probchat.DataConverters;

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


}
