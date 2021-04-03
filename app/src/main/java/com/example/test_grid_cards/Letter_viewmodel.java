package com.example.test_grid_cards;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Letter_viewmodel extends ViewModel {
    public MutableLiveData<ArrayList<Character>> letterArray;
    public String randomWord = "TESTER";

    public MutableLiveData<ArrayList<Character>> getLetters(){
        if (letterArray == null){
            letterArray = new MutableLiveData<ArrayList<Character>>();
            letterArray.setValue(new ArrayList<Character>());
        }
        return letterArray;
    }

    public char pickALetter() {
        Random random = new Random();
        int ascii = random.nextInt(26) + 97;; // lowercase 'a'
        return (char)ascii;
    }

    public boolean isVowel (char c) {
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};

        for (char v: vowels) {
            if (v == c) return true;
        }
        return false;
    }

    public boolean isConsonant (char c) {
        return !isVowel(c);
    }


    public void pickVowel() {
        ArrayList<Character> list = getLetters().getValue();
        assert list != null;
        if (list.size() < 6){
            char c;
            do {
                c = pickALetter();
            } while (!isVowel(c));

            list.add(c);
            letterArray.setValue(list);
        }
    }

    public void pickConsonant() {
        ArrayList<Character> list = getLetters().getValue();
        assert list != null;
        if (list.size() < 6){
            char c;
            do {
                c = pickALetter();
            } while (!isConsonant(c));
            list.add(c);
            //Log.d("TAG", "pickConsonant: LISTLIST" + list.size());
            letterArray.setValue(list);
        }
    }

    public void clearLetter(){
        ArrayList<Character> list = getLetters().getValue();
        assert list != null;
        list.clear();
        letterArray.setValue(list);
    }
}
