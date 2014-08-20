package com.example.kaleb_000.dartdojo;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;


/**
 * Created by kaleb_000 on 8/3/2014.
 */
public class MyGlobals extends Activity {

    //global score for the solitaire activity
    int score = 0;

    //current number that you are on for the solitaire activity
    int current_number = 1;

    //Array for the number properties for the solitaire activity
    boolean[] darts_hit = new boolean[2];

    //Will spit out value based on the value inputted into the function
    public String dartthrow(int i) {
        String string;
        score = score + i;
        string = Integer.toString(score);
        return string;
    }

    public double calculateAverage (List<Integer> marks) {
        Integer sum = 0;
        if(!marks.isEmpty()) {
            for (Integer mark : marks ) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }




}
