package com.metalsack.retrobus.utils;


import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Utils {
    private static Utils instance;
    private Utils() {
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    /**
     * Return True if my service was running.
     *
     * @param serviceClass your service class name (isMyServiceRunning(MyService.class))
     * @param context
     * @return
     */
    public boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public int getAge(int _year, int month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH)) || ((m == cal.get(Calendar.MONTH)) && (d < cal.get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        return a;
    }

    public boolean isValidEmail(CharSequence target) {
        if (target == null || target.length() < 1) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public void launchKeyboard(final Context mContext, final EditText mEditText) {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(mEditText, 0);
            }
        }, 100);
    }

    public int convertToPixel(int value, Context context) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, r.getDisplayMetrics());
        return (int) px;
    }


    public static void ButtonClickEffect(final View v) {
        // v.setEnabled(false);

        AlphaAnimation obja = new AlphaAnimation(1.0f, 0.3f);
        obja.setDuration(5);
        obja.setFillAfter(false);
        v.startAnimation(obja);
    }

    public int[] getScreenWidthHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth(); // deprecated
        int height = display.getHeight(); // deprecated

        int[] wh = {width, height};

        return wh;
    }

}