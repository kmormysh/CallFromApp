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
public class ContactDatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_PHONES = "phones";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_IMAGE = "image";

    public ContactDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean recordsExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_ID + " FROM " + TABLE_CONTACTS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor.moveToNext();
    }

    @Override
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber().get(0));
        values.put(KEY_IMAGE, contact.getImage_url());

        db.insert(TABLE_CONTACTS, null, values);

        addPhones(contact.getPhoneNumber().get(0), contact.getName());

        db.close();
    }

    public void addPhones(String phone, String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PH_NO, phone);

        db.insert(TABLE_PHONES, null, values);
    }

    @Override
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_NAME, KEY_PH_NO, KEY_IMAGE},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        List<String> phoneNumber = new ArrayList<>();
        phoneNumber.add(cursor.getString(2));

        Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), phoneNumber, cursor.getString(3));
        contact.setPhoneNumber(getPhones(db, contact.getName()));

        return contact;
    }

    public List<String> getPhones(SQLiteDatabase db, String name) {
        List<String> phones = new ArrayList<>();

        Cursor cursor = db.query(TABLE_PHONES, new String[]{KEY_NAME, KEY_PH_NO},
                KEY_NAME + "=?",
                new String[]{String.valueOf(name)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (cursor.moveToNext())
            phones.add(cursor.getString(1));
        return phones;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                List<String> phoneNumbers = new ArrayList<>();
                Contact contact = new Contact();
                contact.setID(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                phoneNumbers.add(cursor.getString(2));
                contact.setPhoneNumber(phoneNumbers);

                contact.setPhoneNumber(getPhones(db, cursor.getString(1)));

                contact.setImage_url(cursor.getString(3));

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    @Override
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    @Override
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values_c = new ContentValues();
        values_c.put(KEY_NAME, contact.getName());
        values_c.put(KEY_PH_NO, contact.getPhoneNumber().get(0));
        values_c.put(KEY_IMAGE, contact.getImage_url());

        ContentValues values_p = new ContentValues();
        values_c.put(KEY_NAME, contact.getName());
        values_c.put(KEY_PH_NO, contact.getPhoneNumber().get(0));

        db.update(TABLE_PHONES, values_p, KEY_NAME + " = ?",
                new String[]{String.valueOf(contact.getName())});

        return db.update(TABLE_CONTACTS, values_c, KEY_NAME + " = ?",
                new String[]{String.valueOf(contact.getName())});
    }


    @Override
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[]{String.valueOf(contact.getID())});

        db.delete(TABLE_PHONES, KEY_NAME + " = ?", new String[]{String.valueOf(contact.getName())});

        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null, null);

        db.delete(TABLE_PHONES, null, null);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_PHONES_TABLE = "CREATE TABLE " + TABLE_PHONES + "("
                + KEY_NAME + " TEXT," + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_PHONES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHONES);

        onCreate(db);
    }
}
