package com.rohantaneja.wheelstreetbot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rohantaneja on 14/04/18.
 */

public class Question {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("dataType")
    @Expose
    private String dataType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

}
