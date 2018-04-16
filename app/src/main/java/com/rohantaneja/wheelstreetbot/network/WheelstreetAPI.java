package com.rohantaneja.wheelstreetbot.network;

import com.rohantaneja.wheelstreetbot.model.QuestionsResponse;
import com.rohantaneja.wheelstreetbot.model.pojo.request.SubmittedSurveyRequest;
import com.rohantaneja.wheelstreetbot.model.pojo.response.SubmittedSurveyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by rohantaneja on 15/04/18.
 */

public interface WheelstreetAPI {

    @GET("questions")
    Call<QuestionsResponse> getQuestions();

    @POST("answers")
    Call<SubmittedSurveyResponse> submitSurvey(@Body SubmittedSurveyRequest surveyRequest);

}
