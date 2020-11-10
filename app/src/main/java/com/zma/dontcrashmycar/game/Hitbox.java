package com.zma.dontcrashmycar.game;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Class used to check collision zones with images views. The hitbox is basically a rectangle contained in a view
 */
public class Hitbox {

    private float positionX;
    private float positionY;
    private float width;
    private float height;

    /**
     * Initialises a new hitbox for
     * @param view the view to create an hitbox for
     * @param hitboxRatio the percentage of covering for the hitbox (1.0 = full size, 0.0 = zero size)
     * @throws IllegalArgumentException if hitbox percentage is not between 0.0 and 1.0
     */
    public Hitbox(View view, float hitboxRatio) throws IllegalArgumentException{
        //check for right ratio value
        if(hitboxRatio > 1f || hitboxRatio < 0f){
            throw new IllegalArgumentException("Ratio must be between 0 and 1. Actual : " + hitboxRatio);
        }

        //get the image view size and position in order to create the hitbox
        float viewPositionX = view.getX();
        float viewPositionY = view.getY();
        float viewWidth = view.getLayoutParams().width;
        float viewHeight = view.getLayoutParams().width;

        //width and height are simple
        width = viewWidth * hitboxRatio;
        height = viewHeight * hitboxRatio;

        //now, we have to find the position in order to totally describe the hitbox rectangle
        //get the center of the view
        float viewCenterX = viewPositionX + viewWidth / 2;
        float viewCenterY = viewPositionY + viewHeight / 2;
        //shift this point width/2 to the left and height/2 up to get hitbox rect position
        positionX = viewCenterX - width;
        positionY = viewCenterY - height;

        Log.d("Hitbox", "positionX : " + positionY + " positionY : " + positionY + " width: " + width + " height : " + height);
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    /**
     * Move the hitbox in x-coordinate by a specific amount of pixels
     * @param value the number of pixels that we want to shift the hitbox (negative = left, positive = right)
     */
    public void moveX(float value){
        positionX += value;
    }

    /**
     * Move the hitbox in y-coordinate by a specific amount of pixels
     * @param value the number of pixels that we want to shift the hitbox (negative = left, positive = right)
     */
    public void moveY(float value){
        positionY += value;
    }

    /**
     * Check if an hitbox collides with another one
     * @param other the other hitbox to check
     * @return {@code true} if they are in collision, {@code false} else
     */
    public boolean isColliding(Hitbox other){
        float otherPositionX = other.getPositionX();
        float otherPositionY = other.getPositionY();

        return this.positionX < otherPositionX + other.getWidth() // left rect 1 < right rect 2
                && this.positionX + width > otherPositionX // right rect 1 > left rect 2
                && this.positionY < otherPositionY + other.getHeight() // top rect 1 < bottom rect 2
                && this.positionY + height > otherPositionY; // bottom rect 1 > top rect 2
    }

    @Override
    public String toString() {
        return "Hitbox{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
