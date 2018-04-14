package com.rohantaneja.wheelstreetbot.network;

import com.rohantaneja.wheelstreetbot.model.QuestionsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by rohantaneja on 15/04/18.
 */

public interface WheelstreetAPI {

    @GET("questions")
    Call<QuestionsResponse> getQuestions();

}
