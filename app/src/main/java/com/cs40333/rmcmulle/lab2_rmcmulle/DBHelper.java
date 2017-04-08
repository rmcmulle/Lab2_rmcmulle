package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ryan on 4/8/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "teams.db";
    public static int DATABASE_VERSION = 1;
    public static String TABLE_TEAM = "Team";
    public static String COL_ID = "_id";
    public static String COL_LOGO = "team_logo";
    public static String COL_NAME = "team_name";
    public static String COL_DATE = "team_date";
    public static String COL_DAY = "team_day";
    public static String COL_TIME = "team_time";
    public static String COL_MASCOT = "team_mascot";
    public static String COL_RECORD = "team_record";
    public static String COL_SCORE = "team_score";
    public static String COL_SCORE_TIME = "team_score_time";
    public static String COL_LOCATION = "team_location";

    public DBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TEAM + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_LOGO + " TEXT, " + COL_NAME + " TEXT, " + COL_DATE + " TEXT, " + COL_DAY + " TEXT, " +
                COL_TIME + " TEXT, " +COL_MASCOT + " TEXT, " + COL_RECORD + " TEXT, " + COL_SCORE
                + " TEXT, " + COL_SCORE_TIME + " TEXT, " + COL_LOCATION + " TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists " + TABLE_TEAM );
        onCreate(db);
    }

    public void insertData(String tblname, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();

        long ret = db.insert(tblname, null, contentValues );

        if (ret > -1) {
            System.out.println("Successfully inserted");
        } else {
            System.out.println("Insert Unsuccessful");
        }

        db.close();
    }

    public void deleteData(String tblname, String clause, int _id) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(tblname, clause, new String[]{Integer.toString(_id)});
        db.close();
    }


    public Cursor getAllEntries(String tblname, String[] columns) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tblname, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getSelectEntries(String tblname, String[] columns, String where, String[] args) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tblname, columns, where, args, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public String[] getTableFields(String tblname) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor dbCursor = db.query(tblname, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        return columnNames;
    }
}