package com.deal70.mapp.deal70.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.deal70.mapp.deal70.model.DealEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcvarma on 14/02/16.
 */
public class Deal70DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Deal70.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String CREATE_TBLE_DEAL_DATA =
            "CREATE TABLE " + Deal70Db.DealEntryTable.TABLE_NAME + " (" +
                    Deal70Db.DealEntryTable._ID + " INTEGER PRIMARY KEY," +
                    Deal70Db.DealEntryTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    Deal70Db.DealEntryTable.COLUMN_NAME_DESCR + TEXT_TYPE + COMMA_SEP +
                    Deal70Db.DealEntryTable.COLUMN_NAME_URL + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Deal70Db.DealEntryTable.TABLE_NAME;

    public Deal70DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBLE_DEAL_DATA);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void closeReadDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public void closeWriteDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // code to add the new Deal
    void add(DealEntry deal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Deal70Db.DealEntryTable.COLUMN_NAME_NAME, deal.getName());
        values.put(Deal70Db.DealEntryTable.COLUMN_NAME_DESCR, deal.getDescr());

        // Inserting Row
        db.insert(Deal70Db.DealEntryTable.TABLE_NAME, null, values);

        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single deal
    DealEntry get(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Deal70Db.DealEntryTable.TABLE_NAME, new String[] {
                        Deal70Db.DealEntryTable._ID,
                        Deal70Db.DealEntryTable.COLUMN_NAME_NAME,
                        Deal70Db.DealEntryTable.COLUMN_NAME_DESCR },
                Deal70Db.DealEntryTable._ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DealEntry deal = new DealEntry();
        deal.setName(cursor.getString(1));
        deal.setDescr(cursor.getString(2));

        // return deal
        return deal;
    }

    // code to get all deals in a list view
    public List<DealEntry> getAll() {
        List<DealEntry> dealList = new ArrayList<DealEntry>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Deal70Db.DealEntryTable.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DealEntry deal = new DealEntry();
                deal.setName(cursor.getString(1));
                deal.setDescr(cursor.getString(2));

                // Adding deal to list
                dealList.add(deal);
            } while (cursor.moveToNext());
        }

        // return deal list
        return dealList;
    }

    // code to update the single deal
    public int updatedeal(DealEntry deal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Deal70Db.DealEntryTable.COLUMN_NAME_NAME, deal.getName());
        values.put(Deal70Db.DealEntryTable.COLUMN_NAME_DESCR, deal.getDescr());

        // updating row
        return db.update(Deal70Db.DealEntryTable.TABLE_NAME, values, Deal70Db.DealEntryTable._ID + " = ?",
                new String[] { String.valueOf(deal.getId()) });
    }

    // Deleting single deal
    public void deletedeal(DealEntry deal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Deal70Db.DealEntryTable.TABLE_NAME, Deal70Db.DealEntryTable._ID + " = ?",
                new String[] { String.valueOf(deal.getId()) });
        db.close();
    }

    // Getting deals Count
    public int getCount() {
        String countQuery = "SELECT  * FROM " + Deal70Db.DealEntryTable.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
