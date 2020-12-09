package com.zma.dontcrashmycar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.zma.dontcrashmycar.scores.DatabaseManager;
import com.zma.dontcrashmycar.scores.PlayerData;
import com.zma.dontcrashmycar.scores.SaveScoreService;

import java.util.ArrayList;
import java.util.List;

/**
 * class allowing to display the score in the "activity_scores.xml"
 * @author Zacharie
 */
public class ScoresTableActivity extends AppCompatActivity {
    private static final String TAG = "scoreTableActivity";
    private SharedPreferences sharedPrefs;
    private DatabaseManager db = new DatabaseManager(this);
    private List<PlayerData> scores = new ArrayList();
    public static final String INTENT_FILTER = "filter";
    public static String EVENTS_FILE_PATH = "";
    public static final String NAME_INTENT_EXTRA = "G";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        Intent intent = getIntent();
        //retrieves the preferences which are about of player playing and his best score
        String playerName = "Gauthier";


        //if database is empty, add 30 random scores from 30 random players
        if (db.isTableEmpty()) {
            db.fillTableRandom();
        }
        //else we add the current score if this is not the lowest score compared to
        int doneScore = intent.getIntExtra(GameActivity.SCORE_INTENT_EXTRA,-1);
        if(doneScore>=0) {
            processPlayerScore(doneScore);
        }
        //displayScores();
    }

    /**
     * allows to return in the game activity
     */
    public void returnInGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register the BroadcastReceiver
        LocalBroadcastManager.getInstance(this).registerReceiver(scoreReceiver,
                new IntentFilter(INTENT_FILTER));

    }

    @Override
    public void onPause() {
        super.onPause();

        // Unregister the BroadcastReceiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(scoreReceiver);

    }

    private BroadcastReceiver scoreReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Event received...");
            displayScores();

        }

    };

    /**
     * allows to return in the main menu activity
     */
    public void returnToMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * allows to start the service
     */
    public void processPlayerScore(int score){
        Log.d(TAG, "Save score service");
        Intent intent = new Intent(this, SaveScoreService.class);
        intent.putExtra(GameActivity.SCORE_INTENT_EXTRA,score);
        startService(intent);
    }
    /**
     * retrieve all scores in a list
     */
    public void displayScores(){
        scores = db.getAllRows();
        // Create ListView
        ListView listView = (ListView) findViewById(R.id.Scores_Table);
        ArrayAdapter<PlayerData> arrayAdapter = new ArrayAdapter<>(ScoresTableActivity.this, android.R.layout.simple_list_item_1, scores);
        listView.setAdapter(arrayAdapter);
    }
}
