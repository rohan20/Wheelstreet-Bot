package com.rohantaneja.wheelstreetbot.model;

import android.util.Log;

import com.rohantaneja.wheelstreetbot.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by rohantaneja on 15/04/18.
 */

public class User {

    private long id;
    private String name;
    private String email;
    private String birthday;
    private int gender;
    private int age;
    //initially, age is calculated from dob. But the user can update their age, then there'd be no use of the age calculation from dob
    private int isAgeOverridden;
    private String avatarUrl;
    private String avatarPath;
    //user can update avatar and upload from camera/gallery, then it won't have a url but would have a file path
    private int isAvatarFromPath;

    public User(long id, String name, String email, String birthday, int gender, int age, int isAgeOverridden, String avatarUrl, String avatarPath, int isAvatarFromPath) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.age = age;
        this.isAgeOverridden = isAgeOverridden;
        this.avatarUrl = avatarUrl;
        this.avatarPath = avatarUrl;
        this.isAvatarFromPath = isAvatarFromPath;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getGender() {
        return gender;
    }

    public int getAge() {
        if (isAgeOverridden == Constants.IS_AGE_OVERRIDDEN_TRUE)
            return age;

        return calculateAgeFromDob();
    }

    public int getIsAgeOverridden() {
        return isAgeOverridden;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public int getIsAvatarFromPath() {
        return isAvatarFromPath;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setIsAgeOverridden(int isAgeOverridden) {
        this.isAgeOverridden = isAgeOverridden;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setIsAvatarFromPath(int avatarFromPath) {
        isAvatarFromPath = avatarFromPath;
    }

    private int calculateAgeFromDob() {

        int age = 0;


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar dateOfBirthCalendar = Calendar.getInstance();

        try {
            dateOfBirthCalendar.setTime(sdf.parse(birthday));
        } catch (ParseException e) {
            Log.d(getClass().getName(), "Invalid DOB, can't calculate age.");
        }

        Calendar todayCalendar = Calendar.getInstance();
        int currentYear = todayCalendar.get(Calendar.YEAR);
        int dobYear = dateOfBirthCalendar.get(Calendar.YEAR);
        age = currentYear - dobYear;

        // if dob is month or day is behind today's month or day, reduce age by 1
        int currentMonth = todayCalendar.get(Calendar.MONTH);
        int dobMonth = dateOfBirthCalendar.get(Calendar.MONTH);

        if (dobMonth > currentMonth) {
            // this year shouldn't be counted!
            age--;
        } else if (dobMonth == currentMonth) {
            // same month? compare dates
            int curDay = todayCalendar.get(Calendar.DAY_OF_MONTH);
            int dobDay = dateOfBirthCalendar.get(Calendar.DAY_OF_MONTH);

            if (dobDay > curDay) {
                // birthday not here yet, this year shouldn't be counted!
                age--;
            }
        }

        return age;
    }
}
