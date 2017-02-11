package com.bluelead.probchat.NetworkUtils;

/**
 * Created by Imdad Ali on 11/02/2017.
 */

public interface Callback<T> {
    void next(T result);
}
