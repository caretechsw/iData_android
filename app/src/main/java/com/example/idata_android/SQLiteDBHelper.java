package com.example.idata_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.idata_android.Model.Temperature;

import java.sql.Timestamp;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "idata";
    public static final String TABLE_TEMP = "temperature";
    public static final String COLUMN_TEMP_ID_LOCAL = "temperature_id_local";
    public static final String COLUMN_TEMP = "temperature";
    public static final String COLUMN_ELDER_ID = "elder_id";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_STATUS = "status";


    //database version
    private static final int DB_VERSION = 1;

    public SQLiteDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql = " CREATE TABLE IF NOT EXISTS " + TABLE_TEMP
                + "("
                + COLUMN_TEMP_ID_LOCAL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TEMP + " DOUBLE NOT NULL, "
                + COLUMN_ELDER_ID + " INTEGER, "
                +COLUMN_TIMESTAMP + " TIMESTAMP); ";

        String sql2 = " ALTER TABLE " + TABLE_TEMP + " AUTO_INCREMENT = 1 ";

        db.execSQL(sql+sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = " DROP TABLE IF EXISTS "+ TABLE_TEMP;
        db.execSQL(sql);
    }

    /*
     * This method is taking two arguments
     * first one is the name that is to be saved
     * second one is the status
     * 1 means the name is synced with the server
     * 0 means the name is not synced with the server
     * */
    public boolean addTemp(double temp, int elder_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEMP, temp);
        contentValues.put(COLUMN_ELDER_ID, elder_id);
        contentValues.put(COLUMN_TIMESTAMP, new Timestamp(System.currentTimeMillis()).toString());


        db.insert(TABLE_TEMP, null, contentValues);
        db.close();
        Log.i(TAG, "addTemp");
        return true;
    }

    /*
     * This method taking two arguments
     * first one is the temp_id of the temperature for which
     * we have to update the sync status
     * and the second one is the status that will be changed
     * */
    public boolean updateTempStatus(int temperature_id_local, double temp, int elder_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEMP_ID_LOCAL, temperature_id_local);
        contentValues.put(COLUMN_TEMP, temp);
        contentValues.put(COLUMN_ELDER_ID, elder_id);
       // contentValues.put(COLUMN_TIMESTAMP, timestamp.getTime());

        db.update(TABLE_TEMP, contentValues, COLUMN_TEMP_ID_LOCAL + "=" + temperature_id_local, null);
        db.close();
        return true;
    }

    /*
     * this method will give us all the temps stored in sqlite
     * */
    public Cursor getTemps() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = " SELECT * FROM " + TABLE_TEMP + " ORDER BY " + COLUMN_TEMP_ID_LOCAL + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /*
     * this method is for getting all the unsynced temps
     * so that we can sync it with database
     * */
    public Cursor getUnsyncedTemps() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_TEMP + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    static String TAG ="SQLiteDBHelper";
}
