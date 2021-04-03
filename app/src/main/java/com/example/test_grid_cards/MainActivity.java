/*
Needed:

Main activity -> The screen

Fragment Player 1 -> bottom half of the screen
Fragment Player 2 -> top half of the screen

Viewmodel Letters -> All content for the letters
Viewmodel Numbers -> All content for the numbers
Viewmodel Gamestate -> All info about the game (playing/stopped, round,
 */


package com.example.test_grid_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    Gamestate_viewmodel viewModel;
    private final Letter letter_frag = new Letter();
    private final Number number_frag = new Number();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(Gamestate_viewmodel.class);

        viewModel.getRound().observe(this, round -> {
            if(round.equals(0)){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_player, letter_frag)
                        .commit();
            }
            else{
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_player, number_frag)
                        .commit();
            }
        });
    }

    public void setRound(int num){
        Log.d("TAG", "setRound: RONDE " + num);
        viewModel.setRound(num);
    }
}