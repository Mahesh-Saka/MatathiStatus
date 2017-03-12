package com.status.matathi.handler;

import android.content.Context;

/**
 * Created by Mahesh on 11/28/2016.
 */

public class DataBaseManager {
    private static MarathiDBHandler appDBHandler;

    public static synchronized MarathiDBHandler getDatabase(Context context) {
        if (appDBHandler == null) {
            appDBHandler = new MarathiDBHandler(context);
        }
        return appDBHandler;
    }
}
