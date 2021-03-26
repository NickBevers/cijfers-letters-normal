package com.example.test_grid_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public MutableLiveData<Integer> number = new MutableLiveData<Integer>();

    Timer t = new Timer();
    private static int PERIOD = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number.setValue(0);
        GridLayout cardGridLayout = findViewById(R.id.gridlayout);

        for (int i = 0; i < 6; i++){
            View cardView = getLayoutInflater().inflate(R.layout.cardlayout, cardGridLayout, false);
            TextView tv = cardView.findViewById(R.id.number_card_text);
            tv.setText(Integer.toString(i +1));
            cardGridLayout.addView(cardView);

            final int j = i;

            number.observe(this, number -> {
                tv.setText(Integer.toString(number * j));
            });
        }

        ProgressBar pb = findViewById(R.id.progress_bar);
        //pb::setProgress == (number -> pb.setProgress(number)
        number.observe(this , pb::setProgress);
    }

    public void add(View w)  {
        number.setValue(number.getValue() + 1);
    }

    public void startTimer(View w)  {
        long startTime = System.currentTimeMillis();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - startTime <= 5000) {
                    number.postValue(number.getValue() + 1);
                }
                else {
                    cancel();
                }

            }
        }, 0, PERIOD);
    }
}