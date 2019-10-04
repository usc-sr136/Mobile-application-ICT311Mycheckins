package com.example.mycheckins;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MyCheckins";

    // Items table name
    private static final String TABLE_PLACES = "places";

    // Items Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PLACE = "place";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_DATE = "date";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_PLACES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_TITLE + " TEXT," + KEY_PLACE + " TEXT,"
                + KEY_DETAILS + " TEXT," + KEY_DATE + " TEXT," + KEY_LOCATION + " TEXT,"
                + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_PLACES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);

        // Create tables again
        onCreate(db);
    }

    // Adding new Item
    public void addItems(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, item._name);
        values.put(KEY_TITLE, item._title);
        values.put(KEY_PLACE, item._place);
        values.put(KEY_DETAILS, item._details);
        values.put(KEY_DATE, item._date);
        values.put(KEY_LOCATION, item._loc);
        values.put(KEY_IMAGE, item._image);

        // Inserting Row
        db.insert(TABLE_PLACES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Item
    Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String[] { KEY_ID,
                        KEY_NAME, KEY_TITLE, KEY_PLACE, KEY_DETAILS, KEY_DATE, KEY_LOCATION, KEY_IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)
                ,cursor.getString(5),cursor.getString(6),cursor.getBlob(7));

        // return Item
        return item;

    }

    // Getting All Items
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM places ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setTitle(cursor.getString(2));
                item.setPlace(cursor.getString(3));
                item.setDetails(cursor.getString(4));
                item.setDate(cursor.getString(5));
                item.setLoc(cursor.getString(6));
                item.setImage(cursor.getBlob(7));
                // Adding Item to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return Item list
        return itemList;

    }

    // Updating single Item
    public int updateContact(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_TITLE, item.getTitle());
        values.put(KEY_PLACE, item.getPlace());
        values.put(KEY_DETAILS, item.getDetails());
        values.put(KEY_DATE, item.getDate());
        values.put(KEY_LOCATION, item.getLoc());
        values.put(KEY_IMAGE, item.getImage());

        // updating row
        return db.update(TABLE_PLACES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getID()) });

    }

    // Deleting single Item
    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLACES, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getID()) });
        db.close();
    }

    // Getting Items Count
    public int getItemsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PLACES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
