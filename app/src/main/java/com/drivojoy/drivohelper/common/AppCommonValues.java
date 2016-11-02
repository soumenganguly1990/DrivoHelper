package com.drivojoy.drivohelper.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import com.drivojoy.drivohelper.R;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by sam on 10/29/2016.
 */
public class AppCommonValues {

    public static final int SPLASH_DELAY = 200;
    public static final int ACTIVITY_CHANGE_DELAY = 200;
    public static final int URLACTIVITY = 1;
    public static final int MAPACTIVITY = 2;
    public static final int ABOUTACTIVITY = 3;
    public static final String API_BASE_URL = "http://drivojoydev.cloudapp.net:9001/";
    public static final int URL_ERROR = 1001;
    public static final int URL_RETRIEVAL_UNSUCCESSFUL = 1002;
    public static final int NO_NETWORK = 1004;
    public static final int LOCATION_DISABLED = 1005;

    public static final double INDIA_LAT = 20.5937;
    public static final double INDIA_LONG = 78.9629;
    public static final int ZOOM_LEVEL = 4;
    public static final float GEO_FENCE_RADIUS = 10.0f;

    /**
     * @task checks whether an active internet connection is present or not
     * @param context
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean wifiDataAvailable = false;
        boolean mobileDataAvailable = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
        try {
            for (NetworkInfo netInfo : networkInfo) {
                if (netInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                    if (netInfo.isConnected())
                        wifiDataAvailable = true;
                }
                if (netInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                    if (netInfo.isConnected())
                        mobileDataAvailable = true;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return wifiDataAvailable || mobileDataAvailable;
    }

    /**
     * @task displays a material alertdialog with custom messages
     * @param context
     *        used to spawn the dialog
     * @param whatIsTheReason
     *        used to identify and customize message
     */
    public static void showCommonErrorDialog(Context context, int whatIsTheReason) {
        final MaterialDialog materialDialog = new MaterialDialog(context);
        materialDialog.setTitle(context.getString(R.string.app_name));
        switch (whatIsTheReason) {
            case AppCommonValues.URL_ERROR:
                materialDialog.setMessage("Sorry, some error occurred while trying to connect to server");
                break;
            case AppCommonValues.URL_RETRIEVAL_UNSUCCESSFUL:
                materialDialog.setMessage("Sorry, data could not be retrieved successfully");
                break;
            case AppCommonValues.NO_NETWORK:
                materialDialog.setMessage("Sorry, but you need an active internet connection to fetch data");
                break;
            case AppCommonValues.LOCATION_DISABLED:
                materialDialog.setMessage("Please make sure location service is enabled");
            default:
                materialDialog.setMessage("Sorry, but it seems some error has occurred");
                break;
        }
        materialDialog.setCanceledOnTouchOutside(false);
        materialDialog.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }
}
