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

/**
 * @author Zacharie
 */
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

    //private final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


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


    /**
     * test if the table is empty
     * @return boolean : true if the table is empty, false else
     */
    public boolean isTableEmpty() {
        return getNumberRows() == 0;
    }

    /**
     * look how many rows the table "scores" get
     * @return long : number of row in the table
     */
    public long getNumberRows(){
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    /**
     * initializes the data table with random scores assigned to bot
     */
    public void fillTableRandom() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        int scoreBot;
        Random rand = new Random();
        for (int i = 0; i < 30; i++) {
            scoreBot = rand.nextInt(20000 - 50);
            Log.i(TAG, "Insert score " + scoreBot + " for Player" + i);
            cv.put(KEY_NAME_USER, "Player" + i);
            cv.put(KEY_SCORE, scoreBot);
            db.insert(TABLE_NAME, null, cv);
        }
        Log.i(TAG, "Database fully filled");
        db.close();
    }

    /**
     * get the id of the lowest score in the table "scores"
     * @return int : the id of the lowest score
     */
    public int retrieveIdLowestScore(){
        SQLiteDatabase db = getWritableDatabase();
        String queryIdLowestScore = "SELECT " + KEY_ID + " FROM " + TABLE_NAME + " GROUP BY " + KEY_SCORE +" LIMIT 1";
        Cursor cursorResult = db.rawQuery(queryIdLowestScore, null);
        if (cursorResult != null)
            cursorResult.moveToFirst();

        int IdLowestScore = cursorResult.getInt(0);
        db.close();
        return IdLowestScore;
    }

    /**
     * replace the lowest score by the score of our player if it is higher than the minimum score
     */
    public void fillTableOneCarRacing(String name, int score) {
        int idLowestScore = retrieveIdLowestScore();
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + KEY_NAME_USER + "=" + name + " WHERE " + KEY_ID + "=" + idLowestScore);
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + KEY_SCORE + "=" + score + " WHERE " + KEY_ID + "=" + idLowestScore);
        db.close();
    }

    /**
     * get all scores in the table "scoreManager".
     * @return list : a list of all scores to the table
     */
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

    /**
     * retrieve the best score of player in playing
     * @param name
     * @return int : best score of player in playing
     */
    public int getBestScorePlayer(String name){
        SQLiteDatabase db = getWritableDatabase();
        String queryBestScorePlayer = "SELECT " + KEY_SCORE + " FROM " + TABLE_NAME + " WHERE " + KEY_NAME_USER + "=" + name;
        Cursor cursorResult = db.rawQuery(queryBestScorePlayer, null);
        if (cursorResult != null)
            cursorResult.moveToFirst();

        int bestScorePlayer = cursorResult.getInt(0);
        db.close();
        return bestScorePlayer;
    }

}
