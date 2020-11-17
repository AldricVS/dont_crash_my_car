package com.zma.dontcrashmycar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Simply launch the activity GameActivity in order to start a new game.
     */
    public void play(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Go to the CarChooserActivity
     */
    public void chooseCar(View view) {
        Intent intent = new Intent(this, CarChooserActivity.class);
    }

    /**
     * Go to the scores activity (with empty intent, because we don't have registered any new score)
     */
    public void seeScores(View view) {

    }
}