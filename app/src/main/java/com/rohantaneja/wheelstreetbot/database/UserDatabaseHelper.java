package com.rohantaneja.wheelstreetbot.database;

import android.content.ContentValues;
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
                    UserEntry.COLUMN_ID + " TEXT," +
                    UserEntry.COLUMN_NAME + " TEXT," +
                    UserEntry.COLUMN_MOBILE + " TEXT," +
                    UserEntry.COLUMN_EMAIL + " TEXT," +
                    UserEntry.COLUMN_GENDER + " TEXT," +
                    UserEntry.COLUMN_BIRTHDAY + " TEXT," +
                    UserEntry.COLUMN_AGE + " TEXT," +
                    UserEntry.COLUMN_IS_AGE_OVERRIDDEN + " TEXT," +
                    UserEntry.COLUMN_AVATAR_URL + " TEXT," +
                    UserEntry.COLUMN_AVATAR_PATH + " TEXT," +
                    UserEntry.COLUMN_IS_AVATAR_FROM_PATH + " TEXT)";

    private static final String SQL_DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    public static synchronized UserDatabaseHelper getUserDatabaseHelperInstance(Context context) {
        if (sUserDatabaseHelper == null)
            sUserDatabaseHelper = new UserDatabaseHelper(context.getApplicationContext());

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
    public User getUserFromDb(String userId) {
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
                UserEntry.COLUMN_ID + "=?", new String[]{userId},
                null, null, null
        );

        User user = null;

        //check is cursor has anything i.e. anything for this is was found in db or not
        if (usersCursor.moveToFirst()) {
            user = new User(
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_ID)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_NAME)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_EMAIL)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_BIRTHDAY)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_GENDER)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_MOBILE)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_AGE)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_IS_AGE_OVERRIDDEN)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_AVATAR_URL)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_AVATAR_PATH)),
                    usersCursor.getString(usersCursor.getColumnIndex(UserEntry.COLUMN_IS_AVATAR_FROM_PATH))
            );

        }

        return user;
    }

    private ContentValues getContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserEntry.COLUMN_ID, user.getId());
        contentValues.put(UserEntry.COLUMN_NAME, user.getName());
        contentValues.put(UserEntry.COLUMN_EMAIL, user.getEmail());
        contentValues.put(UserEntry.COLUMN_BIRTHDAY, user.getBirthday());
        contentValues.put(UserEntry.COLUMN_GENDER, user.getGender());
        contentValues.put(UserEntry.COLUMN_MOBILE, user.getMobile());
        contentValues.put(UserEntry.COLUMN_AGE, user.getAge());
        contentValues.put(UserEntry.COLUMN_IS_AGE_OVERRIDDEN, user.getIsAgeOverridden());
        contentValues.put(UserEntry.COLUMN_AVATAR_URL, user.getAvatarUrl());
        contentValues.put(UserEntry.COLUMN_AVATAR_PATH, user.getAvatarPath());
        contentValues.put(UserEntry.COLUMN_IS_AVATAR_FROM_PATH, user.getIsAvatarFromPath());

        return contentValues;
    }

    private void createUserInDb(User user) {
        getWritableDatabase().insert(
                UserEntry.TABLE_NAME,
                null,
                getContentValues(user)
        );
    }

    //If return value i.e. number of rows updated would be 0 => No user with this id exists. Hence, create new user instead
    public void updateUserInDb(User user) {
        int numberOfRowsAffected = getWritableDatabase().update(
                UserEntry.TABLE_NAME,
                getContentValues(user),
                UserEntry.COLUMN_ID + "=?",
                new String[]{String.valueOf(user.getId())}
        );

        if (numberOfRowsAffected == 0)
            createUserInDb(user);
    }

}