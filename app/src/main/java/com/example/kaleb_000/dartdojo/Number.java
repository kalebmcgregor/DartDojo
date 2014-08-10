package com.example.kaleb_000.dartdojo;

import android.app.Activity;

/**
 * Created by kaleb_000 on 8/9/2014.
 */
public class Number extends Activity {

    boolean dart_1_hit = false;
    boolean dart_2_hit = false;
    boolean dart_3_hit = false;
    String number;

    public void set_properties(boolean dart_1, boolean dart_2, boolean dart_3, String number) {
        this.dart_1_hit = dart_1;
        this.dart_2_hit = dart_2;
        this.dart_3_hit = dart_3;
        this.number = number;
    }

    //Will return
    public int previous_number() {
        int previous_number = Integer.parseInt(this.number);
        previous_number = previous_number - 1;
        this.number = Integer.toString(previous_number);
        return previous_number;
    }

    public boolean get_dart_1() {
        return this.dart_1_hit;
    }

    public boolean get_dart_2() {
        return this.dart_2_hit;
    }

    public boolean get_dart_3() {
        return this.dart_3_hit;
    }

    public String get_number() {
        return this.number;
    }
}
