package com.zma.dontcrashmycar.scores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseManager";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "scoreManager";

    // Table name
    private static final String TABLE_NAME = "scores";

    // Table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME_USER = "name_user";
    private static final String KEY_SCORE = "score";

    //query for creating table
    private final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME_USER + " TEXT," +
            KEY_SCORE + " INTEGER)";

    private final String QUERY_SELECT_SCORES = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_SCORE + " DESC";

    private final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean isTableEmpty() {
        return getNumberRows() == 0;
    }

    public long getNumberRows(){
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    public void fillTableRandom() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        int scoreBot;
        Random rand = new Random();
        for (int i = 0; i < 30; i++) {
            scoreBot = rand.nextInt(100000 - 50);
            Log.i(TAG, "Insert score " + scoreBot + " for Player" + i);
            cv.put(KEY_NAME_USER, "Player" + i);
            cv.put(KEY_SCORE, scoreBot);
            db.insert(TABLE_NAME, null, cv);
        }
        Log.i(TAG, "Database fully filled");
    }

    public List<PlayerData> getAllRows() {
        List<PlayerData> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorResult = db.rawQuery(QUERY_SELECT_SCORES, null);

        if (cursorResult != null) {
            //check if have any result
            if (cursorResult.moveToFirst()) {
                //go through all rows and extract event from it
                do {
                    PlayerData playerData = new PlayerData(
                            cursorResult.getInt(0),
                            cursorResult.getString(1),
                            cursorResult.getInt(2)
                    );
                    list.add(playerData);
                } while (cursorResult.moveToNext());
            }
        }
        cursorResult.close();
        return list;
    }
}
