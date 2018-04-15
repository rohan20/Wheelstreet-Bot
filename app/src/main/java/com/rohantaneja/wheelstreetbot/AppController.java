package com.rohantaneja.wheelstreetbot;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

/**
 * Created by rohantaneja on 15/04/18.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Hawk.init(this).build();
    }
}
