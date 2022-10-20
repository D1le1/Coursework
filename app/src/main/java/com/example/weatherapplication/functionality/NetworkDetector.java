package com.example.weatherapplication.functionality;


import android.app.Service;
import android.content.Context;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.security.Provider;

public class NetworkDetector {
    public static boolean isConnected(Context context)
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.getState() == NetworkInfo.State.CONNECTED)
            {
                return true;
            }
        }
        return false;
    }
}
