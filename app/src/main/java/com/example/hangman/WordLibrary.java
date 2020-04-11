package com.example.hangman;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordLibrary {
    ArrayList<String> WORDLIST;

    WordLibrary(Activity mainActivity){
        WORDLIST = new ArrayList<String>();
        String string="";
        StringBuilder sb = new StringBuilder();
        InputStream is = mainActivity.getResources().openRawResource(R.raw.words);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while(true){
            try{
                if((string = reader.readLine())==null) break;
            }
            catch(IOException e){
                e.printStackTrace();
            }
            WORDLIST.add(string.toLowerCase().trim());
        }
        try{
            is.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public int fetchLength(){
        return(WORDLIST.size());
    }

    public String getWord(){
        Random rnd = new Random();
        int idx = rnd.nextInt(WORDLIST.size()-1);
        return(WORDLIST.get(idx));
    }
}
