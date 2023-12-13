package com.example.gameapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

public class TiltDirection implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] acceleration = new float[3];
    private boolean isTiltDetected = false;
    private String tilt;
    private Handler handler = new Handler();
    private Context context;

    public TiltDirection(Context context) {
        // Create instance of accelerometer sensor
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        startTiltCheck();
    }

    private void startTiltCheck() {
        // Start tracking tilt
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkTiltDirection();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void checkTiltDirection() {
        // Check direction of the tilt
        if (acceleration[2] < 9 && acceleration[0] > 4) {
            tilt = "green";
            Log.d("Tilt detected", "green");

        } else if (acceleration[0] < -4 && acceleration[2] < 9) {
            tilt = "red";
            Log.d("Tilt detected", "red");
        } else if (acceleration[1] < -1) {
            tilt = "blue";
            Log.d("Tilt detected", "blue");
        } else if (acceleration[1] > 1) {
            tilt = "yellow";
            Log.d("Tilt detected", "yellow");
        }
        else{
            tilt=null;
        }

        if (tilt != null){
            isTiltDetected = true;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // Bind values for axis
        acceleration[0] = event.values[0]; // x
        acceleration[1] = event.values[1]; // y
        acceleration[2] = event.values[2]; // z
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }


    // Gets the tilt sequence
    public String getTilt(){
        return tilt;
    }
    // Clears the tilt sequence
    public void clearTilt(){
        tilt = null;
    }

}
