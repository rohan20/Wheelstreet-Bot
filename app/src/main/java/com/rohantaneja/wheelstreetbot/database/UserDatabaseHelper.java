package com.rohantaneja.wheelstreetbot.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rohantaneja.wheelstreetbot.database.UserContract.UserEntry;

/**
 * Created by rohantaneja on 15/04/18.
 */

public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Users.db";

    private static UserDatabaseHelper sUserDatabaseHelper;

    private static String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserEntry.COLUMN_ID + " TEXT," +
                    UserEntry.COLUMN_NAME + " TEXT," +
                    UserEntry.COLUMN_MOBILE + " TEXT," +
                    UserEntry.COLUMN_EMAIL + " TEXT," +
                    UserEntry.COLUMN_GENDER + " TEXT," +
                    UserEntry.COLUMN_AGE + " INTEGER," +
                    UserEntry.COLUMN_AVATAR_URL + " TEXT," +
                    UserEntry.COLUMN_AVATAR_PATH + " TEXT," +
                    UserEntry.COLUMN_IS_AVATAR_FROM_PATH + " TEXT," +
                    UserEntry.COLUMN_IS_AVATAR_FROM_PATH + " INTEGER)";

    private static final String SQL_DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    public static UserDatabaseHelper getUserDatabaseHelperInstance(Context context) {
        if (sUserDatabaseHelper == null)
            sUserDatabaseHelper = new UserDatabaseHelper(context);

        return sUserDatabaseHelper;
    }

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_USER_TABLE);
        onCreate(sqLiteDatabase);
    }
}
