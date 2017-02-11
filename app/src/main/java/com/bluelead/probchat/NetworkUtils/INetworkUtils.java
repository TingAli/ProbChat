package com.bluelead.probchat.NetworkUtils;

import com.bluelead.probchat.Models.TypesApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Imdad Ali on 11/02/2017.
 */

public interface INetworkUtils {
    @GET("api/problem-categories")
    Call<TypesApiResponse> getTypes();
}
