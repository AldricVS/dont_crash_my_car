package com.zma.dontcrashmycar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import static com.zma.dontcrashmycar.R.*;

/**
 * Activity to let the player choose which car he want
 * Based on his maximum Score, he can unlock more car
 * @author Maxence
 */
public class CarChooserActivity extends AppCompatActivity {
    private final static String TAG = "CarChooser";
    public final static String CAR_COLOR_REFERENCE = "carColor";
    public final static String CAR_COLOR_DEFAULT = "red";

    GridLayout carLayout;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_carchooser);

        carLayout = findViewById(id.carChooser);
        sharedPrefs = getSharedPreferences(getString(string.prefs_car_key), Context.MODE_PRIVATE);
        //TODO enpecher la selection base sur le score

        //Now, we show which car the player have selected, base on the value from sharedPrefs
        resetCarBackground();
        String carColor = sharedPrefs.getString(CAR_COLOR_REFERENCE, CAR_COLOR_DEFAULT);
        drawSelectedCar(carColor);

    }

    /**
     * Called when the user press on an ImageButton to choose a new car
     * @param view
     */
    public void selectCar(View view) {
        String imgTag = view.getTag().toString();
        Log.i(TAG, imgTag);
        resetCarBackground();
        drawSelectedCar(imgTag);

        editor = sharedPrefs.edit();
        editor.putString(CAR_COLOR_REFERENCE, imgTag);
        editor.apply();
    }

    /**
     * Set the background to DarkGrey for all element in carLayout
     */
    private void resetCarBackground() {
        View carView;
        for (int i = 0; i<carLayout.getChildCount(); i++) {
            carView = carLayout.getChildAt(i);
            if (carView instanceof ImageButton) {
                carView.setBackgroundColor(Color.DKGRAY);
            }
        }
    }

    /**
     * Set the background to Green for the selected element in carLayout
     * @param selectedCar the car to be shown in Green
     */
    private void drawSelectedCar(String selectedCar) {
        View carView;
        for (int i = 0; i<carLayout.getChildCount(); i++) {
            carView = carLayout.getChildAt(i);
            if (carView instanceof ImageButton) {
                if (carView.getTag().equals(selectedCar)) {
                    carView.setBackgroundColor(Color.GREEN);
                }
            }
        }
    }

    /**
     * return to the MainActivity
     * @param view
     */
    public void previous(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
