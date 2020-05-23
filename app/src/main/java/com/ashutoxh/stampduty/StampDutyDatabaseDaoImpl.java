package com.ashutoxh.stampduty;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StampDutyDatabaseDaoImpl extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "stampDutyDB";
    private static final String TABLE_NAME = "stampDutyPartyInfo";
    private static final String KEY_ID = "KEY_ID";
    private static final String PAN_NO = "PAN_NO";
    private static final String KEY_NAME = "KEY_NAME";
    private static final String BLOCK_NO = "BLOCK_NO";
    private static final String ROAD = "ROAD";
    private static final String CITY = "CITY";
    private static final String PIN = "PIN";


    public StampDutyDatabaseDaoImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_NAME + " TEXT,"
                    + PAN_NO + " TEXT,"
                    + BLOCK_NO + " TEXT,"
                    + ROAD + " TEXT,"
                    + CITY + " TEXT,"
                    + PIN + " TEXT"
                    + ")";
            Log.d("DB", "Table script is : " + CREATE_TABLE);
            db.execSQL(CREATE_TABLE);

            //Insert
            insertData("None", "", "", "", "", "", db);
            insertData("K B IMPEX", "AAPFK7876D", "77 RATNA JYOT INDUSTRIAL PREMISES", "IRLA GAWTHAN LANE"
                    , "VILE PARLE MUMBAI", "400056", db);
            insertData("HARMONY PHARMA GLASS CO", "AAIFH0443E", "H NO 550 AT JASKHAR", "POST JNPT"
                    , "RAIGAD MAHARASHTRA", "400702", db);
        } catch (Exception e) {
            Log.e("StampDutyDatabaseDaoImp", "onCreate() : " + e.getMessage());
        }
    }

    public void insertData(String KEY_NAME, String PAN_NO, String BLOCK_NO, String ROAD, String CITY, String PIN, SQLiteDatabase database) {
        //Insert
        ContentValues values = new ContentValues();
        values.put("KEY_NAME", KEY_NAME.toUpperCase());
        values.put("PAN_NO", PAN_NO.toUpperCase());
        values.put("BLOCK_NO", BLOCK_NO.toUpperCase());
        values.put("ROAD", ROAD.toUpperCase());
        values.put("CITY", CITY.toUpperCase());
        values.put("PIN", PIN);
        database.insert(TABLE_NAME, null, values);
        //database.close();
        //values.clear();
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
