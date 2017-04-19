package com.example.zofia.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private static float G = 9.806f;
    private View redBall;
    private float startX;
    private float startY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redBall = findViewById(R.id.red_ball);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor defaultSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.e("X-Axis", String.valueOf(event.values[0]));
                Log.e("Y-Axis", String.valueOf(event.values[1]));
                Log.e("Z-Axis", String.valueOf(event.values[2]));
                float yPositopn = event.values[1]/G;
                float xPositopn = event.values[0]/G;

                if(startX ==0 ||startY==0){
                    setUpStartPosition();
                }
                float newX = startX+startX*xPositopn;
                float newY = startY+startY*yPositopn;
                redBall.setX(newX);
                redBall.setY(newY);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        }, defaultSensor, 1000000);
        setUpStartPosition();
    }

    private void setUpStartPosition(){
        startX = redBall.getX()+redBall.getWidth()/2;
        startY = redBall.getY()+redBall.getHeight()/2;
    }
}
