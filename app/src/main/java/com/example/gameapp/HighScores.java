package com.example.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class HighScores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        // Create db connection
        DatabaseHandler db = new DatabaseHandler(this);

        // Get top 5 players and get listview to display those players
        ListView topScores = findViewById(R.id.lvTopScores);
        List<HighscoreClass> topPlayers = db.top5Highscore();

        // Change topPlayers list to adapter to add to listview and display
        ArrayAdapter<HighscoreClass> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topPlayers);
        topScores.setAdapter(adapter);
    }
    public void newGame(View view){
        // Start new game
        Intent newGame = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(newGame);
        finish();
    }
}