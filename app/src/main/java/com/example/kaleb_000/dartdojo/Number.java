package com.example.kaleb_000.dartdojo;

import android.app.Activity;

/**
 * Created by kaleb_000 on 8/9/2014.
 */
public class Number extends Activity {

    boolean dart_1_hit = false;
    boolean dart_2_hit = false;
    boolean dart_3_hit = false;
    int number;

    public void set_properties(boolean dart_1, boolean dart_2, boolean dart_3, int number) {
        this.dart_1_hit = dart_1;
        this.dart_2_hit = dart_2;
        this.dart_3_hit = dart_3;
        this.number = number;
    }
}
