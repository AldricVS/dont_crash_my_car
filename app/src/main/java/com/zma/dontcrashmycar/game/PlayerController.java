package com.zma.dontcrashmycar.game;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.ImageView;

import com.zma.dontcrashmycar.GameActivity;
import com.zma.dontcrashmycar.R;

/**
 * Used to control player with the orientation of the phone
 * <pre>
 *   Class created with the help of
 *   https://google-developer-training.github.io/android-developer-advanced-course-practicals/unit-1-expand-the-user-experience/lesson-3-sensors/3-2-p-working-with-sensor-based-orientation/3-2-p-working-with-sensor-based-orientation.html
 * </pre>
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
    float accelerometerValues[] = new float[3];
    Sensor sensorMagneticField;
    float magneticValues[] = new float[3];
    float rotationMatrix[] = new float[9];

    //we only care about roll, since we could only move the car from left to right
    float rollOrientation = 0f;

    /**
     * Constants used for speed and sensitivity, modify them to change gameplay feelings.
     *
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

        enableSensors();
    }

    /**
     * Set the new position of the player depending on the orientation of the device
     */
    public void updatePlayerMovement(){

    }

    /**
     * Enables the sensors used to control player. Call this method after disabling them
     */
    public void enableSensors(){
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
        private final String TAG_LISTENER = "OrientationSensorListener";
        @Override
        public void onSensorChanged(SensorEvent event) {
            synchronized (this){
                //update values from one or another type
                switch(event.sensor.getType()){
                    case Sensor.TYPE_ACCELEROMETER:
                        accelerometerValues = event.values.clone();
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        magneticValues = event.values.clone();
                        break;
                    default:
                        Log.w(TAG_LISTENER, "Wrong sensor type");
                        return;
                }

                //calculate the rotation of the device
                boolean isRotationSuccessful = SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerValues, magneticValues);
                //Log.i(TAG_LISTENER, String.format("New roll values : %f", rotationMatrix[0]));
                //SensorManager#getRotationMatrix can not return result under certain conditions (as a free fall of the device, unlikely to produce when playing though)
                if(isRotationSuccessful){
                    float orientationValues[] = new float[3];
                    SensorManager.getOrientation(rotationMatrix, orientationValues);
                    rollOrientation = orientationValues[2];
                    Log.d(TAG_LISTENER, String.format("New orientation values : Pitch=%f, Yaw=%f, Roll=%f", orientationValues[0], orientationValues[1], orientationValues[2]));
                    Log.i(TAG_LISTENER, "rollOrientation = " + rollOrientation);
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.i(TAG_LISTENER, "Accuracy changed, new accuracy level : " + accuracy);
        }
    }
}
