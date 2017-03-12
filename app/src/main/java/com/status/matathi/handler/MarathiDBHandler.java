package com.status.matathi.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Mahesh on 2/8/2017.
 */

public class MarathiDBHandler extends SQLiteOpenHelper implements SqliteHelper {

    static SQLiteDatabase mSQLiteDatabase;
    Context context;
    private static String DATABASE_PATH = "";
    String file;
    String loc;


    public MarathiDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION_1);
        this.context = context;
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openDatabase();
        mSQLiteDatabase = this.getWritableDatabase();
    }

    public void createDataBase() throws IOException {
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            decompress();
           /* try {
                copyDatabase();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }*/
        }
    }

    private boolean checkDataBase() {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        return file.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new MarathiSmsDBHandler(context).onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static SQLiteDatabase getDatabase() {
        return mSQLiteDatabase;
    }

    protected static int createRecord(String tableName, ContentValues values) {
        return (int) mSQLiteDatabase.insert(tableName, null, values);
    }

    protected void deleteRecord(String tableName) {
        mSQLiteDatabase.delete(tableName, null, null);
    }

    public static void releaseResoruces(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    protected int updateRecord(String tableName, String key, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(key, value);
        return mSQLiteDatabase.update(tableName, contentValues, null, null);
    }

    public void copyDatabase() throws IOException {
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);
        String file = DATABASE_PATH + DATABASE_NAME;
        OutputStream outputStream = new FileOutputStream(file);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = inputStream.read(mBuffer)) > 0) {
            outputStream.write(mBuffer, 0, mLength);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public boolean openDatabase() {
        String database = DATABASE_PATH + DATABASE_NAME;
        mSQLiteDatabase = SQLiteDatabase.openDatabase(database, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mSQLiteDatabase != null;
    }

    public MarathiSmsDBHandler getMarathiSmsDBHandler() {
        return new MarathiSmsDBHandler(context);
    }

    @Override
    public synchronized void close() {
        if (mSQLiteDatabase != null)
            mSQLiteDatabase.close();
        super.close();
    }

    public void decompress() {
        try {
            byte[] buffer = new byte[4098];
            ZipInputStream zin = new ZipInputStream(context.getAssets().open(DATABASE_FILE));
            ZipEntry ze = null;
            int size;
            while ((ze = zin.getNextEntry()) != null) {
                FileOutputStream fout = new FileOutputStream(DATABASE_PATH + ze.getName());
                BufferedOutputStream bos = new BufferedOutputStream(fout, buffer.length);
                while ((size = zin.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, size);
                }
                bos.flush();
                bos.close();
                fout.flush();
                fout.close();
                zin.closeEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
