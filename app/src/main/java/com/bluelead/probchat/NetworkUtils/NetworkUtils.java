package com.bluelead.probchat.NetworkUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.bluelead.probchat.Models.TypesApiResponse;
import com.bluelead.probchat.Models.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Imdad Ali on 11/02/2017.
 */

public abstract class NetworkUtils {
    private static ArrayList<Type> mTypesArrayList;
    private static final String PROBLEM_URL = "http://probchat.tk/a/";


    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public static void getTypes(final Context context, final com.bluelead.probchat.NetworkUtils.Callback<ArrayList<Type>> callbackInterface){

        mTypesArrayList = new ArrayList<Type>();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PROBLEM_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        INetworkUtils apiService =
                retrofit.create(INetworkUtils.class);

        Call<TypesApiResponse> call = null;

        call = apiService.getTypes();

        if(call != null) {
            call.enqueue(new Callback<TypesApiResponse>() {

                @Override
                public void onResponse(Call<TypesApiResponse> call, Response<TypesApiResponse> response) {
                    mTypesArrayList =  response.body().getTypes();

                    callbackInterface.next(mTypesArrayList);
                }

                @Override
                public void onFailure(Call<TypesApiResponse> call, Throwable t) {
                    // the network call was a failure
                    // handle error
                    t.printStackTrace();
                    Toast.makeText(context, "ERROR: API fail", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(context, "ERROR: Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }
}
