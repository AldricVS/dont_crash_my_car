package com.zma.dontcrashmycar.game;

import android.util.Log;
import android.widget.ImageView;


public class Enemy {
    private final String TAG = "Enemy";

    private ImageView imageView;
    private float speed;

    private int screenWidth;
    private int screenHeight;

    public Enemy(ImageView imageView, int screenWidth, int screenHeight){
        this.imageView = imageView;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    public void update(){
        imageView.setY(imageView.getY() + speed);
    }

    public float getSpeed() {
        return speed;
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
}
