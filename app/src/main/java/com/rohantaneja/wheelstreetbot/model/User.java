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

    private String id;
    private String name;
    private String email;
    private String birthday;
    private String gender;
    private String mobile;
    private String age;
    //initially, age is calculated from dob. But the user can update their age, then there'd be no use of the age calculation from dob
    private String isAgeOverridden;
    private String avatarUrl;
    private String avatarPath;
    //user can update avatar and upload from camera/gallery, then it won't have a url but would have a file path
    private String isAvatarFromPath;

    public User(String id, String name, String email, String birthday, String gender, String mobile, String age, String isAgeOverridden, String avatarUrl, String avatarPath, String isAvatarFromPath) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.mobile = mobile;
        this.age = age;
        this.isAgeOverridden = isAgeOverridden;
        this.avatarUrl = avatarUrl;
        this.avatarPath = avatarUrl;
        this.isAvatarFromPath = isAvatarFromPath;
    }

    public String getId() {
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

    public String getGender() {

        //To change "male" to "Male" and "female" to "Female"
        if (gender.equalsIgnoreCase(Constants.GENDER_MALE))
            gender = Constants.GENDER_MALE;

        else if (gender.equalsIgnoreCase(Constants.GENDER_FEMALE))
            gender = Constants.GENDER_FEMALE;

        return gender;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAge() {
        if (isAgeOverridden.equalsIgnoreCase(Constants.IS_AGE_OVERRIDDEN_TRUE))
            return age;

        return calculateAgeFromDob();
    }

    public String getIsAgeOverridden() {
        return isAgeOverridden;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public String getIsAvatarFromPath() {
        return isAvatarFromPath;
    }

    public void setId(String id) {
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setIsAgeOverridden(String isAgeOverridden) {
        this.isAgeOverridden = isAgeOverridden;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setIsAvatarFromPath(String avatarFromPath) {
        isAvatarFromPath = avatarFromPath;
    }

    private String calculateAgeFromDob() {

        int age = 0;

        Log.d(getClass().getName(), "birthday: " + birthday);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar dateOfBirthCalendar = Calendar.getInstance();

        try {
            dateOfBirthCalendar.setTime(sdf.parse(birthday));
        } catch (ParseException e) {
            Log.d(getClass().getName(), "Invalid DOB, can't calculate age.");
        }

        Calendar todayCalendar = Calendar.getInstance();
        int currentYear = todayCalendar.get(Calendar.YEAR);
        Log.d(getClass().getName(), "currentYear: " + currentYear);
        int dobYear = dateOfBirthCalendar.get(Calendar.YEAR);
        Log.d(getClass().getName(), "dobYear: " + dobYear);
        age = currentYear - dobYear;
        Log.d(getClass().getName(), "age: " + age);

        // if dob is month or day is behind today's month or day, reduce age by 1
        int currentMonth = todayCalendar.get(Calendar.MONTH);
        Log.d(getClass().getName(), "currentMonth: " + currentMonth);
        int dobMonth = dateOfBirthCalendar.get(Calendar.MONTH);
        Log.d(getClass().getName(), "dobMonth: " + dobMonth);

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

        return String.valueOf(age);
    }
}
