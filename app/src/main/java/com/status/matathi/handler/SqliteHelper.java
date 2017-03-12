package com.status.matathi.handler;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mahesh on 2/8/2017.
 */

public interface SqliteHelper {
    String DATABASE_NAME = "marathi.sqlite";
    String DATABASE_FILE = "marathi.zip";
    int DB_VERSION_1 = 1;

    void onCreate(SQLiteDatabase database);

    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

}
