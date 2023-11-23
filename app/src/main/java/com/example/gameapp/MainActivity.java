package com.example.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void onClick(View view){
        colourSequence.clear();
        for (int i = 0; i < sequence ; i++) {
            colourSequence.add(colours[rng.nextInt(4)]);
        }
        for(String colour : colourSequence) {
            Toast.makeText(getApplicationContext(), colour, Toast.LENGTH_SHORT).show();
        }
    }
}