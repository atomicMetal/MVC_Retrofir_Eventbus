package com.metalsack.retrobus.utils;

import android.os.Build;
import com.metalsack.retrobus.BuildConfig;


/**
 * Log class wrapper. Shows only log messages, if app is in debug mode!
 */
public class Logger {
    private static boolean showLogMessages;
    private static String tag;

    /**
     * Constructor which requires a logtag for the Log messages. Log messages are always shown in
     * Debug mode.
     *
     * @param tag - Logtag of the class
     */
    public Logger(final String tag) {
        this(tag, BuildConfig.DEBUG);
    }

    /**
     * Constructor which requires a logtag for the log messages and a flag if the log messages
     * should be shown or not
     *
     * @param tag                   - Logtag of the class
     * @param showAlwaysLogMessages - enable or disable log messages
     */
    public Logger(final String tag, final boolean showAlwaysLogMessages) {
        this.tag = tag;
        showLogMessages = showAlwaysLogMessages;
    }

    /**
     * {@link android.util.Log#d(String, String)}
     */
    public static void d(final String msg) {
        if (showLogMessages) {
            android.util.Log.d(tag, msg);
        }
    }

    /**
     * {@link android.util.Log#d(String, String, Throwable)}
     */
    public static void d(final String msg, Throwable t) {
        if (showLogMessages) {
            android.util.Log.d(tag, msg, t);
        }
    }

    /**
     * {@link android.util.Log#e(String, String)}
     */
    public static void e(final String msg) {
        if (showLogMessages) {
            android.util.Log.e(tag, msg);
        }
    }

    /**
     * {@link android.util.Log#e(String, String, Throwable)}
     */
    public static void e(final String msg, Throwable t) {
        if (showLogMessages) {
            android.util.Log.e(tag, msg, t);
        }
    }

    /**
     * {@link android.util.Log#i(String, String)}
     */
    public static void i(final String msg) {
        if (showLogMessages) {
            android.util.Log.i(tag, msg);
        }
    }

    /**
     * {@link android.util.Log#i(String, String, Throwable)}
     */
    public static void i(final String msg, Throwable t) {
        if (showLogMessages) {
            android.util.Log.i(tag, msg, t);
        }
    }

    /**
     * {@link android.util.Log#w(String, String)}
     */
    public static void w(final String msg) {
        if (showLogMessages) {
            android.util.Log.w(tag, msg);
        }
    }

    /**
     * {@link android.util.Log#w(String, String, Throwable)}
     */
    public static void w(final String msg, Throwable t) {
        if (showLogMessages) {
            android.util.Log.w(tag, msg, t);
        }
    }

    /**
     * {@link android.util.Log#wtf(String, String)}
     */
    public static void wtf(final String msg) {
        if (showLogMessages) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                android.util.Log.wtf(tag, msg);
            } else {
                android.util.Log.e(tag, msg);
            }
        }
    }

    /**
     * {@link android.util.Log#wtf(String, String, Throwable)}
     */
    public static void wtf(Throwable t) {
        if (showLogMessages) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                android.util.Log.wtf(tag, t);
            } else {
                android.util.Log.e(tag, t.getMessage(), t);
            }
        }
    }

    /**
     * {@link android.util.Log#wtf(String, String, Throwable)}
     */
    public static void wtf(final String msg, Throwable t) {
        if (showLogMessages) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                android.util.Log.wtf(tag, msg, t);
            } else {
                android.util.Log.e(tag, msg, t);
            }
        }
    }
}
