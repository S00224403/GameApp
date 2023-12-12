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
import android.widget.Toast;

import java.util.List;

public class GameOver extends AppCompatActivity {

    int gameScore = 0;
    Button newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        DatabaseHandler db = new DatabaseHandler(this);
        Intent intent = getIntent();
        gameScore = intent.getIntExtra("gameScore", 0);
        List<HighscoreClass> highscores = db.top5Highscore();
        for (HighscoreClass ps : highscores){
            Log.d("Debug", "TOP 5 Name : " + ps._name + " Score : " + ps._highscore);
        }
        if (highscores.isEmpty() || gameScore > highscores.get(4)._highscore || highscores.size() < 5) {
            showInputDialog(db);
        }

        newGame = findViewById(R.id.btnNewGame);
    }

    private void showInputDialog(DatabaseHandler db) {
        final EditText input = new EditText(this);
        Log.d("Debug", "showInputDialog: in input dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();
                        Toast.makeText(getApplicationContext(), "Hello, " + name, Toast.LENGTH_SHORT).show();
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
        Intent game =  new Intent(getApplicationContext(), GameActivity.class);

        startActivity(game);

    }
}