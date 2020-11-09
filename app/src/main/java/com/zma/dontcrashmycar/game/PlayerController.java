package com.zma.dontcrashmycar.game;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;
import android.widget.ImageView;

import com.zma.dontcrashmycar.GameActivity;
import com.zma.dontcrashmycar.R;

/**
 * Used to control player with the orientation of the phone
 */
public class PlayerController{
    private final String TAG = "PlayerController";

    private GameActivity activity;
    private ImageView playerImage;

    /**
     * All attributes relative to phone orientation management
     */
    private OrientationSensorListener orientationSensorListener = new OrientationSensorListener();
    SensorManager sensorManager;
    Sensor sensorAccelerometer;
    Sensor sensorMagneticField;

    /**
     * Constants used for speed and sensitivity, modify them to change gameplay feelings.
     * <pre>
     *  class created with the help of
     *  https://google-developer-training.github.io/android-developer-advanced-course-practicals/unit-1-expand-the-user-experience/lesson-3-sensors/3-2-p-working-with-sensor-based-orientation/3-2-p-working-with-sensor-based-orientation.html
     * </pre>
     */
    private final int PLAYER_SPEED = 20;

    /**
     * Instantiate the player controller : initiate the player sprite and the sensor
     * @param activity the activity the player controller is related to
     */
    public PlayerController(GameActivity activity){
        this.activity = activity;
        initPlayerSprite();
        initSensor();
    }

    private void initPlayerSprite(){
        //load the player sprite (already added to the view)
        playerImage = activity.findViewById(R.id.playerCar);
        // TODO get the car sprite depending on the shared preferences
        playerImage.setImageDrawable(activity.getDrawable(R.drawable.car));
        //we have to set size of sprites depending on the screen size
        int spriteWidth = activity.getSpriteWidth();
        playerImage.getLayoutParams().width = spriteWidth;
        int spriteHeight = activity.getSpriteHeight();
        playerImage.getLayoutParams().height = spriteHeight;
        //put player at bottom center of the screen
        playerImage.setZ(1);
        playerImage.setY(activity.getScreenHeight() - spriteHeight - activity.getSquareSizeY());
        playerImage.setX(activity.getScreenWidth() / 2 - spriteWidth / 2);
    }

    private void initSensor(){
        /*Sensor type TYPE_ORIENTATION is deprecated, so we have to get those values with accelerometer and magnetic sensor*/
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        enableSensor();
    }

    /**
     * Enables the sensors used to control player. Call this method after disabling them
     */
    public void enableSensor(){
        sensorManager.registerListener(orientationSensorListener, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(orientationSensorListener, sensorMagneticField, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * Disables sensors used to control player. Call this method when activity is paused.
     */
    public void disableSensors(){
        sensorManager.unregisterListener(orientationSensorListener);
    }

    class OrientationSensorListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
