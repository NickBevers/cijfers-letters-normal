package com.example.test_grid_cards;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Gamestate_viewmodel extends ViewModel{
    private MutableLiveData<Integer> round;
    public Integer roundNum = 0;
    public Integer gameType = 0;
    public int scorePlayer1 = 0;
    public int scorePlayer2 = 0;
    public int numberOfGames = 3; //Amount of games to be played +1
    public int player1Difference;
    public int player2Difference;

    public MutableLiveData<Integer> getRound() {
        if (round == null) {
            round = new MutableLiveData<Integer> ();
            round.postValue(roundNum);
        }
        return round;
    }

    public int compareNum(int num1, int num2, int target){
        int res = 0;
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
            res = 0;
        }

        else if (player1Difference > player2Difference){
            scorePlayer2++;
            res = 1;
        }

        else{
            draw();
            res = 2;
        }

        return res;
    }

    public void winPlayer1(){
        scorePlayer1++;
    }

    public void winPlayer2(){
        scorePlayer2++;
    }

    public void draw(){
        scorePlayer1++;
        scorePlayer2++;
    }

    public void setRound(int num){
        round.postValue(num);
    }

    public void setGame(int num){
        gameType = num;
    }
}
