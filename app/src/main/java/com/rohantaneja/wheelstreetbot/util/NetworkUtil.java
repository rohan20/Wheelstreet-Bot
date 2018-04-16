package com.rohantaneja.wheelstreetbot.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Created by rohantaneja on 16/04/18.
 */

public class NetworkUtil {

    private static boolean mConnectionAvailable = false;
    private static Context mContext;


    public static void checkNetworkConnectivity(Context context) {
        try {
            mConnectionAvailable = false;
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                mConnectionAvailable = false;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Network[] networks = connectivity.getAllNetworks();
                    for (Network mNetwork : networks) {
                        NetworkInfo networkInfo = connectivity.getNetworkInfo(mNetwork);
                        if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                            mConnectionAvailable = true;
                            break;
                        }
                    }
                } else {
                    NetworkInfo[] info = connectivity.getAllNetworkInfo();
                    if (info != null) {
                        for (int i = 0; i < info.length; i++) {
                            if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                                mConnectionAvailable = true;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mConnectionAvailable = false;
        }

    }

    public static NetworkInfo getNetworkType(Context context) {
        if (context != null) {
            NetworkInfo networkinfo;
            try {
                networkinfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            } catch (Exception exception) {
                return null;
            }
            return networkinfo;
        } else {
            return null;
        }
    }

    public static boolean isNetworkAvailable() {
        return mConnectionAvailable;
    }

    private static int getNetworkType() {
        if (mContext != null) {
            ConnectivityManager connectivitymanager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivitymanager != null) {
                NetworkInfo networkinfo = connectivitymanager.getNetworkInfo(1);
                if (networkinfo != null && networkinfo.isConnected()) {
                    return 1;
                }
            }
            int i = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkType();
            if (i > 0 && i < 3) {
                return 3;
            }
            return i <= 2 ? 4 : 2;
        } else {
            return -1;
        }
    }

}
