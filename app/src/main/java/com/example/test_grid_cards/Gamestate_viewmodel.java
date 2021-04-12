package com.example.test_grid_cards;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Gamestate_viewmodel extends ViewModel{
    private MutableLiveData<Integer> round;
    private MutableLiveData<Integer> game;
    public static final Integer roundNum = 0;
    public static final Integer gameType = 0;
    public int scorePlayer1 = 0;
    public int scorePlayer2 = 0;
    public int player1Difference;
    public int player2Difference;

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

    public boolean compareNum(int num1, int num2, int target){
        if (num1 > target){
            player1Difference = num1 - target;
        }
        else{
            player1Difference = target - num1;
        }

        if (num2 > target){
            player2Difference = num2 - target;
        }
        else{
            player2Difference = target - num2;
        }

        if (player1Difference < player2Difference){
            scorePlayer1++;
            Log.d("TAG", "compareNum: PLAYER 1 WINS");
            return true;
        }
        else{
            scorePlayer2++;
            Log.d("TAG", "compareNum: PLAYER 2 WINS");
            return false;
        }
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
