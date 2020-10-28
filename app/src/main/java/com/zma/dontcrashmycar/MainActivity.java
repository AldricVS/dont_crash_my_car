package com.zma.dontcrashmycar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Test", "je préferais Pro Evolution Car");
        Log.i("new test", "ça marche ou ça marche pas...");
        Log.i("Test", "BILLY !");
    }
}