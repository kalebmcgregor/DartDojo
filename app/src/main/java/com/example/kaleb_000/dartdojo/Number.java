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

    public void set_dart_1(boolean dart_hit) {
        this.dart_1_hit = dart_hit;
    }

    public void set_dart_2(boolean dart_hit) {
        this.dart_2_hit = dart_hit;
    }

    public void set_dart_3(boolean dart_hit) {
        this.dart_3_hit = dart_hit;
    }

    public void set_number(int i) {
        this.number = Integer.toString(i);
   }

    public void set_b() { this.number = "B"; }

    public void set_all_false() {
        this.dart_1_hit = false;
        this.dart_2_hit = false;
        this.dart_3_hit = false;
        }
}
