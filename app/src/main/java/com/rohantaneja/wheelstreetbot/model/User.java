package com.rohantaneja.wheelstreetbot.model;

import android.util.Log;

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
    private int age;
    private String gender;
    private String avatarUrl;

    public User(long id, String name, String email, String birthday, String gender, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
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

    public int getAge() {
        return calculateAgeFromDob();
    }

    public String getGender() {
        return gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
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

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
