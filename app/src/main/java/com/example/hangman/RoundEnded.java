package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RoundEnded extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_ended);

        //Get the intent that started this dan thing
        Intent whoDidThis = getIntent();
        String youDidThis = whoDidThis.getStringExtra(MainActivity.extra_label);
        TextView resultTV = findViewById(R.id.terminus);
        TextView revealTV = findViewById(R.id.bigReveal);
        resultTV.setText(youDidThis.substring(0,youDidThis.indexOf(";")));
        String reveal = revealTV.getText().toString()+" "+youDidThis.substring(youDidThis.indexOf(";")+1);
        revealTV.setText(reveal);
    }
}
