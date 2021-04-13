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
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Letter extends Fragment {
    public GridLayout cardGridLayout;
    private static final int PERIOD = 1000;
    public MutableLiveData<Integer> number = new MutableLiveData<Integer>();
    public boolean result1;
    public boolean result2;

    View v;
    Gamestate_viewmodel gameViewModel;
    Timer t = new Timer();
    MutableLiveData<Integer> ronde;
    EditText editText1;
    EditText editText2;
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
        editText1 = v.findViewById(R.id.et_player1);
        editText2 = v.findViewById(R.id.et_player2);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cardGridLayout = v.findViewById(R.id.gridlayout);
        Letter_viewmodel letterViewModel = new ViewModelProvider(requireActivity()).get(Letter_viewmodel.class);
        TextView tv_player1 = v.findViewById(R.id.score_player1);
        TextView tv_player2 = v.findViewById(R.id.score_player2);
        tv_player1.setText(String.format(Locale.ENGLISH,"Score: %d", gameViewModel.scorePlayer1));
        tv_player2.setText(String.format(Locale.ENGLISH,"Score: %d", gameViewModel.scorePlayer2));



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
                int game = gameViewModel.gameType;
                if (System.currentTimeMillis() - startTime <= 5000) {
                    number.postValue(number.getValue() + 1);
                } else {
                    //editText1.setFocusable(false);
                    //editText2.setFocusable(false);
                    String text1 = String.valueOf(editText1.getText());
                    String text2 = String.valueOf(editText2.getText());

                    boolean resultPlayer1 = checkText(text1, result1);
                    boolean resultPlayer2 = checkText(text2, result2);

                    if (game != gameViewModel.numberOfGames){
                        if (resultPlayer1 && resultPlayer2){
                            if (text1.length() == text2.length()){
                                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Draw!", Toast.LENGTH_SHORT).show());
                                gameViewModel.draw();
                            }

                            if (text1.length() > text2.length()){
                                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Player 1 wins!", Toast.LENGTH_SHORT).show());
                                gameViewModel.winPlayer1();
                            }
                            else{
                                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Player 2 wins!", Toast.LENGTH_SHORT).show());
                                gameViewModel.winPlayer2();
                            }
                        }
                        else if (resultPlayer1 && !resultPlayer2){
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Player 1 wins!", Toast.LENGTH_SHORT).show());
                            gameViewModel.winPlayer1();
                        }

                        else if (!resultPlayer1 && resultPlayer2){
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Player 2 wins!", Toast.LENGTH_SHORT).show());
                            gameViewModel.winPlayer2();
                        }

                        else{
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "No Points!", Toast.LENGTH_SHORT).show());
                        }
                    }


                    game += 1;
                    gameViewModel.setGame(game);
                    Log.d("TAG", "GAME: " + game);
                    if (game < gameViewModel.numberOfGames){
                        ((MainActivity) requireActivity()).setRound(0);
                    }
                    else {
                        ((MainActivity) requireActivity()).setRound(3);
                    }

                    cancel();
                }

            }
        }, 1000, PERIOD);
    }

    @Override
    public void onStart() {
        super.onStart();
        editText1.setText("");
        editText2.setText("");
    }

    @Override
    public void onDestroyView() {
        Letter_viewmodel letterViewModel = new ViewModelProvider(requireActivity()).get(Letter_viewmodel.class);
        super.onDestroyView();
        letterViewModel.clearLetter();
    }

    private boolean checkText(String userText, boolean res) {
        try {
            if (userText.length() < 2) {
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_SHORT).show());
                Log.i("TAG", "TOOO SHORT");
                res = false;
            }
            else{
                char [] wordArray = userText.toCharArray();
                ArrayList<Character> tempList = letter_viewmodel.letterArray.getValue();

                ArrayList<Character> wordList = new ArrayList<>();
                for (char c:wordArray){
                    wordList.add(c);
                }
                assert tempList != null;
                wordList.retainAll(tempList);
                Log.d("TAG", "WORDLIST: " + wordList);
                Log.d("TAG", "WORDLIST_SIZE " + wordList.size());

                switch (userText.length()){
                    case 2:
                        is = this.getResources().openRawResource(R.raw.filter2);
                        if (wordList.size() < 2){
                            Log.i("TAG", "WROOOOOONG 2. YOUR WORD IS INVALID");
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_SHORT).show());
                            res = false;
                        }
                        break;

                    case 3:
                        is = this.getResources().openRawResource(R.raw.filter3);
                        if (wordList.size() < 3){
                            Log.i("TAG", "WROOOOOONG 3. YOUR WORD IS INVALID");
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_SHORT).show());
                            res = false;
                        }
                        break;

                    case 4:
                        is = this.getResources().openRawResource(R.raw.filter4);
                        if (wordList.size() < 4){
                            Log.i("TAG", "WROOOOOONG 4. YOUR WORD IS INVALID");
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_SHORT).show());
                            res = false;
                        }
                        break;

                    case 5:
                        is = this.getResources().openRawResource(R.raw.filter5);
                        if (wordList.size() < 5){
                            Log.i("TAG", "WROOOOOONG 5. YOUR WORD IS INVALID");
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_SHORT).show());
                            res = false;
                        }
                        break;

                    case 6:
                        is = this.getResources().openRawResource(R.raw.filter6);
                        if (wordList.size() < 6){
                            Log.i("TAG", "WROOOOOONG 6. YOUR WORD IS INVALID");
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Your word is not a valid word", Toast.LENGTH_SHORT).show());
                            res = false;
                        }
                        break;
                }
                byte[] buffer = new byte[is.available()];
                while (is.read(buffer) != -1){
                    String jsontext = new String(buffer);
                    if (jsontext.contains(userText)){
                        Log.d("TAG", "SimpleText: " + userText.length());
                        Log.i("TAG", "ANTWOORD:  JAAAAAAAA");
                        res = true;
                    }
                    else{
                        Log.d("TAG", "ANTWOORD: NEEEEEEEEEEEE");
                        res = false;
                    }
                }
            }

        } catch (Exception e) {
            Log.e("TAG", "" + e.toString());
        }
        return res;
    }

}