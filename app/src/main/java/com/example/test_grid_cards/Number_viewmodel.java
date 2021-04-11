package com.example.test_grid_cards;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Random;

public class Number_viewmodel extends ViewModel{
    public MutableLiveData<ArrayList<Integer>> numberArray;
    Random random = new Random();
    public int randomNum = random.nextInt(900)+100;

    public MutableLiveData<ArrayList<Integer>> getNumbers(){
        if (numberArray == null){
            numberArray = new MutableLiveData<ArrayList<Integer>>();
            numberArray.setValue(new ArrayList<Integer>());
        }
        return numberArray;
    }

    public void pickLowNumber() {
        ArrayList<Integer> list = getNumbers().getValue();
        assert list != null;
        if (list.size() < 6) {
            Random lowr = new Random();
            //if (list.)
            list.add(lowr.nextInt(9) + 1); //9 is number between 1 and 9
            numberArray.setValue(list);
        }
    }

    public void pickHighNumber() {
        ArrayList<Integer> list = getNumbers().getValue();
        assert list != null;
        if (list.size() < 6) {
            Random highr = new Random();
            int high = highr.nextInt(5);
            switch (high) {
                case 0:
                    high = 10;
                    break;

                case 1:
                    high = 25;
                    break;

                case 2:
                    high = 50;
                    break;

                case 3:
                    high = 75;
                    break;

                case 4:
                    high = 100;
                    break;
            }
            list.add(high);
            numberArray.setValue(list);
        }
    }

    public void compare(int num1, int num2){

    }


    public void clearNumber(){
        ArrayList<Integer> list = getNumbers().getValue();
        assert list != null;
        list.clear();
        numberArray.setValue(list);
    }
}
