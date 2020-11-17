package com.zma.dontcrashmycar;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import static com.zma.dontcrashmycar.R.*;

public class CarChooserActivity extends AppCompatActivity {
    LinearLayout carList, carSubList;
    ImageButton ImgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_carchooser);
        showCarList();
    }

    private void showCarList() {
        carList = findViewById(id.carChooser);
        for (int i = 0; i < 12; i++) {
            if (i % 3 == 0) {
                carSubList = new LinearLayout(this);
            }
            ImgButton = new ImageButton(this);
            ImgButton.setImageResource(drawable.car);
            ImgButton.setTag(i);
            carSubList.addView(ImgButton);
            if (i % 3 == 2) {
                carList.addView(carSubList);
                carSubList = null;
            }
        }

    }
}
