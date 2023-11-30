package com.example.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Random rng = new Random();
    ImageView Red, Blue, Green, Yellow;
    int sequence = 4;
    String[] colours = new String[]{"red", "blue", "green", "yellow"};
    List<String> colourSequence = new ArrayList<>();
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Red = findViewById(R.id.imageViewRed);
        Blue = findViewById(R.id.imageViewBlue);
        Green = findViewById(R.id.imageViewGreen);
        Yellow = findViewById(R.id.imageViewYellow);
    }

    public void onClick(View view) {
        colourSequence.clear();
        for (int i = 0; i < sequence; i++) {
            colourSequence.add(colours[rng.nextInt(4)]);
        }
        currentIndex = 0;
        flashSequence();
    }

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
    }

    private void flashImageView(int color, ImageView current) {
        int flashColor = Color.WHITE;

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

    private int getColorFromString(String colorName) {
        switch (colorName) {
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
    }

    private ImageView getColorView(String colorName) {
        switch (colorName) {
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
    }
}
