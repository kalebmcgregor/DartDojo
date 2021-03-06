package com.example.kaleb_000.dartdojo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;
import com.example.kaleb_000.dartdojo.Number;

import org.w3c.dom.Text;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.prefs.PreferenceChangeEvent;

public class Solitaire extends Activity {


    MyGlobals global = new MyGlobals();
    Number[] numbers = new Number[21];
    int[][] dart_percent_list = new int[2][63];
    boolean[] dart_hit = new boolean[63];
    int current_number = 0;
    List score_list = new ArrayList();
    int high_score = 0;
    double average = 0;
    int dart_1_index = 0;
    int dart_2_index = 1;
    int dart_3_index = 2;
    public static final String PREFS_NAME = "Solitaire";
    boolean submitted = false;
    int n = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solitaire);

        //Fill the dart Array
        for(int i=0; i< numbers.length; i++){
            numbers[i] = new Number();
            numbers[i].set_number(i+1);
        }

        //fill the dart_percent_list if it is empty
        for (int j=0; j < 2; j++) {
            for (int i = 0; i < dart_percent_list[0].length; i++) {
                dart_percent_list[0][i] = 0;
                n = 1;
                dart_hit[i] = false;
            }
        }


        //set the last place of the array to B for the bulls-eye
        numbers[20].set_b();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Set<String> set = settings.getStringSet("score_list", new HashSet<String>());
        String percent_list = settings.getString("percent_list", "");

        score_list.addAll(set);
        high_score = settings.getInt("high_score", high_score);

        n = settings.getInt("n", 1);



        StringTokenizer str = new StringTokenizer(percent_list, ",");

        while (str.hasMoreTokens()) {
            for (int k = 0; k < dart_percent_list[0].length; k++) {
                dart_percent_list[0][k] = Integer.parseInt(str.nextToken());
            }
            n = n -1;
        }

        if (n < 1) {
            n = 1;
        }

        set_dart_percent_text();
    }

    protected void onPause(){
        super.onPause();

        Set<String> set = new HashSet<String>();
        set.addAll(score_list);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet("score_list", set);
        editor.putInt("high_score", high_score);

        // Commit the edits!
        editor.commit();
    }

    public double get_dart_average (int i) {
        DecimalFormat df = new DecimalFormat("#.#");
        if (n <= 0) {
            return 0;
            }
        double sum = dart_percent_list[0][i];
        double average = sum/ (double) n * 100;
        String format = df.format(average);
        average = Double.parseDouble(format);
        return average;
        }

    //First variable passed in will be set to visible, while the second will be set to invisible
    public void button_visibility_toggle(int visible, int invisible) {
        Button button_visible = (Button) findViewById(visible);
        button_visible.setVisibility(View.VISIBLE);
        Button button_invisible = (Button) findViewById(invisible);
        button_invisible.setVisibility(View.INVISIBLE);
    }

    //set variable passed in to visible (usually a R.id variable)
    public void make_button_visible(int visible) {
        Button button_visible = (Button) findViewById(visible);
        button_visible.setVisibility(View.VISIBLE);
    }

    //set variable passed in to invisible (usually a R.id variable)
    public void make_button_invisible(int invisible) {
        Button button_invisible = (Button) findViewById(invisible);
        button_invisible.setVisibility(View.INVISIBLE);
    }

    //sets dart buttons 1, 2, and 3 to visible. Sets dart buttons 4, 5, and 6 to invisible.
    public void dart_default_visibility() {
        Button dart_1 = (Button) findViewById(R.id.button);
        Button dart_2 = (Button) findViewById(R.id.button2);
        Button dart_3 = (Button) findViewById(R.id.button3);
        Button dart_4 = (Button) findViewById(R.id.button4);
        Button dart_5 = (Button) findViewById(R.id.button5);
        Button dart_6 = (Button) findViewById(R.id.button6);
        dart_1.setVisibility(View.VISIBLE);
        dart_2.setVisibility(View.VISIBLE);
        dart_3.setVisibility(View.VISIBLE);
        dart_4.setVisibility(View.INVISIBLE);
        dart_5.setVisibility(View.INVISIBLE);
        dart_6.setVisibility(View.INVISIBLE);
    }

    //will set the dart visibility according to the properties of the number class passed in
    public void set_dart(Number number) {
        if (number.get_dart_1()) {
            button_visibility_toggle(R.id.button4, R.id.button);
        } else {
            button_visibility_toggle(R.id.button, R.id.button4);
        }

        if (number.get_dart_2()) {
            button_visibility_toggle(R.id.button5, R.id.button2);
        } else {
            button_visibility_toggle(R.id.button2, R.id.button5);
        }

        if (number.get_dart_3()) {
            button_visibility_toggle(R.id.button6, R.id.button3);
        } else {
            button_visibility_toggle(R.id.button3, R.id.button6);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.solitaire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void set_default_all_darts (Number[] dartArray) {
        for (int i = 0; i < dartArray.length; i++) {
            dartArray[i].set_properties(false, false, false, dartArray[i].get_number());
        }
        global.score = 0;
    }

    public void set_dart_percent_text() {

        TextView dart_1_percent = (TextView) findViewById(R.id.dart_1_percent);
        TextView dart_2_percent = (TextView) findViewById(R.id.dart_2_percent);
        TextView dart_3_percent = (TextView) findViewById(R.id.dart_3_percent);
        dart_1_percent.setText(Double.toString(get_dart_average(dart_1_index)));
        dart_2_percent.setText(Double.toString(get_dart_average(dart_2_index)));
        dart_3_percent.setText(Double.toString(get_dart_average(dart_3_index)));
    }
    //function used for deciding which dart was thrown in Solitaire class
    public void dart_pressed(View view){

        //Set int button_id to the id of the button that was pressed
        int button_id = view.getId();

        //Set new TextView textView with the same properties as the current textView
        TextView textView = (TextView) findViewById(R.id.Score);

        //Select the right button and do actions depending on which dart was selected
        switch(button_id){
            case R.id.button:
                textView.setText(global.dartthrow(1));
                button_visibility_toggle(R.id.button4, R.id.button);
                numbers[current_number].set_dart_1(true);
                dart_percent_list[0][dart_1_index]++;
                dart_hit[dart_1_index] = true;
                break;
            case R.id.button2:
                textView.setText(global.dartthrow(1));
                button_visibility_toggle(R.id.button5, R.id.button2);
                numbers[current_number].set_dart_2(true);
                dart_percent_list[0][dart_2_index]++;
                dart_hit[dart_2_index] = true;
                break;
            case R.id.button3:
                textView.setText(global.dartthrow(1));
                button_visibility_toggle(R.id.button6, R.id.button3);
                numbers[current_number].set_dart_3(true);
                dart_percent_list[0][dart_3_index]++;
                dart_hit[dart_3_index] = true;
                break;
            case R.id.button4:
                textView.setText(global.dartthrow(-1));
                button_visibility_toggle(R.id.button, R.id.button4);
                numbers[current_number].set_dart_1(false);
                dart_percent_list[0][dart_1_index]--;
                dart_hit[dart_1_index] = false;
                break;
            case R.id.button5:
                textView.setText(global.dartthrow(-1));
                button_visibility_toggle(R.id.button2, R.id.button5);
                numbers[current_number].set_dart_2(false);
                dart_percent_list[0][dart_2_index]--;
                dart_hit[dart_2_index] = false;
                break;
            case R.id.button6:
                textView.setText(global.dartthrow(-1));
                button_visibility_toggle(R.id.button3, R.id.button6);
                numbers[current_number].set_dart_3(false);
                dart_percent_list[0][dart_3_index]--;
                dart_hit[dart_3_index] = false;
                break;

        }
    }



    public void submit_button_pressed (View view) {

        //create decimal format which we will use later to trim the average to two decimal spots
        DecimalFormat df = new DecimalFormat("#.##");
        TextView number = (TextView) findViewById(R.id.number);

        //add the score to the list of scores we already have
        score_list.add(global.score);

        //calculate the average of the list of scores and set it to average
        average = global.calculateAverage(score_list);

        //set the high score if the current score is greater than the high score
        if (global.score > high_score) {
            high_score = global.score;
        }

        //reset the global score
        global.score = 0;

        current_number = 0;
        dart_1_index = 0;
        dart_2_index = 1;
        dart_3_index = 2;
        number.setText(numbers[current_number].get_number());
        make_button_invisible(R.id.previous_number);
        make_button_visible(R.id.next_number);

        //update the dart_percent text
        set_dart_percent_text();

        //increment all of the n values by 1 and all of dart_hit to false
            for (int i = 0; i < dart_percent_list[0].length; i++) {
                dart_hit[i] = false;
            }

        n++;

        //update the dart_percent text
        if (submitted == false) {
            set_dart_percent_text();
            n++;
        }

        submitted = true;

        for (int i = 0; i <= 20; i++) {
            numbers[i].set_all_false();
        }



        //set all the dart position to their correct visibility
        dart_default_visibility();

        //grab the textview for average_score, high_score, and current score text boxes
        TextView average_score_text = (TextView) findViewById(R.id.average_score);
        TextView high_score_text = (TextView) findViewById(R.id.high_score);
        TextView score_text = (TextView) findViewById(R.id.Score);

        //set average to the format of df
        average = Double.valueOf(df.format(average));

        //set average_score text and high_score text to the values of average and high_score
        average_score_text.setText(Double.toString(average));
        high_score_text.setText(Integer.toString(high_score));

        score_text.setText(Integer.toString(global.score));

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        StringBuilder percent_list = new StringBuilder();
        for (int i = 0; i < dart_percent_list[0].length; i++) {
            percent_list.append(dart_percent_list[0][i]).append(",");
        }

        editor.putString("percent_list", percent_list.toString());
        editor.putInt("n", n);

        editor.commit();
    }

    public void clear_button_pressed (View view) {
        score_list.clear();
        high_score = 0;
        average = 0;
        global.score = 0;
        submit_button_pressed(new View(this));
        set_default_all_darts(numbers);
        for (int j=0; j < 2; j++) {
            for (int i = 0; i < dart_percent_list[0].length; i++) {
                dart_percent_list[0][i] = 0;
                n = 1;
                dart_hit[i] = false;
            }
        }
        set_dart_percent_text();
        dart_default_visibility();
    }

    //Function used when next_number button and previous_number button is pressed
    public void next_previous_button_pressed (View view) {
        //Set int button_id to the id of the button that was pressed
        int button_id = view.getId();
        TextView textView = (TextView) findViewById(R.id.number);

        //Select the right button and do actions depending on which dart was selected
        switch(button_id) {
            case R.id.previous_number:

                //this if statement is here to prevent current_number for going negative
                if (current_number >= 1) {

                    //decrease current_number by 1
                    current_number--;

                    if (current_number == 0) {
                        make_button_invisible(R.id.previous_number);
                    }

                    //set the dart visibility of the current number
                    set_dart(numbers[current_number]);

                    //change the number text
                    textView.setText(numbers[current_number].get_number());
                    dart_1_index = dart_1_index - 3;
                    dart_2_index = dart_2_index - 3;
                    dart_3_index = dart_3_index - 3;

                    n = n -1;
                    set_dart_percent_text();
                    n++;

                    //make next_button visible so it always populates
                    make_button_visible(R.id.next_number);

                }

                break;
            case R.id.next_number:

                //this if statement is here to prevent current_number for being too big
                if (current_number <= 20) {

                    //changes the next set to default positions if they aren't set yet
                    dart_default_visibility();

                    //increment the current_number
                    current_number++;
                    set_dart(numbers[current_number]);
                    textView.setText(numbers[current_number].get_number());
                    make_button_visible(R.id.previous_number);
                    dart_1_index = dart_1_index + 3;
                    dart_2_index = dart_2_index + 3;
                    dart_3_index = dart_3_index + 3;

                    n = n -1;
                    set_dart_percent_text();
                    n++;

                    if (current_number == 20) {
                        make_button_invisible(R.id.next_number);
                    }
                }

                break;
        }
    }
}
