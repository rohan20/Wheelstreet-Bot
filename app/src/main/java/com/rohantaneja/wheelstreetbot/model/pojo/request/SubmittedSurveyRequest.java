package com.rohantaneja.wheelstreetbot.model.pojo.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rohantaneja.wheelstreetbot.model.QuestionAnswer;

import java.util.List;

/**
 * Created by rohantaneja on 16/04/18.
 */

public class SubmittedSurveyRequest {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fbUserName")
    @Expose
    private String fbUserName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("questions")
    @Expose
    private List<QuestionAnswer> questions = null;

    public void setId(String id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setFbUserName(String fbUserName) {
        this.fbUserName = fbUserName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<QuestionAnswer> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionAnswer> questions) {
        this.questions = questions;
    }

}
