package com.zma.dontcrashmycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zma.dontcrashmycar.helpers.ScreenCalculator;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private final String TAG = "GameActivity";

    private FrameLayout frameLayout;
    private ImageView playerImage;
    private ArrayList<ImageView> ennemies = new ArrayList<>();

    private int screenWidth;
    private int screenHeight;

    private int spriteWidth;
    private int spriteHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //init all dimensions used for displaying
        screenWidth = ScreenCalculator.getScreenWidth(this);
        screenHeight = ScreenCalculator.getScreenHeight(this);
        spriteWidth = screenWidth / 6;
        spriteHeight = 2 * spriteWidth;

        Log.d(TAG, "sprite width = " + spriteWidth + " & sprite height = " + spriteHeight);
        Log.d(TAG, "frame width = " + screenWidth + " & frame height = " + screenHeight);
        initSprites();
    }

    private void initSprites() {
        //load the player sprite
        playerImage = findViewById(R.id.playerCar);
        playerImage.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.car)));
        //we have to set size of sprites depending on the screen size
        playerImage.getLayoutParams().width = spriteWidth;
        playerImage.getLayoutParams().height = spriteHeight;
        //put player at bottom center of the screen
        playerImage.setY(screenHeight - 2 * spriteHeight / 3);
        playerImage.setX(screenWidth / 2 - spriteWidth / 2);
    }

    /**
     * create an enemy sprite and add him to the list
     */
    private void createEnemySprite(){
        ImageView enemySprite = new ImageView(getApplicationContext());
        ennemies.add(enemySprite);
    }

    /**
     * We don't want that the user could return to the previous screen easily
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}