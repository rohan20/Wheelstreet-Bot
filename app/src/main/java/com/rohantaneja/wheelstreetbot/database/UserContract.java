package com.rohantaneja.wheelstreetbot.database;

import android.provider.BaseColumns;

/**
 * Created by rohantaneja on 15/04/18.
 */

public class UserContract {

    //to prevent initialisation of the UserContract class, mark the constructor as private
    private UserContract() {

    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user_table";
        public static final String COLUMN_ID = "user_id";
        public static final String COLUMN_NAME = "user_name";
        public static final String COLUMN_MOBILE = "user_mobile";
        public static final String COLUMN_EMAIL = "user_email";
        public static final String COLUMN_GENDER = "user_gender";
        public static final String COLUMN_BIRTHDAY = "user_birthday";
        public static final String COLUMN_AVATAR_URL = "user_url";
        public static final String COLUMN_AVATAR_PATH = "user_avatar_path";
        public static final String COLUMN_IS_AVATAR_FROM_PATH = "user_is_avatar_from_path";
    }

}


