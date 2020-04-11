package com.example.hangman;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private String SELECTED_WORD;
    private String MASKED_WORD;
    private WordLibrary WORDS;
    public int LIVES;
    private int GUESSES;
    public boolean LOST;
    public boolean WON;

    public GameEngine(Activity hostActivity){
        WORDS = new WordLibrary(hostActivity);
        LIVES = 0;
        GUESSES = 0;
        resetVals();
    }

    public void resetVals(){
        LIVES=10;
        GUESSES=0;
        LOST = false;
        WON = false;
    }

    public void grabWord(){
        SELECTED_WORD = WORDS.getWord();
        StringBuilder sb = new StringBuilder();
        for(char ch : SELECTED_WORD.toCharArray()){
            sb.append("*");
        }
        MASKED_WORD = sb.toString();
    }

    String cheat(){
        return(SELECTED_WORD);
    }

    public String grabMasked(){ return(MASKED_WORD);}

    public int guessLetter(String guess){
        //Guesses the letter.  Returns the number of occurrences that letter has in the puzzle.
        String properG = Character.toString(guess.charAt(0));
        int startIdx=0;
        int newIdx=0;
        List<Integer> indices = new ArrayList<Integer>();
        while(newIdx>=0){
            newIdx = SELECTED_WORD.indexOf(guess,startIdx);
            if(newIdx>=0){
                indices.add(newIdx);
                startIdx=newIdx+1;
            }
        }
        int ret = indices.size();
        if(ret>0){
            for(int val : indices){
                MASKED_WORD = MASKED_WORD.substring(0,val) + guess.charAt(0) + MASKED_WORD.substring(val+1);
            }
        }else{
            LIVES--;
        }
        evaluateGameState();
        return(ret);
    }

    private void evaluateGameState(){
        if(LIVES==0){
            LOST=true;
        }else{
            if(MASKED_WORD.indexOf("*")==-1){
                WON=true;
            }
        }

    }

}
