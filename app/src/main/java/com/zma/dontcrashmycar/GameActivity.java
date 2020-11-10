package com.zma.dontcrashmycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zma.dontcrashmycar.game.BackgroundManager;
import com.zma.dontcrashmycar.game.EnemiesManager;
import com.zma.dontcrashmycar.game.Hitbox;
import com.zma.dontcrashmycar.game.PlayerController;
import com.zma.dontcrashmycar.helpers.ScreenCalculator;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private final String TAG = "GameActivity";

    private BackgroundManager backgroundManager;
    private PlayerController playerController;
    private EnemiesManager enemiesManager;

    private FrameLayout frameLayout;
    private ArrayList<ImageView> ennemies = new ArrayList<>();

    private int screenWidth;
    private int screenHeight;

    private int spriteWidth;
    private int spriteHeight;
    
    private int hitboxWidth;
    private int hitboxHeight;

    private GameThread gameThread;

    private final int TIME_BETWEEN_FRAMES = 16;

    /**
     * This number must be less than screenWidth / spriteWidth, or else enemies can't behavior properly
     */
    private final int NUMBER_OF_ENEMIES = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //we have to keep user from changing phone orientation (only portrait mode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        frameLayout = findViewById(R.id.frameLayout);
        //init all dimensions used for displaying
        screenWidth = ScreenCalculator.getScreenWidth(this);
        screenHeight = ScreenCalculator.getScreenHeight(this);
        spriteWidth = screenWidth / 6;
        spriteHeight = 2 * spriteWidth;

        Log.d(TAG, "sprite width = " + spriteWidth + " & sprite height = " + spriteHeight);
        Log.d(TAG, "frame width = " + screenWidth + " & frame height = " + screenHeight);

        backgroundManager = new BackgroundManager(this, frameLayout, spriteHeight);
        playerController = new PlayerController(this);
        enemiesManager = new EnemiesManager(this, frameLayout, NUMBER_OF_ENEMIES);

        gameThread = new GameThread();
        gameThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerController.disableSensors();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerController.enableSensors();
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerController.disableSensors();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerController.disableSensors();
    }

    /**
     * create an enemy sprite and add him to the list
     */
    private void createEnemySprite() {
        ImageView enemySprite = new ImageView(getApplicationContext());

        ennemies.add(enemySprite);
    }

    /**
     * Tries to detect any collision between the player and enemies.
     */
    private void checkForCollision(){
        Hitbox playerHitbox = playerController.getHitbox();
        if(enemiesManager.detectCollision(playerHitbox)){
            //TODO collision happened, end the game and go to scores
            Log.i(TAG, "Player has collided with an enemy");
        }
    }

    /**
     * We don't want that the user could return to the previous screen easily
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public int getSquareSizeY(){
        return backgroundManager.getSquareSizeY();
    }

    class GameThread extends Thread {
        @Override
        public void run() {
            /*
             * We will keep track of the elapsed time to do the logic and render a frame.
             * Instead of only sleep for fixed amount of time, we will wait depending on the time needed to render the previous frame
             */
            long timeStart, deltaTime;
            while (true) {
                //get the time at the beginning of the rendering
                timeStart = System.nanoTime();

                //logic and rendering
                backgroundManager.nextStep();
                playerController.updatePlayerMovement();
                enemiesManager.updateEnemies();
                checkForCollision();

                //end of rendering : we check the time elapsed during this frame
                deltaTime = (System.nanoTime() - timeStart) / 1000000;
                try {
                    if (deltaTime < TIME_BETWEEN_FRAMES) {
                        Thread.sleep(TIME_BETWEEN_FRAMES - deltaTime);
                        //Log.d(TAG, "Sleep for " + (TIME_BETWEEN_FRAMES - deltaTime) + "ms");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}