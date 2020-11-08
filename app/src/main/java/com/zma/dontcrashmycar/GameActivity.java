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

import com.zma.dontcrashmycar.game.BackgroundManager;
import com.zma.dontcrashmycar.helpers.ScreenCalculator;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private final String TAG = "GameActivity";

    private BackgroundManager backgroundManager;

    private FrameLayout frameLayout;
    private ImageView playerImage;
    private ArrayList<ImageView> ennemies = new ArrayList<>();

    private int screenWidth;
    private int screenHeight;

    private int spriteWidth;
    private int spriteHeight;

    private final int TIME_BETWEEN_FRAMES = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        frameLayout = findViewById(R.id.frameLayout);
        //init all dimensions used for displaying
        screenWidth = ScreenCalculator.getScreenWidth(this);
        screenHeight = ScreenCalculator.getScreenHeight(this);
        spriteWidth = screenWidth / 6;
        spriteHeight = 2 * spriteWidth;

        Log.d(TAG, "sprite width = " + spriteWidth + " & sprite height = " + spriteHeight);
        Log.d(TAG, "frame width = " + screenWidth + " & frame height = " + screenHeight);

        backgroundManager = new BackgroundManager(this, frameLayout, spriteHeight);
        initSprites();

        new Thread(){
            @Override
            public void run() {
                long timeStart, deltaTime;
                while(true) {
                    timeStart = System.nanoTime();
                    backgroundManager.nextStep();
                    deltaTime = (System.nanoTime() - timeStart) / 1000000;
                    try {
                        if(true){
                            Thread.sleep(TIME_BETWEEN_FRAMES);
                            Log.i(TAG, "sleep for " + (TIME_BETWEEN_FRAMES - deltaTime));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    private void initSprites() {
        //load the player sprite
        playerImage = findViewById(R.id.playerCar);
        playerImage.setImageDrawable(getDrawable(R.drawable.car));
        //we have to set size of sprites depending on the screen size
        playerImage.getLayoutParams().width = spriteWidth;
        playerImage.getLayoutParams().height = spriteHeight;
        //put player at bottom center of the screen
        playerImage.setZ(1);
        playerImage.setY(screenHeight - spriteHeight - backgroundManager.getSquareSizeY());
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

    public int getScreenHeight(){
        return screenHeight;
    }

    public int getScreenWidth(){
        return screenWidth;
    }
}