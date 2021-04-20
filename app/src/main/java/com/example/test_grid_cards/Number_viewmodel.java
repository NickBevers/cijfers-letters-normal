package com.example.test_grid_cards;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kotlin.jvm.Synchronized;

public class Number_viewmodel extends ViewModel{
    public MutableLiveData<ArrayList<Integer>> numberArray;
    Integer[] highNums = {10, 25, 50, 75, 100};
    ArrayList highList = new ArrayList<Integer>(Arrays.asList(highNums));

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
            int high = highr.nextInt(highList.size() - 1);
            list.add((Integer) highList.get(high));
            numberArray.setValue(list);
        }
    }


    public void clearNumber(){
        ArrayList<Integer> list = getNumbers().getValue();
        assert list != null;
        list.clear();
        numberArray.setValue(list);
    }
}
