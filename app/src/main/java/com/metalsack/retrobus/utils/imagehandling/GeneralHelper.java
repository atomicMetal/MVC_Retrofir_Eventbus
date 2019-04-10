package com.metalsack.retrobus.utils.imagehandling;

import android.database.Cursor;

/**
 * Created by dhruv on 16/3/16.
 */
public class GeneralHelper {


    public static void closeCursor(Cursor c) {
        if (c != null && !c.isClosed()) {
            c.close();
        }
    }
}
