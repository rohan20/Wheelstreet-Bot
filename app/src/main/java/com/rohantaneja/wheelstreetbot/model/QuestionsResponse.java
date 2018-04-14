package com.rohantaneja.wheelstreetbot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rohantaneja on 14/04/18.
 */

public class QuestionsResponse {

    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("data")
    @Expose
    private List<QuestionAnswer> data = null;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<QuestionAnswer> getData() {
        return data;
    }

    public void setData(List<QuestionAnswer> data) {
        this.data = data;
    }
}
