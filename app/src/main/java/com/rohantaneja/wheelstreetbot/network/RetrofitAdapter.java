package com.rohantaneja.wheelstreetbot.network;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rohantaneja on 15/04/18.
 */

public class RetrofitAdapter {

    private WheelstreetAPI WheelstreetAPI;

    public RetrofitAdapter(String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WheelstreetAPI = retrofit.create(WheelstreetAPI.class);
    }

    public WheelstreetAPI getWheelstreetAPI() {
        return WheelstreetAPI;
    }

}
