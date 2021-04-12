package com.example.test_grid_cards;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Number extends Fragment {

    public GridLayout cardGridLayout;
    View v;
    Gamestate_viewmodel gameViewModel;
    Number_viewmodel numberViewModel;
    Button btn_Check;
    int num_player1;
    int num_player2;
    int targetNum;
    EditText editText1;
    EditText editText2;
    Timer t = new Timer();
    private static final int PERIOD = 1000;
    public MutableLiveData<Integer> number = new MutableLiveData<Integer>();
    MutableLiveData<Integer> ronde;

    public Number() {
        // Required empty public constructor
        super(R.layout.number);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.number, container, false);
        number.setValue(0);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cardGridLayout = v.findViewById(R.id.gridlayout);
        gameViewModel = new ViewModelProvider(requireActivity()).get(Gamestate_viewmodel.class);
        numberViewModel = new ViewModelProvider(requireActivity()).get(Number_viewmodel.class);
        editText1 = v.findViewById(R.id.et_player1);
        editText2 = v.findViewById(R.id.et_player2);
        btn_Check = v.findViewById(R.id.check_button);
        btn_Check.setVisibility(View.INVISIBLE);


        TextView tv_player1 = v.findViewById(R.id.score_player1);
        TextView tv_player2 = v.findViewById(R.id.score_player2);
        tv_player1.setText(String.format(Locale.ENGLISH,"Score: %d", gameViewModel.scorePlayer1));
        tv_player2.setText(String.format(Locale.ENGLISH,"Score: %d", gameViewModel.scorePlayer2));


        v.findViewById(R.id.btn_low_number).setOnClickListener(view -> {
            numberViewModel.pickLowNumber();
            //Log.d("TAG", "LOW");
        });

        v.findViewById(R.id.btn_high_number).setOnClickListener(view -> {
            //Log.d("TAG", "HIGH");
            numberViewModel.pickHighNumber();
        });

        btn_Check.setOnClickListener(view -> {
            if (editText1.getText().length() == 0){
                gameViewModel.scorePlayer2++;
            }
            else if (editText2.getText().length() == 0){
                gameViewModel.scorePlayer1++;
            }
            else{
                num_player1 = Integer.parseInt(String.valueOf(editText1.getText()));
                num_player2 = Integer.parseInt(String.valueOf(editText2.getText()));
                boolean result = gameViewModel.compareNum(num_player1, num_player2, targetNum);
                if (result){
                    new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Player 1 wins!", Toast.LENGTH_LONG).show());
                }
                else{
                    new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(requireContext(), "Player 2 wins!", Toast.LENGTH_LONG).show());
                }
                start2ndTimer(requireView());
            }
        });



        numberViewModel.getNumbers().observe(getViewLifecycleOwner(), numberArray -> {
            //Log.d("TAG", "onActivityCreated: " + numberArray);
            if (numberArray.size() > 0 && numberArray.size() <= 6){
                View cardView = getLayoutInflater().inflate(R.layout.cardlayout, cardGridLayout, false);
                TextView tv = cardView.findViewById(R.id.number_card_text);
                tv.setText(String.valueOf(numberArray.get(numberArray.size()-1)));
                cardGridLayout.addView(cardView);
            }

            if (numberArray.size() == 6){
                TextView tv = v.findViewById(R.id.tv_random);
                targetNum = numberViewModel.randomNum;
                tv.setText(String.format(Locale.ENGLISH, "Number to reach: %d", targetNum));
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
                }
                else {
                    btn_Check.getHandler().post(new Runnable() {
                        public void run() {
                            btn_Check.setVisibility(View.VISIBLE);
                        }
                    });
                    cancel();
                }
            }
        }, 1000, PERIOD);
    }

    public void start2ndTimer(View m){
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                //switch rounds
                ronde = gameViewModel.getRound();
                if (ronde.getValue().equals(2)){
                    ((MainActivity) requireActivity()).setRound(0);
                }
                else {
                    ((MainActivity) requireActivity()).setRound(ronde.getValue() + 1);
                }
            }
        }, 2000);
    }

    @Override
    public void onDestroyView() {
        Number_viewmodel numberViewModel = new ViewModelProvider(requireActivity()).get(Number_viewmodel.class);
        super.onDestroyView();
        numberViewModel.clearNumber();
    }

}