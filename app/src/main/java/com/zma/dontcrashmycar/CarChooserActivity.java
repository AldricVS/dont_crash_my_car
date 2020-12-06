package com.zma.dontcrashmycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.zma.dontcrashmycar.R.*;

public class CarChooserActivity extends AppCompatActivity {
    GridLayout carLayout;
    List<String> carList;
    ImageButton ImgButton;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_carchooser);
        ImgButton = new ImageButton(this);
        sharedPrefs = getSharedPreferences(getString(string.prefs_car_key), Context.MODE_PRIVATE);
        int carUsingId = sharedPrefs.getInt(String.valueOf(R.integer.carUsingId), 0);
        Log.i("ID car saved", ""+carUsingId);
        if (carUsingId != 0) {
            ImgButton.findViewById(carUsingId);
            ImgButton.setBackgroundColor(Color.GREEN);
        }

    }
/*
    private void showCarList() {
        //get The Layout with all cars in
        carLayout = findViewById(id.carChooser);
    }

    private ImageButton createCarImage(int imageRessource) {
        ImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change la voiture choisi par celle selectionné
                //TODO prérequis de score à avoir
                editor = sharedPrefs.edit();
                editor.putInt(getString(R.string.carUsed), imageRessource);
                editor.apply();
                ImgButton.setBackgroundColor(Color.GREEN);
            }
    }
*/

    public void selectCar(View view) {
        int Id = view.getId();
        Log.i("ID car choosen", ""+Id);
        int oldId = sharedPrefs.getInt(String.valueOf(R.integer.carUsingId), 0);
        Log.i("ID old car", ""+oldId);
        editor = sharedPrefs.edit();
        editor.putInt(String.valueOf(integer.carUsingId), Id);
        editor.apply();
        ImgButton.findViewById(oldId);
        ImgButton.setBackgroundColor(Color.TRANSPARENT);
        ImgButton.findViewById(Id);
        ImgButton.setBackgroundColor(Color.GREEN);
    }

    public void previous(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
