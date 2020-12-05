package com.zma.dontcrashmycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class ScoresTableActivity extends AppCompatActivity {
    SharedPreferences sharedPrefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        sharedPrefs = getSharedPreferences(getString(R.string.carUsed), Context.MODE_PRIVATE);

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
     * allows to return in the choosecar activity
     */
    public void returnInChoose(View view) {
        Intent intent = new Intent(this, CarChooserActivity.class);
        startActivity(intent);
        finish();
    }
}
