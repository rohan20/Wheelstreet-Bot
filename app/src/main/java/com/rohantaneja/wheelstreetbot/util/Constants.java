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

    //user profile
    public static final String HAWK_USER_DETAILS = "hawk_user_details";

    public static final String GENDER_MALE = "Male";
    public static final String GENDER_FEMALE = "Female";

    public static final String IS_AGE_OVERRIDDEN_TRUE = "true";
    public static final String IS_AGE_OVERRIDDEN_FALSE = "false";

    public static final String IS_AVATAR_FROM_PATH_TRUE = "true";
    public static final String IS_AVATAR_FROM_PATH_FALSE = "false";


    public static final int AGE_MIN_VALUE = 14;
    public static final int AGE_MAX_VALUE = 120;

    public static final String FROM_UPDATE_PROFILE = "from_update_profile";

    //survey
    public static final int NEXT_QUESTION_DELAY = 600;

    public static final String ONGOING_SURVEY_QUESTIONS_LIST = "ongoing_survey_questions_list";
    public static final String IS_SURVEY_COMPLETE = "is_survey_complete";

    public static final int SURVEY_SUBMITTED_SUCCESSFULLY = 1;


    public enum ANIMATION_TYPE {
        SLIDE, FADE, DEFAULT, NONE
    }

    public enum FRAGMENTS {
        VIEW_PROFILE, UPDATE_PROFILE, SUBMIT_SURVEY
    }
}
