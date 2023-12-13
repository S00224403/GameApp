package com.example.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    TiltDirection tiltDirection;
    Sequence sequence;
    ImageView Red, Blue, Green, Yellow;
    List<String> colourSequence = new ArrayList<>();
    int sequenceIndex = 0, sequenceNumber = 4, totalScore = 0;
    Handler handler = new Handler();
    TextView sequenceList, score;
    boolean newGame = false;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //find views
        Red = findViewById(R.id.imageViewRed);
        Blue = findViewById(R.id.imageViewBlue);
        Green = findViewById(R.id.imageViewGreen);
        Yellow = findViewById(R.id.imageViewYellow);

        score = findViewById(R.id.score);
        // Initialise sequence
        sequence = new Sequence();
        colourSequence = sequence.GenerateSequence(sequenceNumber);


        // Start flashing the sequence
        flashSequence();

    }

    // Flash the current sequence
    private void flashSequence() {
        if (currentIndex < colourSequence.size()) {
            String colour = colourSequence.get(currentIndex);
            flashImageView(getColorFromString(colour), getColorView(colour));
            currentIndex++;

            // Delay the execution of the next flash after a given duration
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    flashSequence(); // Continue flashing the sequence
                }
            }, 1000); // Delay between flashes (adjust as needed)
        }
        //start tracking the accelerometer (need to switch this so it will only track once the sequence showing is over
        tiltDirection = new TiltDirection(this);
        checkUserSequence();
    }

    private int getColorFromString(String colourName) {
        // Get colour value from colour string
        if (colourName != null) {
            switch (colourName) {
                case "red":
                    return Color.rgb(244, 67, 54);
                case "blue":
                    return Color.rgb(33, 150, 243);
                case "green":
                    return Color.rgb(76, 175, 80);
                case "yellow":
                    return Color.rgb(255, 235, 59);
                default:
                    return Color.WHITE;
            }
        } else {
            return Color.WHITE; // Default value
        }
    }

    private ImageView getColorView(String colourName) {
        // Get ImageView from colour string
        if (colourName != null) {
            switch (colourName) {
                case "red":
                    return Red;
                case "blue":
                    return Blue;
                case "green":
                    return Green;
                case "yellow":
                    return Yellow;
                default:
                    return null;
            }
        } else {
            return null; // Default value
        }
    }

    // Flashes the tile according to the color
    private void flashImageView(int color, ImageView current) {
        int flashColor = Color.WHITE;
        if(current != null){
            // Set the background color to the flashing color
            current.setBackgroundColor(flashColor);

            // Reset the background color after a delay
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    current.setBackgroundColor(color); // Reset to original color
                }
            }, 600); // Delay after the color change (adjust as needed)


        }

    }
    private void checkUserSequence() {
        String currentTilt = tiltDirection.getTilt();
        flashImageView(getColorFromString(currentTilt), getColorView(currentTilt));

        // Check if there is a tilt and there are colours in sequence
        if (currentTilt != null && colourSequence != null && !colourSequence.isEmpty()) {
            Log.d("Debug", "Current Tilt: " + currentTilt);
            Log.d("Debug", "Colour Sequence: " + colourSequence.toString());
            // If tilt is correct remove it from current sequence
            if (currentTilt.equals(colourSequence.get(0))) {

                colourSequence.remove(0);


                tiltDirection.clearTilt();

            } else { // If tilt is not correct to the sequence show gameover activity
                Intent gameOver = new Intent(getApplicationContext(), GameOver.class);
                gameOver.putExtra("gameScore", totalScore);
                startActivity(gameOver);
                finish();
            }
        } else if (colourSequence.isEmpty() && currentTilt == null){// If the sequence list is empty create next level
            Log.d("Debug", "Current Tilt: " + currentTilt);
            Log.d("Debug", "making next sequence");
            Log.d("Debug", "Colour Sequence is null or empty.");
            Log.d("Debug", colourSequence.toString());

            totalScore += sequenceNumber;
            score.setText(String.valueOf(totalScore));
            sequenceNumber += 2;
            currentIndex = 0;
            colourSequence.clear();
            colourSequence = sequence.GenerateSequence(sequenceNumber);



            tiltDirection.clearTilt();
            sequenceIndex = 0;
            // Delay flash new sequence
            try{
                Thread.sleep(600);
                flashSequence();
            } catch (InterruptedException e){

            }


        } else{ // Code to keep checking the tilt until something happens
            handler.postDelayed(this::checkUserSequence, 100);
        }
    }
    //the following methods below are for the tilt direction
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
