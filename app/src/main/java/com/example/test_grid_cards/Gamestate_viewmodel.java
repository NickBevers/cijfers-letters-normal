package com.example.test_grid_cards;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Gamestate_viewmodel extends ViewModel{
    private MutableLiveData<Integer> round;
    public Integer roundNum = 0;
    public Integer gameType = 0;
    public int scorePlayer1 = 0;
    public int scorePlayer2 = 0;
    public int numberOfGames = 1; //Amount of games to be played +1
    public int timerDuration = 5000; // timer duration in milliseconds
    public int player1Difference;
    public int player2Difference;
    public int player1Wins = 0;
    public int player2Wins = 1;

    public MutableLiveData<Integer> getRound() {
        if (round == null) {
            round = new MutableLiveData<Integer> ();
            round.postValue(roundNum);
        }
        return round;
    }

    public int calculateDifference(int num1, int num2, int target){
        player1Difference = Math.abs(num1- target);
        player2Difference = Math.abs(num2- target);

        if (player1Difference <= player2Difference){
            scorePlayer1++;
            return player1Wins;
        }

        if (player1Difference >= player2Difference){
            scorePlayer2++;
            return player2Wins;
        }

        return 2;
    }

    public void winPlayer1(){
        scorePlayer1++;
    }

    public void winPlayer2(){
        scorePlayer2++;
    }

    public void setRound(int num){
        round.postValue(num);
    }

    public void setGame(int num){
        gameType = num;
    }
}
