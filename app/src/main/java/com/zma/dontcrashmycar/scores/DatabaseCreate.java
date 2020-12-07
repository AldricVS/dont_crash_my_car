package com.zma.dontcrashmycar.scores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseCreate extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "scoreManager";

    // Table name
    private static final String TABLE_EVENTS = "scores";

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

    public DatabaseCreate(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE);
        Insertion();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void Insertion(){
        SQLiteDatabase sql = getWritableDatabase();
        ContentValues cv=new ContentValues();
        int score_bot;
        Random rand = new Random();
        for (int i=0;i<30;i++) {
            score_bot = rand.nextInt(100000-50);
            cv.put(KEY_ID, i);
            cv.put(KEY_NAME_USER, "player");
            cv.put(KEY_SCORE, score_bot);
            sql.insert(TABLE_EVENTS, KEY_ID, cv);
        }
        System.out.println("les données sont dans la table");
    }

    public List<String> getAllRows() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorResult = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);

        if(cursorResult!=null){
            //check if have any result
            if (cursorResult.moveToFirst()) {
                //go through all rows and extract event from it
                do {
                    list.add(String.valueOf(cursorResult.getInt(0)));
                    list.add(cursorResult.getString(1));
                    list.add(String.valueOf(cursorResult.getInt(2)));
                } while (cursorResult.moveToNext());
            }
        }
        cursorResult.close();
        return list;
    }
}
