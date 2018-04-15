package com.rohantaneja.wheelstreetbot.util;

/**
 * Created by rohantaneja on 14/04/18.
 */

public class Constants {

    public static final int TYPE_STRING = 0;
    public static final int TYPE_FLOAT = 1;
    public static final int TYPE_INTEGER = 2;
    public static final int TYPE_BOOLEAN = 3;

    public static final String BASE_URL = "https://api.wheelstreet.com/v1/test/";

    public static final String HAWK_USER_DETAILS = "hawk_user_details";

    public static final int NEXT_QUESTION_DELAY = 750;

    public static final int GENDER_MALE = 98;
    public static final String GENDER_MALE_STRING = "Male";
    public static final int GENDER_FEMALE = 99;
    public static final String GENDER_FEMALE_STRING = "Female";

    public static final int IS_AGE_OVERRIDDEN_TRUE = 89;
    public static final int IS_AGE_OVERRIDDEN_FALSE = 88;

    public static final int IS_AVATAR_FROM_PATH_TRUE = 79;
    public static final int IS_AVATAR_FROM_PATH_FALSE = 78;


    public enum ANIMATION_TYPE {
        SLIDE, FADE, DEFAULT, NONE
    }

    public enum FRAGMENTS {
        VIEW_PROFILE, UPDATE_PROFILE, VIEW_QUESTION_ANSWER, UPDATE_QUESTION_ANSWER
    }
}
