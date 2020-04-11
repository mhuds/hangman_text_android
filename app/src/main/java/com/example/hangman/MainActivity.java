package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GameEngine GE;
    String howdy;
    StringBuilder guesses;
    public static final String extra_label = "com.example.hangman.extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guesses = new StringBuilder("");

        GE = new GameEngine(this);
        GE.grabWord();
        //TODO:  REMOVE CHEAT CALLS EVENTUALLY
        howdy = GE.cheat();
        TextView textView = findViewById(R.id.debug);
        textView.setText(howdy);
        //END TODO

        updateUI();

        final Button btn = (Button)findViewById(R.id.btn);
        final Button gUp = (Button)findViewById(R.id.giveUp);
        View.OnClickListener guess;
        View.OnClickListener giveUp;
        guess = new View.OnClickListener(){

            @Override
            public void onClick(View v){
                EditText et = findViewById(R.id.letterGuess);
                String screenGuess = et.getText().toString();
                if(screenGuess.trim().length()>0){
                    int foundThisMany = GE.guessLetter(screenGuess);
                    updateGuessLog(screenGuess);
                    if(foundThisMany>0){
                        updatePuzzle();
                    }
                    if(GE.LOST || GE.WON){
                        Intent gameover = new Intent(MainActivity.this, RoundEnded.class);
                        TextView goTV = (TextView)findViewById(R.id.terminus);
                        String message = new String();
                        if(GE.LOST){
                            message = getString(R.string.round_ended_lost);
                        }else{
                            message = getString(R.string.round_ended_won);
                        }
                        StringBuilder extrasout = new StringBuilder();
                        extrasout.append(message+";"+GE.cheat());
                        gameover.putExtra(extra_label,extrasout.toString());
                        startActivity(gameover);
                    }
                    updateUI();
                }
            }
        };
        giveUp = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GE.LOST=true;
                Intent gameover = new Intent(MainActivity.this, RoundEnded.class);
                TextView goTV = (TextView)findViewById(R.id.terminus);
                String message = new String();
                message="You Lost!";
                StringBuilder extrasout = new StringBuilder();
                extrasout.append(message+";"+GE.cheat());
                gameover.putExtra(extra_label,extrasout.toString());
                startActivity(gameover);
                GE.grabWord();
                //TODO:  REMOVE CHEAT CALLS EVENTUALLY
                howdy = GE.cheat();
                TextView textView = findViewById(R.id.debug);
                textView.setText(howdy);
                //END TODO
                updateUI();
                GE.resetVals();
                resetGuessLog();
            }
        };
        btn.setOnClickListener(guess);
        gUp.setOnClickListener(giveUp);

    }

    private void updateUI(){
        updatePuzzle();
        updateLives();
    }

    private void updatePuzzle(){
        TextView textView = findViewById(R.id.puzzleBox);
        textView.setText(GE.grabMasked());
    }

    private void updateLives(){
        TextView updateTV = findViewById(R.id.livesVal);
        updateTV.setText(Integer.toString(GE.LIVES));
    }

    private void updateGuessLog(String guess){
        guesses.append(""+guess.substring(0,1)+", ");
        TextView tv = findViewById(R.id.guessHistory);
        tv.setText(guesses);
    }

    private void resetGuessLog(){
        guesses = new StringBuilder("");
        TextView tv = findViewById(R.id.guessHistory);
        tv.setText(guesses);
    }
}
