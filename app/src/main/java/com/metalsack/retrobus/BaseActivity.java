package com.metalsack.retrobus;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.metalsack.retrobus.network.ApiClient;
import com.metalsack.retrobus.utils.Logger;
import com.metalsack.retrobus.utils.PermissionResult;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * @author DearDhruv
 */
public abstract class BaseActivity extends Activity { //FragmentActivity {

    private static final String LOGTAG = "AbstractActivity";
    private static final Logger LOG = new Logger(LOGTAG);

    EventBus mEventBus;
    public ApiClient mApiClient;
    private Handler handler;
    private CustomDialog customDialog;
    private int KEY_PERMISSION = 0;
    private PermissionResult permissionResult;
    private String permissionsAsk[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FooDetectApp app = ((FooDetectApp) getApplication());
        mApiClient = getApp().getApiClient();
        mEventBus = EventBus.getDefault();
    }

    protected void showMsg(String msg) {
        showToastMsg(msg);
    }

    protected void showToastMsg(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    public void onStop() {
        mEventBus.unregister(this);
        super.onStop();
    }
    protected void centerTitle(Toolbar toolbar) {
        final CharSequence originalTitle = toolbar.getTitle();
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        mTitle.setSelected(true);
        mTitle.setText(originalTitle);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
    }
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public RetroBusApp getApp() {
        return (RetroBusApp) getApplication();
    }

    protected void initDialog() {
        customDialog = new CustomDialog(this);
        handler = new Handler();
    }

    protected void dismissProgress() {
        if (handler != null &&  customDialog != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    customDialog.hide();
                }
            });
        }
    }

    protected void showProgress() {
        if (handler != null && customDialog != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (!customDialog.isDialogShowing()) {
                        customDialog.show();
                    }

                }// hideKeyboard(edt);}});}}
            });
        }
    }
    /**
     * Checks if Application Permission is Granted
     *
     * @param context
     * @param permission
     * @return
     */
    public boolean isPermissionGranted(Context context, String permission) {
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) ||
                (ActivityCompat.checkSelfPermission(context, permission) ==
                        PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Checks if Application Permissions are Granted
     *
     * @param context
     * @param permissions
     * @return
     */
    public boolean isPermissionsGranted(Context context, String permissions[]) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        boolean granted = true;
        for (String permission : permissions) {
            if (!(ActivityCompat.checkSelfPermission(
                    context, permission) == PackageManager.PERMISSION_GRANTED))
                granted = false;
        }
        return granted;
    }

    /**
     * Requests Application Permissions
     *
     * @param permissionAsk
     */
    private void internalRequestPermission(String[] permissionAsk) {
        String arrayPermissionNotGranted[];
        ArrayList<String> permissionsNotGranted = new ArrayList<>();

        for (String aPermissionAsk : permissionAsk) {
            if (!isPermissionGranted(this, aPermissionAsk)) {
                permissionsNotGranted.add(aPermissionAsk);
            }
        }

        if (permissionsNotGranted.isEmpty()) {

            if (permissionResult != null)
                permissionResult.permissionGranted();

        } else {

            arrayPermissionNotGranted = new String[permissionsNotGranted.size()];
            arrayPermissionNotGranted = permissionsNotGranted.toArray(arrayPermissionNotGranted);
            ActivityCompat.requestPermissions(this,
                    arrayPermissionNotGranted, KEY_PERMISSION);
        }
    }

    /**
     * Callback for the PermissionResult
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode != KEY_PERMISSION) {
            return;
        }

        List<String> permissionDenied = new LinkedList<>();
        boolean granted = true;

        for (int i = 0; i < grantResults.length; i++) {

            if (!(grantResults[i] == PackageManager.PERMISSION_GRANTED)) {
                granted = false;
                permissionDenied.add(permissions[i]);
            }
        }

        if (permissionResult != null) {
            if (granted) {
                permissionResult.permissionGranted();
            } else {
                for (String s : permissionDenied) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, s)) {
                        permissionResult.permissionForeverDenied();
                        return;
                    }
                }
                permissionResult.permissionDenied();
            }
        }
    }

    public void askCompactPermission(String permission, PermissionResult permissionResult) {
        KEY_PERMISSION = 200;
        permissionsAsk = new String[]{permission};
        this.permissionResult = permissionResult;
        internalRequestPermission(permissionsAsk);
    }

    /**
     * Requests given Permissions.
     *
     * @param permissions
     * @param permissionResult
     */
    public void askCompactPermissions(String permissions[], PermissionResult permissionResult) {
        KEY_PERMISSION = 200;
        permissionsAsk = permissions;
        this.permissionResult = permissionResult;
        internalRequestPermission(permissionsAsk);
    }

}
