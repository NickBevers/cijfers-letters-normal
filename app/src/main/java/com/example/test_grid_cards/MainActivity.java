package com.example.test_grid_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public MutableLiveData<Integer> number = new MutableLiveData<Integer>();
    private final Letter_frag fragLetter = new Letter_frag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.letterLayout, fragLetter)
                .commit();

    }
}


/* CODE FOR UPSIDE DOWN FRAGMENT

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout mView = (ConstraintLayout) inflater.inflate(R.layout.fragment_second, container, false);
        int w = container.getWidth();
        int h = container.getHeight();
        mView.setRotation(180);
        mView.setTranslationX((w - h) / 2);
        mView.setTranslationY((h - w) / 2);
        ViewGroup.LayoutParams lp = mView.getLayoutParams();
        lp.height = w;
        lp.width = h;
        mView.requestLayout();
        return mView;
    }

 */