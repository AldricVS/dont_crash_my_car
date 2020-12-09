package com.zma.dontcrashmycar.scores;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.zma.dontcrashmycar.MainActivity;
import com.zma.dontcrashmycar.R;
import com.zma.dontcrashmycar.ScoresTableActivity;

import java.io.File;


/**
 * @author Zacharie
 */
public class SaveScoreService extends Service {
    private static final String TAG = "scoreTableActivity";
    private SharedPreferences sharedPrefs;
    private DatabaseManager db = new DatabaseManager(this);
    private int bestScore = 0;

    /**
     *
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        /*
        // Get the information from the intent
        if(intent != null) {
            sharedPrefs = getSharedPreferences(getString(R.string.carUsed), Context.MODE_PRIVATE);
            String playerName = intent.getStringExtra(MainActivity.playerName);
            int doneScore = intent.getIntExtra(MainActivity.doneScore);


            this.bestScore = sharedPrefs.getInt("bestScore", 1);

            //if the player done a new best score
            if (this.bestScore < doneScore) {
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("player name", playerName);
                editor.putInt("best score", doneScore);
                editor.commit();
            }

            //if database is empty, add 30 random scores from 30 random players
            if (db.isTableEmpty()) {
                db.fillTableRandom();
            }
            //else we add the current score if this is not the lowest score compared to
            else {
                db.fillTableOneCarRacing(playerName, doneScore);
            }

            if (new File(ScoresTableActivity.EVENTS_FILE_PATH).exists()) {
                Log.d(TAG, "Done!");
                Intent scoreIntent = new Intent(ScoresTableActivity.INTENT_FILTER);
                LocalBroadcastManager.getInstance(this).sendBroadcast(scoreIntent);
            }
        }*/
        return null;
    }
}
