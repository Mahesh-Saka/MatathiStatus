package com.status.matathi.handler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Mahesh on 2/8/2017.
 */

public class MarathiSmsDBHandler implements SqliteHelper {

    Context context;
    public static final String TABLE_NAME = "MARATHI_STATUS";
    public static final String COLUMN_STATUS = "sStatus";

    public MarathiSmsDBHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<String> getAllStatusByCatagories(int catID) {
        ArrayList<String> arrayList = null;
        Cursor cursor = null;
        String query = null;
        if (catID > 0)
            query = "Select * from " + TABLE_NAME + " where  Cat=" + catID;
        else
            query = "Select * from " + TABLE_NAME + " limit 3000 ";
        try {
            arrayList = new ArrayList<String>();
            cursor = MarathiDBHandler.getDatabase().rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    arrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (arrayList != null)
            Collections.reverse(arrayList);
        return arrayList;
    }
}
