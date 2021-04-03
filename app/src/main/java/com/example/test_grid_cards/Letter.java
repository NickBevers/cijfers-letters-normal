package com.example.test_grid_cards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Letter extends Fragment {
    public GridLayout cardGridLayout;
    View v;
    Gamestate_viewmodel gameViewModel;
    Timer t = new Timer();
    private static final int PERIOD = 1000;
    public MutableLiveData<Integer> number = new MutableLiveData<Integer>();
    MutableLiveData<Integer> ronde;
    EditText editText;
    String text;
    InputStream is;
    Letter_viewmodel letter_viewmodel;

    public Letter() {
        // Required empty public constructor
        super(R.layout.number);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.letter, container, false);
        gameViewModel = new ViewModelProvider(requireActivity()).get(Gamestate_viewmodel.class);
        letter_viewmodel = new ViewModelProvider(requireActivity()).get(Letter_viewmodel.class);
        number.setValue(0);
        editText = v.findViewById(R.id.et_word);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cardGridLayout = v.findViewById(R.id.gridlayout);
        Letter_viewmodel letterViewModel = new ViewModelProvider(requireActivity()).get(Letter_viewmodel.class);



        v.findViewById(R.id.btn_vowel).setOnClickListener(view -> {
            letterViewModel.pickVowel();
        });

        v.findViewById(R.id.btn_consonant).setOnClickListener(view -> {
            letterViewModel.pickConsonant();
        });



        letterViewModel.getLetters().observe(getViewLifecycleOwner(), letterArray -> {
            if (letterArray.size() > 0 && letterArray.size() <= 6){
                View cardView = getLayoutInflater().inflate(R.layout.cardlayout, cardGridLayout, false);
                TextView tv = cardView.findViewById(R.id.number_card_text);
                tv.setText(String.valueOf(letterArray.get(letterArray.size()-1)));
                cardGridLayout.addView(cardView);
            }

            if (letterArray.size() == 6){
                TextView tv = v.findViewById(R.id.tv_random);
                /*int randomNum = numberViewModel.pickRandom().getValue();*/
                String randomWord = letterViewModel.randomWord;
                tv.setText(randomWord);
                startTimer(requireView());
            }
        });

        ProgressBar pb = requireActivity().findViewById(R.id.progress_bar);
        //pb::setProgress == (number -> pb.setProgress(number)
        number.observe(requireActivity() , pb::setProgress);
    }

    public void startTimer(View w) {
        long startTime = System.currentTimeMillis();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - startTime <= 5000) {
                    number.postValue(number.getValue() + 1);
                } else {
//                    editText.setEnabled(false);
//                    editText.setFocusable(false);
                    text = String.valueOf(editText.getText());
                    checkText(text);

                    /*ronde = gameViewModel.getRound();
                    //Log.d("TAG", "Timer: TIMEEEEEE " + ronde.getValue());
                    if (ronde.getValue().equals(0)){
                        ((MainActivity) requireActivity()).setRound(1);
                        //Log.d("TAG", "IF" + ronde.getValue());
                    }
                    else{
                        ((MainActivity) requireActivity()).setRound(0);
                        //Log.d("TAG", "ELSE" + ronde.getValue());
                    }*/
                    cancel();
                }

            }
        }, 1000, PERIOD);
    }

    @Override
    public void onDestroyView() {
        Letter_viewmodel letterViewModel = new ViewModelProvider(requireActivity()).get(Letter_viewmodel.class);
        super.onDestroyView();
        letterViewModel.clearLetter();
    }


    private void checkText(String userText) {
        try {
            if (userText.length() < 2) {
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_LONG).show());
                Log.i("TAG", "TOOO SHORT");
            }
            else{
                char [] wordArray = userText.toCharArray();
                ArrayList<Character> tempList = letter_viewmodel.letterArray.getValue();

                ArrayList<Character> wordList = new ArrayList<>();
                for (char c:wordArray){
                    wordList.add(c);
                }
                wordList.retainAll(tempList);
                Log.d("TAG", "WORDLIST: " + wordList);
                Log.d("TAG", "WORDLIST_SIZE " + wordList.size());

                switch (userText.length()){
                    case 2:
                        is = this.getResources().openRawResource(R.raw.filter2);
                        if (wordList.size() < 2){
                            Log.i("TAG", "WROOOOOONG 2. YOUR WORD IS INVALID");
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_LONG).show());
                        }
                        break;

                    case 3:
                        is = this.getResources().openRawResource(R.raw.filter3);
                        if (wordList.size() < 3){
                            Log.i("TAG", "WROOOOOONG 3. YOUR WORD IS INVALID");
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_LONG).show());
                        }
                        break;

                    case 4:
                        is = this.getResources().openRawResource(R.raw.filter4);
                        if (wordList.size() < 4){
                            Log.i("TAG", "WROOOOOONG 4. YOUR WORD IS INVALID");
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_LONG).show());
                        }
                        break;

                    case 5:
                        is = this.getResources().openRawResource(R.raw.filter5);
                        if (wordList.size() < 5){
                            Log.i("TAG", "WROOOOOONG 5. YOUR WORD IS INVALID");
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_LONG).show());
                        }
                        break;

                    case 6:
                        is = this.getResources().openRawResource(R.raw.filter6);
                        if (wordList.size() < 6){
                            Log.i("TAG", "WROOOOOONG 6. YOUR WORD IS INVALID");
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_LONG).show());
                        }
                        break;
                }
                byte[] buffer = new byte[is.available()];
                while (is.read(buffer) != -1){
                    String jsontext = new String(buffer);
                    if (jsontext.contains(userText)){
                        Log.d("TAG", "SimpleText: " + userText.length());
                        Log.i("TAG", "ANTWOORD:  JAAAAAAAA");
                    }
                    else{
                        Log.d("TAG", "ANTWOORD: NEEEEEEEEEEEE");
                    }
                }
            }

        } catch (Exception e) {

            Log.e("TAG", "" + e.toString());
        }
    }

}