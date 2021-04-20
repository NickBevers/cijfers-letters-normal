package com.example.test_grid_cards;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Letter_viewmodel extends AndroidViewModel {
    public MutableLiveData<ArrayList<Character>> letterArray;
    public String randomWord = "TESTER";
    InputStream is;

    public Letter_viewmodel(@NonNull Application application) {
        super(application);
    }

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

    public boolean checkText(String userText, boolean res) {
        try {
            if (userText.length() < 2) {
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(getApplication().getApplicationContext(), getApplication().getApplicationContext().getResources().getString(R.string.invalid), Toast.LENGTH_SHORT).show());
                Log.i("TAG", "TOOO SHORT");
                res = false;
            }
            else{
                char [] wordArray = userText.toCharArray();
                ArrayList<Character> tempList = letterArray.getValue();

                ArrayList<Character> wordList = new ArrayList<>();
                for (char c:wordArray){
                    wordList.add(c);
                }
                assert tempList != null;
                wordList.retainAll(tempList);
                Log.d("TAG", "WORDLIST: " + wordList);
                Log.d("TAG", "WORDLIST_SIZE " + wordList.size());

                int fileToOpen = getApplication().getResources().getIdentifier("raw/filter" + String.valueOf(userText.length()), null, getApplication().getApplicationContext().getPackageName());

                is = this.getApplication().getApplicationContext().getResources().openRawResource(fileToOpen);
                if (wordList.size() < userText.length()){
                    //Log.i("TAG", "WROOOOOONG 2. YOUR WORD IS INVALID");
                    new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(getApplication().getApplicationContext(), getApplication().getApplicationContext().getResources().getString(R.string.invalid), Toast.LENGTH_SHORT).show());
                    res = false;
                }

                byte[] buffer = new byte[is.available()];
                while (is.read(buffer) != -1){
                    String jsontext = new String(buffer);
                    //Log.d("TAG", "SimpleText: " + userText.length());
                    //Log.i("TAG", "ANTWOORD:  JAAAAAAAA");
                    //Log.d("TAG", "ANTWOORD: NEEEEEEEEEEEE");
                    res = jsontext.contains(userText);
                }
            }

        } catch (Exception e) {
            Log.e("TAG", "" + e.toString());
        }
        return res;
    }
}
