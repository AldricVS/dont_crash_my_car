package com.zma.dontcrashmycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.zma.dontcrashmycar.scores.DatabaseManager;
import com.zma.dontcrashmycar.scores.PlayerData;

import java.util.ArrayList;
import java.util.List;


public class ScoresTableActivity extends AppCompatActivity {
    SharedPreferences sharedPrefs;
    //DatabaseHandler db = new DatabaseHandler(this);
    DatabaseManager db = new DatabaseManager(this);
    List<PlayerData> scores = new ArrayList();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        //sharedPrefs = getSharedPreferences(getString(R.string.carUsed), Context.MODE_PRIVATE);


        //if database is empty, add 30 random scores from 30 random players
        if(db.isTableEmpty()){
            db.fillTableRandom();
        }
        displayScores("player");
        //createTable();
    }

    /**
     * allows to return in the game activity
     */
    public void returnInGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * allows to return in the main menu activity
     */
    public void returnToMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * register the score of the user, which replace a bot score
     * @param name_user
     * @param
     */
    /*public void RegisterScoreInBD(String name_user, int score){
        Event e = new Event(0,"player",score);
        db.addRow(e);
    }

    /**
     * retrieve all rows in this ArrayList to Display it
     * @param name_user
     */
    public void displayScores(String name_user){
        scores = db.getAllRows();
        scores = db.getAllRows();
        // Create ListView
        ListView listView = (ListView) findViewById(R.id.Scores_Table);
        ArrayAdapter<PlayerData> arrayAdapter = new ArrayAdapter<>(ScoresTableActivity.this, android.R.layout.simple_list_item_1, scores);
        listView.setAdapter(arrayAdapter);
    }
}
