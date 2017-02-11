package com.bluelead.probchat.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by Imdad Ali on 11/02/2017.
 */

public class Type implements Parcelable {

    private UUID Id;
    @SerializedName("type")
    private String Type;

    public UUID getId() {
        return Id;
    }

    public String getType() {
        return Type;
    }

    public static final Parcelable.Creator<Type> CREATOR = new Parcelable.Creator<Type>() {
        public Type createFromParcel(Parcel source) {
            Type type = new Type();
            type.Type = source.readString();

            return type;
        }

        public Type[] newArray(int size) {
            return new Type[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Type);
    }
}
