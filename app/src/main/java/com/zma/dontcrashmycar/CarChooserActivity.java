package com.zma.dontcrashmycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
        sharedPrefs = getSharedPreferences(getString(string.carUsed), Context.MODE_PRIVATE);

        //createCarList();
        //showCarList();
    }

    private void createCarList() {
        //méthode qui doit servir à récupérer le combo int / images des voitures
        carList = new ArrayList<String>();
        carList.add("default");
        carList.add("default");
        carList.add("default");
        carList.add("default");
        carList.add("default");
        carList.add("default");
        carList.add("default");
        carList.add("default");
        carList.add("default");
    }

    private void showCarList() {
        //get The Layout with all cars in
        carLayout = findViewById(id.carChooser);

        //define number of item on a row
        int nbrCar = carList.size();
        int rowCount = nbrCar / 3;
        carLayout.setColumnCount(3);
        carLayout.setRowCount(rowCount + 1);

        //On traversera la liste d'image de voiture
        for (int i = 0; i < nbrCar; i++) {
            ImgButton = createCarImage(R.drawable.car_default);
            ImgButton.setTag(i);
            carLayout.addView(ImgButton);
        }
        //carLayout.removeViewAt(0);
    }

    private ImageButton createCarImage(int imageRessource) {
        //create the ImageButton
        ImageButton ImgButton = new ImageButton(this);
        //ImgButton.findViewById(id.carButton);
        //Add the Image on the Button
        ImgButton.setImageResource(imageRessource);
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
        );

        return ImgButton;
    }



    public void previous(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
