package com.zma.dontcrashmycar.game;

import android.util.Log;
import android.widget.ImageView;


public class Enemy {
    private final String TAG = "Enemy";

    /**
     * Hitbox ratio of the enemy (depends on the size of the enemy's sprite)
     * ONLY BETWEEN 0f and 1f.
     */
    private final float HITBOX_RATIO = 0.95f;

    private ImageView imageView;
    private Hitbox hitbox;
    private float speed;

    private int screenWidth;
    private int screenHeight;

    public Enemy(ImageView imageView, int screenWidth, int screenHeight){
        this.imageView = imageView;
        hitbox = new Hitbox(imageView, HITBOX_RATIO);
        Log.d(TAG, hitbox.toString());
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    public void update(){
        imageView.setY(imageView.getY() + speed);
        //we also want to move the hitbox of the enemy
        hitbox.moveY(speed);
    }

    public float getSpeed() {
        return speed;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }


    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setPosition(float positionX, float positionY){
        imageView.setX(positionX);
        imageView.setY(positionY);
    }

    public float getPositionX(){
        return imageView.getX();
    }

    public float getPositionY(){
        return imageView.getY();
    }

    /**
     * Check if enemy is under the screen.
     */
    public boolean isUnderScreen(){
        return imageView.getY() > screenHeight;
    }

    /**
     * move the hitbox of the enemy depending on his new position
     */
    public void resetHitboxPosition() {
        float centerX = imageView.getX() + imageView.getLayoutParams().width / 2;
        float centerY = imageView.getY() + imageView.getLayoutParams().height / 2;

        hitbox.setPositionX(centerX - hitbox.getWidth() / 2);
        hitbox.setPositionY(centerY - hitbox.getHeight() / 2);
    }
}
