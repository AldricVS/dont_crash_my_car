package com.zma.dontcrashmycar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import static com.zma.dontcrashmycar.R.*;

public class CarChooserActivity extends AppCompatActivity {
    GridLayout carList;
    ImageButton ImgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_carchooser);
        //showCarList();
    }

    private void showCarList() {
        carList = findViewById(id.carChooser);
        for (int i = 0; i < 12; i++) {
            ImgButton = new ImageButton(this);
            ImgButton.setImageResource(drawable.car);
            ImgButton.setTag(i);
            carList.addView(ImgButton);
        }

    }


    public void previous(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
