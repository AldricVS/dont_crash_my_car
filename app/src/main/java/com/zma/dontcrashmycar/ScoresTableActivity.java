package com.zma.dontcrashmycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.zma.dontcrashmycar.scores.DatabaseCreate;
import com.zma.dontcrashmycar.scores.DatabaseHandler;
import com.zma.dontcrashmycar.scores.Event;

import java.util.ArrayList;
import java.util.List;


public class ScoresTableActivity extends AppCompatActivity {
    SharedPreferences sharedPrefs;
    //DatabaseHandler db = new DatabaseHandler(this);
    DatabaseCreate db = new DatabaseCreate(this);
    List<String> scores = new ArrayList();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        //sharedPrefs = getSharedPreferences(getString(R.string.carUsed), Context.MODE_PRIVATE);
        DisplayScores("player");
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
    public void returnInMain(View view) {
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
    public void DisplayScores(String name_user){
        //scores = db.getAllRows();
        scores = db.getAllRows();
        // Create ListView
        //ListView listView = findViewById(R.id.Scores_Table);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ScoresTableActivity.this, android.R.layout.activity_list_item, scores);
        //listView.setAdapter(arrayAdapter);
    }
}
