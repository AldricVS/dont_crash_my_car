package com.zma.dontcrashmycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import static com.zma.dontcrashmycar.R.*;

public class CarChooserActivity extends AppCompatActivity {
    GridLayout carList;
    ImageButton ImgButton;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_carchooser);
        sharedPrefs = getSharedPreferences(getString(string.carUsed), Context.MODE_PRIVATE);

        //createCarList();
        //showCarList();
    }

    private void createCarList() {
        //méthode qui doit servir à récupérer le combo int / images des voitures
    }

    private void showCarList() {
        carList = findViewById(id.carChooser);
        carList.setColumnCount(3);
        carList.setRowCount(5);
        //On traversera la liste d'image de voiture
        for (int i = 0; i < 12; i++) {
            ImgButton = createCarImage(drawable.car_default);
            ImgButton.setTag(i);
            carList.addView(ImgButton);
        }
    }

    private ImageButton createCarImage(int img) {
        ImageButton ImgButton = new ImageButton(this);
        //ImgButton.findViewById(id.carButton);

        ImgButton.setImageResource(img);
        ImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change la voiture choisi par celle selectionné
                //TODO prérequis de score à avoir
                editor = sharedPrefs.edit();
                editor.putInt(getString(R.string.carUsed), img);
                editor.apply();
            }
        }
        );

        return ImgButton;
    }



    public void previous(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
