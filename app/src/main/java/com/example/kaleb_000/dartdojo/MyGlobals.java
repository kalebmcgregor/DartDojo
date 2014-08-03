package com.example.kaleb_000.dartdojo;

/**
 * Created by kaleb_000 on 8/3/2014.
 */
public class MyGlobals {

    int score = 0;
    //Will spit out value based on the value inputted into the function
    public String dartthrow(int i) {
        String string = new String();
        score = score + i;
        string = Integer.toString(score);
        return string;
    }
}
