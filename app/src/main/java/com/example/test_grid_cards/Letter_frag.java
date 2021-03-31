package com.example.test_grid_cards;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class Letter_frag extends Fragment {

    private LetterViewModel mViewModel;
    private View v;
    private GridLayout cardGridLayout;
    public static Letter_frag newInstance() {
        return new Letter_frag();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.letter_frag, container, false);



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(LetterViewModel.class);
        // TODO: Use the ViewModel

        cardGridLayout = v.findViewById(R.id.gridlayout);

        v.findViewById(R.id.btn_vowel).setOnClickListener(view -> {
            mViewModel.pickVowel();
            Log.d("TAG", "VOWEL");
        });
        v.findViewById(R.id.btn_consonant).setOnClickListener(view -> {
            Log.d("TAG", "CONSONANT");
            mViewModel.pickConsonant();
        });

        mViewModel.getLetters().observe(requireActivity(), letterArray -> {
            Log.d(TAG, "onActivityCreated: CREATED");

            if (letterArray.size() > 0){
                View cardView = getLayoutInflater().inflate(R.layout.cardlayout, cardGridLayout, false);
                TextView tv = cardView.findViewById(R.id.number_card_text);
                tv.setText(String.valueOf(letterArray.get(letterArray.size()-1)));
                cardGridLayout.addView(cardView);
            }


            /*for (int i = 0; i < letterArray.size(); i++) {
                String.valueOf(letterArray.get(i))
            }*/
        });
    }

}