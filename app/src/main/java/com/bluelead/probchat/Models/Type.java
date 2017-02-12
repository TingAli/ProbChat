package com.bluelead.probchat.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Imdad Ali on 11/02/2017.
 */

public class Type implements Parcelable {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("caption")
    private String caption;

    private String action;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() { return this.action; }

    public String getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public static final Parcelable.Creator<Type> CREATOR = new Parcelable.Creator<Type>() {
        public Type createFromParcel(Parcel source) {
            Type type = new Type();
            type.id = source.readString();
            type.caption = source.readString();
            type.action = source.readString();

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
        dest.writeString(id);
        dest.writeString(caption);
        dest.writeString(action);
    }
}
