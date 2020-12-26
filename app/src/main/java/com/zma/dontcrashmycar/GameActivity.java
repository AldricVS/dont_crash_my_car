package com.zma.dontcrashmycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zma.dontcrashmycar.game.BackgroundManager;
import com.zma.dontcrashmycar.game.EnemiesManager;
import com.zma.dontcrashmycar.game.PlayerController;
import com.zma.dontcrashmycar.game.Score;
import com.zma.dontcrashmycar.helpers.ScreenCalculator;


public class GameActivity extends AppCompatActivity {
    private final String TAG = "GameActivity";

    private BackgroundManager backgroundManager;
    private PlayerController playerController;
    private Score score;
    private EnemiesManager enemiesManager;

    private RelativeLayout relativeLayout;
    private LinearLayout uiLayout;
    private LinearLayout pauseLayout;
    private TextView scoreTextView;

    private int screenWidth;
    private int screenHeight;

    private int spriteWidth;
    private int spriteHeight;
    
    private int hitboxWidth;
    private int hitboxHeight;

    private GameThread gameThread;
    private boolean isPlaying = false;

    private final int TIME_BETWEEN_FRAMES = 16;

    public static final String SCORE_INTENT_EXTRA = "Score";


    /*APP LIFECYCLE*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        relativeLayout = (RelativeLayout)findViewById(R.id.frameLayout);
        uiLayout = (LinearLayout)findViewById(R.id.ui_layout);
        pauseLayout = (LinearLayout)findViewById(R.id.pause_layout);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        //we have to keep user from changing phone orientation (only portrait mode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //init all dimensions used for displaying
        screenWidth = ScreenCalculator.getScreenWidth(this);
        screenHeight = ScreenCalculator.getScreenHeight(this);
        spriteWidth = screenWidth / 6;
        spriteHeight = 5 * spriteWidth / 3;

        backgroundManager = new BackgroundManager(this, relativeLayout, spriteHeight);
        playerController = new PlayerController(this);
        score = new Score();
        enemiesManager = new EnemiesManager(this, relativeLayout);

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
                uiLayout.setVisibility(View.VISIBLE);
                pauseLayout.setVisibility(View.INVISIBLE);
                playerController.enableSensors();
                gameThread = new GameThread();
                gameThread.start();
            }else{
                uiLayout.setVisibility(View.INVISIBLE);
                pauseLayout.setVisibility(View.VISIBLE);
                playerController.disableSensors();
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
        startActivity(intent);
        finish();
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
            //we have to stop the game (i.e the game thread)
            isPlaying = false;

            //launch score activity (with score in extras) and destroy the game activity
            Intent intent = new Intent(GameActivity.this, ScoresTableActivity.class);
            intent.putExtra(SCORE_INTENT_EXTRA, score.getScore());
            finish();
            startActivity(intent);
        }
    }

    private void updateScore() {
        //increase score, it it returns true, that means that we have to increase enemies speed
        if(score.addScore()){
            enemiesManager.increaseEnemySpeed();
        }
        //update the score text label
        //we can't do this on another thread, we have to do this on the ui thread
        runOnUiThread(() -> scoreTextView.setText(Integer.toString(score.getScore())));
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

                /*logic and rendering*/
                backgroundManager.nextStep();
                playerController.updatePlayerMovement();
                enemiesManager.updateEnemies();
                checkForCollision();
                updateScore();


                //end of rendering : we check the time elapsed during this frame
                deltaTime = (System.nanoTime() - timeStart) / 1000000;
                try {
                    if (deltaTime < TIME_BETWEEN_FRAMES) {
                        Thread.sleep(TIME_BETWEEN_FRAMES - deltaTime);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}