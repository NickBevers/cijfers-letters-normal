package com.example.test_grid_cards;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Gamestate_viewmodel extends ViewModel{
    private MutableLiveData<Integer> round;
    private MutableLiveData<Integer> game;
    public static final Integer roundNum = 0;
    public static final Integer gameType = 0;
    public int scorePlayer1 = 0;
    public int scorePlayer2 = 0;

    public MutableLiveData<Integer> getRound() {
        if (round == null) {
            round = new MutableLiveData<Integer> ();
            round.postValue(roundNum);
        }
        return round;
    }

    public MutableLiveData<Integer> getGame() {
        if (game == null) {
            game = new MutableLiveData<Integer> ();
            game.postValue(gameType);
        }
        return game;
    }

    public void win_player1(){
        scorePlayer1++;
    }

    public void win_player2(){
        scorePlayer2++;
    }

    public void setRound(int num){
        if (num == 0){
            round.postValue(0);
        }
        else{
            round.postValue(1);
        }
    }
}
