package com.rohantaneja.wheelstreetbot.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.rohantaneja.wheelstreetbot.BuildConfig;
import com.rohantaneja.wheelstreetbot.R;
import com.rohantaneja.wheelstreetbot.model.User;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by rohantaneja on 16/04/18.
 */

public class Utils {

    public static final String TAG = "Utils";

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getDrawable(drawableId);

        //noinspection deprecation
        return context.getResources().getDrawable(drawableId);
    }


    public static boolean isSimulator() {
        boolean isSimulator = "google_sdk".equals(Build.PRODUCT)
                || "vbox86p".equals(Build.PRODUCT)
                || "sdk".equals(Build.PRODUCT);
        Log.d(TAG, "Build.PRODUCT= " + Build.PRODUCT + "  isSimulator= "
                + isSimulator);

        return isSimulator;
    }

    public static int dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static String convertLocalTimeToUTCString(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(calendar.getTime());
    }

    public static Uri getUri(Context context, File file, boolean isFileProvider) {
        if (isFileProvider) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        }
        return Uri.fromFile(file);

    }

    public static Uri getUri(Context context, File file) {
        return getUri(context, file, false);
    }


    public static String getDOBFromMillis(long millis) {

        Date date = new Date(millis);
        DateFormat format = new SimpleDateFormat("dd MMMM");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(date);
        format.setTimeZone(TimeZone.getDefault());//your zone
        formatted = format.format(date);
        return formatted;
    }

    public static String getYear(String yy) {
//        try {
//            DateFormat formatter = new SimpleDateFormat("yy");
//            Date date = null;
//            date = (Date) formatter.parse(yy);
//            formatter = new SimpleDateFormat("yyyy");
//            return formatter.format(date);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return 20 + yy;
    }

    public static boolean compareYear(String year) {
        try {

            DateFormat formatter = new SimpleDateFormat("yyyy");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(formatter.parse(year));

            if (calendar.get(Calendar.YEAR) < (Calendar.getInstance().get(Calendar.YEAR)))
                return false;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getDOB(Date date) {

        DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        return format.format(date);
    }

    public static String getPicassoPath(User user) {

        String pathOrUrl;

        //set user image
        if (user.getIsAvatarFromPath().equalsIgnoreCase(Constants.IS_AVATAR_FROM_PATH_TRUE)) {
            pathOrUrl = Constants.PICASSO_FILE_PREFIX + user.getAvatarPath();
        } else {
            pathOrUrl = user.getAvatarUrl();
        }

        return pathOrUrl;

    }


}
