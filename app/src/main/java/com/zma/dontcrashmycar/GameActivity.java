package com.zma.dontcrashmycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zma.dontcrashmycar.game.BackgroundManager;
import com.zma.dontcrashmycar.game.EnemiesManager;
import com.zma.dontcrashmycar.game.PlayerController;
import com.zma.dontcrashmycar.helpers.ScreenCalculator;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private final String TAG = "GameActivity";

    private BackgroundManager backgroundManager;
    private PlayerController playerController;
    private EnemiesManager enemiesManager;

    private FrameLayout frameLayout;
    private LinearLayout pauseLayout;

    private int screenWidth;
    private int screenHeight;

    private int spriteWidth;
    private int spriteHeight;
    
    private int hitboxWidth;
    private int hitboxHeight;

    private GameThread gameThread;
    private boolean isPlaying = false;

    private final int TIME_BETWEEN_FRAMES = 16;

    /*
     * This number must be less than screenWidth / spriteWidth, or else enemies can't behavior properly
     */
    private final int NUMBER_OF_ENEMIES = 4;


    /*APP LIFECYCLE*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        frameLayout = findViewById(R.id.frameLayout);
        pauseLayout = findViewById(R.id.pause_layout);

        //we have to keep user from changing phone orientation (only portrait mode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //init all dimensions used for displaying
        screenWidth = ScreenCalculator.getScreenWidth(this);
        screenHeight = ScreenCalculator.getScreenHeight(this);
        spriteWidth = screenWidth / 6;
        spriteHeight = 5 * spriteWidth / 3;

        Log.d(TAG, "sprite width = " + spriteWidth + " & sprite height = " + spriteHeight);
        Log.d(TAG, "frame width = " + screenWidth + " & frame height = " + screenHeight);

        backgroundManager = new BackgroundManager(this, frameLayout, spriteHeight);
        playerController = new PlayerController(this);
        enemiesManager = new EnemiesManager(this, frameLayout, NUMBER_OF_ENEMIES);

        //hide and disable the pause layout, and start the game thread
        setGameActive(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setGameActive(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //A to this method when the game is already not active will only cost one boolean verification
        setGameActive(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setGameActive(false);
    }

    /**
     * We don't want that the user could return to the previous screen easily
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    /**
     * Set all behavior relative to the game pause and play.
     * It will manage game thread state and pause layout visibility.
     * If game is already in the right state, nothing will happen
     * @param active if the game has to be active or not
     */
    private void setGameActive(boolean active){
        //check if the pause state is not already set
        if(active != isPlaying){
            //if active == false, this will also stop the current thread
            isPlaying = active;
            if(active){
                pauseLayout.setVisibility(View.INVISIBLE);
                playerController.enableSensors();
                gameThread = new GameThread();
                gameThread.start();
                Log.d(TAG, "Game is now active");
            }else{
                pauseLayout.setVisibility(View.VISIBLE);
                playerController.disableSensors();
                Log.d(TAG, "Game is now paused");
            }
        }
    }

    /*LAYOUT METHODS*/



    /*LISTENERS*/

    /**
     * Suspend the game thread and display the pause screen.
     */
    public void pauseGame(View view){
        setGameActive(false);
    }


    public void resumeGame(View view) {
        setGameActive(true);
    }


    public void quitGame(View view) {
        setGameActive(false);
        //return to menu, without forgetting to kill this game activity
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }


    /*GETTERS AND SETTERS*/
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

    public boolean isPlaying() {
        return isPlaying;
    }

    /*GAME RELATIVE METHODS*/

    /**
     * Tries to detect any collision between the player and enemies.
     * If it happens, go to the score activity creating intent
     */
    private void checkForCollision(){
        if(enemiesManager.isThereCollision(playerController)){
            //TODO collision happened, end the game and go to scores
            Log.i(TAG, "Player has collided with an enemy");
            //we have to stop the game (i.e the game thread)
            isPlaying = false;

            ///TODO : this is for testing purposes, we have to do as the javadoc above says instead
            //launch main menu activity and destroy the game activity
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }


    class GameThread extends Thread {
        @Override
        public void run() {
            /*
             * We will keep track of the elapsed time to do the logic and render a frame.
             * Instead of only sleep for fixed amount of time, we will wait depending on the time needed to render the previous frame
             */
            long timeStart, deltaTime;
            while (isPlaying) {
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