package com.rohantaneja.wheelstreetbot.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rohantaneja.wheelstreetbot.database.UserContract.UserEntry;
import com.rohantaneja.wheelstreetbot.model.User;

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
                    UserEntry.COLUMN_ID + " INTEGER," +
                    UserEntry.COLUMN_NAME + " TEXT," +
                    UserEntry.COLUMN_MOBILE + " TEXT," +
                    UserEntry.COLUMN_EMAIL + " TEXT," +
                    UserEntry.COLUMN_GENDER + " INTEGER," +
                    UserEntry.COLUMN_BIRTHDAY + " TEXT," +
                    UserEntry.COLUMN_AGE + " INTEGER," +
                    UserEntry.COLUMN_IS_AGE_OVERRIDDEN + " INTEGER," +
                    UserEntry.COLUMN_AVATAR_URL + " TEXT," +
                    UserEntry.COLUMN_AVATAR_PATH + " TEXT," +
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

    //database methods
    public User getUserFromDb(int userId) {
        Cursor usersCursor = getReadableDatabase().query(
                UserEntry.TABLE_NAME,
                new String[]{
                        UserEntry.COLUMN_ID,
                        UserEntry.COLUMN_NAME,
                        UserEntry.COLUMN_MOBILE,
                        UserEntry.COLUMN_EMAIL,
                        UserEntry.COLUMN_GENDER,
                        UserEntry.COLUMN_AGE,
                        UserEntry.COLUMN_IS_AGE_OVERRIDDEN,
                        UserEntry.COLUMN_BIRTHDAY,
                        UserEntry.COLUMN_AVATAR_URL,
                        UserEntry.COLUMN_AVATAR_PATH,
                        UserEntry.COLUMN_IS_AVATAR_FROM_PATH
                },
                UserEntry.COLUMN_ID + "?=", new String[]{String.valueOf(userId)},
                null, null, null
        );

        User user = null;

        //check is cursor has anything i.e. anything for this is was found in db or not
        if (usersCursor.moveToFirst()) {
            user = new User(
                    usersCursor.getInt(usersCursor.getColumnIndex(UserEntry.COLUMN_ID)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_NAME)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_EMAIL)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_BIRTHDAY)),
                    usersCursor.getInt(usersCursor.getColumnIndex(UserEntry.COLUMN_GENDER)),
                    usersCursor.getInt(usersCursor.getColumnIndex(UserEntry.COLUMN_AGE)),
                    usersCursor.getInt(usersCursor.getColumnIndex(UserEntry.COLUMN_IS_AGE_OVERRIDDEN)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_AVATAR_URL)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_AVATAR_PATH)),
                    usersCursor.getInt(usersCursor.getColumnIndex(UserEntry.COLUMN_IS_AVATAR_FROM_PATH))
            );

        }

        return user;
    }

}
