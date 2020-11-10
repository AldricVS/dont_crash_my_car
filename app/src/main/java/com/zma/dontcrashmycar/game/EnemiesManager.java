package com.zma.dontcrashmycar.game;

import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zma.dontcrashmycar.GameActivity;
import com.zma.dontcrashmycar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manager for all enemies, sprites and behavior.
 */
public class EnemiesManager {
    private final String TAG = "EnemiesManager";

    /**
     * Constants to change to modify the gameplay
     */

    /**
     * The initial speed of each enemy
     */
    private final float ENEMY_SPEED_BASE = 20;

    /**
     *
     * The speed of an enemy will be speed plus a random number between (-{@code ENEMY_SPEED_THRESHOLD}, {@code ENEMY_SPEED_THRESHOLD})
     */
    private final int ENEMY_SPEED_THRESHOLD = 10;

    /**
     * When wanted, enemy speed can be increased by a specific amount
     */
    private final float ENEMY_SPEED_MULTIPLIER = 1.2f;

    private List<Enemy> enemies = new ArrayList<>();
    private float currentEnemySpeed = ENEMY_SPEED_BASE;

    private Random random = new Random();
    private int screenWidth;
    private int screenHeight;
    private int spriteWidth;
    private int spriteHeight;

    /**
     * Initializes the manager and create a specific amount of new enemies (i.e the number of enemies that can be on the screen at the same time)
     * @param activity the activity where enemies will live.
     * @param layout the layout where add sprites.
     * @param numberOfEnemies the number of enemies to create.
     */
    public EnemiesManager(GameActivity activity, FrameLayout layout, int numberOfEnemies){
        screenHeight = activity.getScreenHeight();
        screenWidth = activity.getScreenWidth();
        //init the enemies sprite
        Drawable enemyDrawable = activity.getDrawable(R.drawable.car_reverse);
        spriteWidth = activity.getSpriteWidth();
        spriteHeight = activity.getSpriteHeight();
        for(int i = 0; i < numberOfEnemies; i++){
            ImageView enemyImage = new ImageView(activity);
            enemyImage.setImageDrawable(enemyDrawable);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(spriteWidth, spriteHeight);
            enemyImage.setLayoutParams(layoutParams);

            //create a new instance of enemy
            Enemy enemy = new Enemy(enemyImage, screenWidth, screenHeight);
            setRandomSpeed(enemy);
            //set position to random position above screen, and between screen limits
            resetEnemyPosition(enemy);

            //add to the layout and list
            enemies.add(enemy);
            layout.addView(enemyImage);



        }
    }

    /**
     * Main update call for all enemies.
     */
    public void updateEnemies(){
        //move all enemies
        for (Enemy enemy: enemies) {
            enemy.update();
            //if enemy is at bottom of the screen, put it back to top at random position and speed
            if(enemy.isUnderScreen()){
                resetEnemyPosition(enemy);
            }
        }
    }

    /**
     * Set a random speed for an enemy.
     * This speed is between (ENEMY_SPEED_BASE - ENEMY_SPEED_THRESHOLD) and (ENEMY_SPEED_BASE + ENEMY_SPEED_THRESHOLD)
     * @param enemy the enemy we want to change speed
     */
    private void setRandomSpeed(Enemy enemy){
        //Set a random speed between -ENEMY_SPEED_THRESHOLD and ENEMY_SPEED_THRESHOLD
        float speedModifier = random.nextFloat() * ENEMY_SPEED_THRESHOLD;
        speedModifier *= random.nextBoolean() ? 1 : -1;
        enemy.setSpeed(ENEMY_SPEED_BASE + speedModifier);
    }

    /**
     * Set the position of the enemy above screen. Used when enemy has reached bottom of the screen
     * @param enemy the enemy to ch
     */
    private void resetEnemyPosition(Enemy enemy){
        float positionX = random.nextFloat() * (screenWidth - spriteWidth);
        float positionY = random.nextFloat() * (-screenHeight * 2) - spriteHeight;
        enemy.setPosition(positionX, positionY);
    }



    /**
     * Multiply the global enemy speed by {@link EnemiesManager#ENEMY_SPEED_MULTIPLIER}
     */
    public void increaseEnemySpeed(){
        currentEnemySpeed *= ENEMY_SPEED_MULTIPLIER;
    }

    /**
     * Generate a random number between two limits (inclusive)
     * @param min the lowest value that can be returned
     * @param max the largest value that can be returned
     * @return a float between {@code min} and {@code max}
     */
    private float randomBetween(float min, float max){
        return random.nextFloat();
    }
}
