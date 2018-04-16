package com.rohantaneja.wheelstreetbot.model.pojo.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rohantaneja on 16/04/18.
 */

public class SubmittedSurveyResponse {

    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("data")
    @Expose
    private String data;

    public Long getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

}
