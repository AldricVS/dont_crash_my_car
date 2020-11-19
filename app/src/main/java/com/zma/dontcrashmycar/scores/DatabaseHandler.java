package com.zma.dontcrashmycar.scores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final String TAG = "DatabaseHandler";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "eventManager";

    // Table name
    private static final String TABLE_EVENTS = "utilisateur";

    // Table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME_USER = "name_user";
    private static final String KEY_SCORE = "score";

    //query for creating table
    private final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EVENTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NAME_USER + " TEXT," +
            KEY_SCORE + " INTEGER)";

    private final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_EVENTS;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Create table event");
        db.execSQL(QUERY_CREATE_TABLE);
    }

    // Upgrade the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrade");

        // Drop older table if existed
        db.execSQL(QUERY_DROP_TABLE);

        // Create tables again
        onCreate(db);
    }

    // Add a new row
    void addRow(Event e) {
        SQLiteDatabase db = getWritableDatabase();

        //put values in ContentValues before sending it to the database (ID will be auto-increment)
        ContentValues values = new ContentValues();
        values.put(KEY_ID, e.getID());
        values.put(KEY_NAME_USER, e.getNameUser());
        values.put(KEY_SCORE, e.getScore());

        db.insert(TABLE_EVENTS, null, values);
    }

    // Get all rows
    public List<Event> getAllRows() {
        List<Event> eventList = new ArrayList<>();

        ////////////////////////////////////////////////////////////////////////////////////////////
        // TODO                                                                                   //
        // - Use the select all query                                                             //
        // - Loop through all the rows and add the to a list                                      //
        // - Return the list                                                                      //
        ////////////////////////////////////////////////////////////////////////////////////////////
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorResult = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);

        Log.d(TAG, "Number of rows in database : " + cursorResult.getCount());

        //check if have any result
        if(cursorResult.moveToFirst()){
            //go through all rows and extract event from it
            while(!cursorResult.isAfterLast()) {
                Event event = new Event();

                event.setID(cursorResult.getInt(cursorResult.getColumnIndex(KEY_ID)));
                event.setNameUser(cursorResult.getString(cursorResult.getColumnIndex(KEY_NAME_USER)));
                event.setScore(cursorResult.getInt(cursorResult.getColumnIndex(KEY_SCORE)));

                eventList.add(event);
                cursorResult.moveToNext();
            }
        }
        cursorResult.close();
        Log.d(TAG, "Send eventList");
        return eventList;
    }

    // Clear the table
    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EVENTS);
        db.close();
    }

    //create the table if not exists
    public void createTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(QUERY_CREATE_TABLE);
    }

    // insert a score of user in the Data Base
    public void InsertScore(String name_user, int score){
        int id = GetIdMax();
        id+=1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(String.format("INSERT INTO (id, name_user, score) VALUES %d,%s,%d", id, name_user, score));
        db.close();
    }

    // get the max id while to increment this
    public int GetIdMax(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorResult = db.rawQuery("SELECT MAX(id) FROM utilisateur", null);
        int id_max = cursorResult.getInt(cursorResult.getColumnIndex(KEY_ID));
        db.close();
        return id_max;
    }

    // return the best score of unique user
    public int GetBestScore(String name_user){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorResult = db.rawQuery("SELECT MAX(score) FROM utilisateur WHERE name_user = " + name_user, null);
        int max_score = cursorResult.getInt(cursorResult.getColumnIndex(KEY_SCORE));
        db.close();
        return max_score;
    }

}
