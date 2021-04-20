package com.example.test_grid_cards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Letter extends Fragment {
    public GridLayout cardGridLayout;
    private static final int PERIOD = 1000;
    public MutableLiveData<Integer> number = new MutableLiveData<Integer>();
    public boolean result1;
    public boolean result2;
    private

    View v;
    Gamestate_viewmodel gameViewModel;
    Timer t = new Timer();
    MutableLiveData<Integer> ronde;
    EditText editText1;
    EditText editText2;
    Letter_viewmodel letter_viewmodel;

    public Letter() {
        // Required empty public constructor
        super(R.layout.letter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.letter, container, false);
        gameViewModel = new ViewModelProvider(requireActivity()).get(Gamestate_viewmodel.class);
        letter_viewmodel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(Letter_viewmodel.class);
        //letter_viewmodel = new ViewModelProvider(requireActivity()).get(Letter_viewmodel.class);
        number.setValue(0);
        editText1 = v.findViewById(R.id.et_player1);
        editText2 = v.findViewById(R.id.et_player2);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cardGridLayout = v.findViewById(R.id.gridlayout);
        TextView tv_player1 = v.findViewById(R.id.score_player1);
        TextView tv_player2 = v.findViewById(R.id.score_player2);
        tv_player1.setText(String.format(Locale.ENGLISH,"Score: %d", gameViewModel.scorePlayer1));
        tv_player2.setText(String.format(Locale.ENGLISH,"Score: %d", gameViewModel.scorePlayer2));



        v.findViewById(R.id.btn_vowel).setOnClickListener(view -> {
            letter_viewmodel.pickVowel();
        });

        v.findViewById(R.id.btn_consonant).setOnClickListener(view -> {
            letter_viewmodel.pickConsonant();
        });



        letter_viewmodel.getLetters().observe(getViewLifecycleOwner(), letterArray -> {
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
                if (System.currentTimeMillis() - startTime <= 15000) {
                    number.postValue(number.getValue() + 1);
                } else {
                    //editText1.setFocusable(false);
                    //editText2.setFocusable(false);
                    String text1 = String.valueOf(editText1.getText());
                    String text2 = String.valueOf(editText2.getText());

                    boolean resultPlayer1 = letter_viewmodel.checkText(text1, result1);
                    boolean resultPlayer2 = letter_viewmodel.checkText(text2, result2);

                    if (game != gameViewModel.numberOfGames){
                        if (resultPlayer1 && resultPlayer2){
                            if (text1.length() == text2.length()){
                                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), getResources().getString(R.string.draw), Toast.LENGTH_SHORT).show());
                                gameViewModel.winPlayer1();
                                gameViewModel.winPlayer2();
                            }

                            if (text1.length() > text2.length()){
                                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), getResources().getString(R.string.player1_win), Toast.LENGTH_SHORT).show());
                                gameViewModel.winPlayer1();
                            }
                            else{
                                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), getResources().getString(R.string.player2_win), Toast.LENGTH_SHORT).show());
                                gameViewModel.winPlayer2();
                            }
                        }
                        else if (resultPlayer1 && !resultPlayer2){
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), getResources().getString(R.string.player1_win), Toast.LENGTH_SHORT).show());
                            gameViewModel.winPlayer1();
                        }

                        else if (!resultPlayer1 && resultPlayer2){
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), getResources().getString(R.string.player2_win), Toast.LENGTH_SHORT).show());
                            gameViewModel.winPlayer2();
                        }

                        else{
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), getResources().getString(R.string.no_winner), Toast.LENGTH_SHORT).show());
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

}