package com.example.gameapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GameOver extends AppCompatActivity {

    int gameScore = 0;
    Button newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        // Create db connection
        DatabaseHandler db = new DatabaseHandler(this);

        // Get user score
        Intent intent = getIntent();
        gameScore = intent.getIntExtra("gameScore", 0);

        // Get top 5 high scores
        List<HighscoreClass> highscores = db.top5Highscore();

        // Show user their score
        TextView score = findViewById(R.id.tvScore);
        score.setText("Your score was " + gameScore);

        // Check if user score can be added to database
        if (highscores.isEmpty() || gameScore > highscores.get(highscores.size() - 1)._highscore || highscores.size() < 5) {
            showInputDialog(db);
        }

        newGame = findViewById(R.id.btnNewGame);
    }

    private void showInputDialog(DatabaseHandler db) {
        final EditText input = new EditText(this);
        Log.d("Debug", "showInputDialog: in input dialog");

        // Prompt user with dialog to insert their name
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();
//                        Toast.makeText(getApplicationContext(), "Hello, " + name, Toast.LENGTH_SHORT).show();
                        // Add user to database
                        db.addHighscore(new HighscoreClass(name, gameScore));
                        List<HighscoreClass> players = db.getAllHighscore();
                        for (HighscoreClass player : players) {
                            // Log the players' names
                            Log.d("Debug", "Name: " + name + " Score: " + gameScore);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
    public void newGame(View view){
        // Start new game
        Intent game =  new Intent(getApplicationContext(), GameActivity.class);

        startActivity(game);
        finish();

    }
    public void highScores(View view){
        // View high scores
        Intent highScores = new Intent(getApplicationContext(), HighScores.class);
        startActivity(highScores);
        finish();
    }
}