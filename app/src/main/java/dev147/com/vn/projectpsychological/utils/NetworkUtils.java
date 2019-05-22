package dev147.com.vn.projectpsychological.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    /**
     * used to check connected
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return false;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (null != activeNetwork) && activeNetwork.isConnected() && activeNetwork.isAvailable();
    }

}
