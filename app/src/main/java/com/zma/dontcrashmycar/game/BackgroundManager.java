package com.zma.dontcrashmycar.game;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zma.dontcrashmycar.GameActivity;
import com.zma.dontcrashmycar.R;

import java.util.ArrayList;

/**
 * Class used to manage all sprites relative to the lines on the road
 */
public class BackgroundManager {
    private final String TAG = "BackgroundManager";

    private GameActivity activity;

    /*Change this to have different number of lines displayed on the road*/
    private final int NUMBER_LINES_ON_ROAD = 7;
    private ImageView squares[] = new ImageView[NUMBER_LINES_ON_ROAD];

    int squareSizeX;
    int squareSizeY;

    /**
     * Scrolling speed in dp / sec. Bigger the number is, faster the scrolling will be
     */
    private final int BACKGROUND_SPEED = 20;

    public BackgroundManager(GameActivity activity, FrameLayout layout){
        this.activity = activity;
        //create all squares needed, and set size and position
        squareSizeX = activity.getScreenWidth() / 7;
        squareSizeY = (2 * activity.getScreenHeight()) / (3 * (NUMBER_LINES_ON_ROAD - 1));
        int halfSizeY = squareSizeY / 2;
        int posX = activity.getScreenWidth() / 2 - squareSizeX / 2;
        int posY = activity.getScreenHeight() - squareSizeY;

        Log.d(TAG, "squareSizeX = " + squareSizeX);
        Log.d(TAG, "squareSizeY = " + squareSizeY);
        for (int i = 0; i < NUMBER_LINES_ON_ROAD; i++){
            ImageView square = new ImageView(activity);
            square.setImageDrawable(activity.getDrawable(R.drawable.white_square));
            //create the layout params before adding it to the image
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(squareSizeX, squareSizeY);
            square.setLayoutParams(layoutParams);
            //positioning depends on the last positioned element
            //(the first line will be stuck at the bottom of the screen  and the last will be above screen)
            //but all will be at the middle of the screen
            square.setX(posX);
            square.setY(posY);
            square.setZ(-1);

            //decrease position by [square height] + [half square height] in order to have space between each other (as in a real road)
            posY -= squareSizeY + halfSizeY;

            //add this view to the layout
            layout.addView(square);
            //and store it in the array for later
            squares[i] = square;
        }

        // TODO : REMOVE IT ! the thread doesn't belongs here (just for testing)
        new Thread(){
            @Override
            public void run() {
                while(true) {
                    nextStep();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    /**
     * Move all the squares down by a specific value {@link BackgroundManager#BACKGROUND_SPEED}. This method is used in the game thread in order to show a movement
     */
    public void nextStep(){
        /**
         * Move all squares to bottom, if one is not visible anymore, put it back on top
         */
        for (ImageView square : squares) {
            square.setY(square.getY() + BACKGROUND_SPEED);
            if(square.getY() > activity.getScreenHeight()){
                square.setY(-squareSizeY);
            }
        }
    }

    public int getSquareSizeX() {
        return squareSizeX;
    }

    public int getSquareSizeY() {
        return squareSizeY;
    }
}
