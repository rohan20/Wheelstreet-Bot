package com.rohantaneja.wheelstreetbot.util;

import java.util.regex.Pattern;

/**
 * Created by rohantaneja on 16/04/18.
 */

public class StringUtil {

    public static boolean isNullOrEmpty(String data) {
        return (data == null || data.isEmpty());
    }


    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email))
            return false;

        return Pattern.compile("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$").matcher(email).find();
    }

    public static boolean isValidMobileNumber(String mobileNumber) {
        return Pattern.compile("^([0-9]{10})$").matcher(mobileNumber).find();
    }

    public static boolean isValidLongValue(String s) {
        if (isNullOrEmpty(s))
            return false;

        try {
            Long.parseLong(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidIntegerValue(String s) {
        if (isNullOrEmpty(s))
            return false;

        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
