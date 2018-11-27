package com.mujirenben.android.common.util;

import android.util.Log;

public class Logger {

    public static void dump(String tag, Object msg) {
        Log.d(tag, msg.toString());
    }
}
