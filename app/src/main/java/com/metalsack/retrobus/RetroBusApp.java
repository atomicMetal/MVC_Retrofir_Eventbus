package com.metalsack.retrobus;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.metalsack.retrobus.network.ApiClient;
import com.metalsack.retrobus.utils.Prefs;
import com.metalsack.retrobus.utils.Utils;

import java.util.ArrayList;


public class RetroBusApp extends Application {
    private static RetroBusApp mInstance;
    private static Context mContext = null;
    private ApiClient mApiClient;
    public static String FROM_ID;
    public static boolean IS_SCAN_REDIRECT = false;

    public static int[] screenWH;
    private double scaleFactor = 2.7;
    public static boolean addedNewPublicForum=false;
    public static boolean isChatScreenOpen = false;

    public static ArrayList<String> arrayIssueList = new ArrayList<>();

    public static synchronized RetroBusApp getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        screenWH = Utils.getInstance().getScreenWidthHeight(mContext);

        mInstance = this;

        new Prefs.Builder()
                .setContext(getApplicationContext())
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .setDefaultIntValue(-1)
                .setDefaultBooleanValue(false)
                .setDefaultStringValue(null)
                .build();
        initApiClient();
    }

    /**
     * Initializes the api client which provides the connection to the api. The api client uses
     * {@link okhttp3.OkHttpClient} for the http connection. This allows us to modify the connection
     * properties. The response conversion is done using the
     * {@link retrofit2.converter.gson.GsonConverterFactory}.
     */
    private void initApiClient() {
        mApiClient = new ApiClient(this);
    }


    public static Context getAppContext() {
        return mContext;
    }

    public ApiClient getApiClient() {
        return mApiClient;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }


    /**
     * Return True if my service was running.
     *
     * @param serviceClass your service class name (isMyServiceRunning(CallService.class))
     * @return
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
