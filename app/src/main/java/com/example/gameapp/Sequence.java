package com.example.gameapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sequence {
    private Random random = new Random();
    List<String> colourSequence = new ArrayList<>();
    String[] colours = new String[]{"red", "blue", "green", "yellow"};
    public List<String> GenerateSequence(int gameLength){
        // Create random sequence of colours
        colourSequence.clear();
        for (int i = 0; i < gameLength; i++) {
            colourSequence.add(colours[random.nextInt(colours.length)]);
        }
        return colourSequence;
    }
}
