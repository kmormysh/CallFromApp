package com.samples.katy.callfromapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Katy on 3/5/2015.
 */
public class CallDatabaseHandler extends SQLiteOpenHelper implements ICallsDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "callsManager";
    private static final String TABLE_CALLS = "calls";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_TIME = "time";
    private static final String KEY_CALLTYPE = "call_type";

    public CallDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean recordsExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_ID + " FROM " + TABLE_CALLS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor.moveToNext();
    }

    @Override
    public void addCalls (Calls call) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, call.getName());
        values.put(KEY_PH_NO, call.getPhoneNumber());
        values.put(KEY_TIME, call.getTime());
        values.put(KEY_CALLTYPE, call.getCallType());

        db.insert(TABLE_CALLS, null, values);

        db.close();
    }

    @Override
    public Calls getCalls(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CALLS, new String[]{KEY_ID, KEY_NAME, KEY_PH_NO, KEY_TIME, KEY_CALLTYPE},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Calls call = new Calls(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
        return call;
    }

    @Override
    public List<Calls> getAllCalls() {
        List<Calls> callsList = new ArrayList<Calls>();
        String selectQuery = "SELECT  * FROM " + TABLE_CALLS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Calls call = new Calls();
                call.setID(cursor.getInt(0));
                call.setName(cursor.getString(1));
                call.setPhoneNumber(cursor.getString(2));
                call.setTime(cursor.getString(3));
                call.setCallType(cursor.getInt(4));

                callsList.add(call);
            } while (cursor.moveToNext());
        }

        return callsList;
    }

    @Override
    public int getCallsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CALLS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    @Override
    public int updateCalls(Calls call) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, call.getName());
        values.put(KEY_PH_NO, call.getPhoneNumber());
        values.put(KEY_TIME, call.getTime());
        values.put(KEY_CALLTYPE, call.getCallType());

        return db.update(TABLE_CALLS, values, KEY_NAME + " = ?",
                new String[]{String.valueOf(call.getName())});
    }


    @Override
    public void deleteCalls(Calls call) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CALLS, KEY_ID + " = ?", new String[]{String.valueOf(call.getID())});

        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CALLS, null, null);

        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CALLS_TABLE = "CREATE TABLE " + TABLE_CALLS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_TIME + " TEXT," + KEY_CALLTYPE + " INTEGER" + ")";
        db.execSQL(CREATE_CALLS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALLS);
        onCreate(db);
    }
}
