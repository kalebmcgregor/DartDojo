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

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

public class Solitaire extends Activity {


    MyGlobals global = new MyGlobals();
    Number[] numbers = new Number[21];
    int current_number = 0;
    List score_list = new ArrayList();
    int high_score = 0;
    int counter = 0;
    int average = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solitaire);

        //Fill the dart Array
        for(int i=0; i< numbers.length; i++){
            numbers[i] = new Number();
            numbers[i].set_number(i+1);
        }
        //set the last place of the array to B for the bulls-eye
        numbers[20].set_b();
    }

    protected void onPause() {
        super.onPause();

        SharedPreferences settings = getPreferences(0);
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
                break;
            case R.id.button2:
                textView.setText(global.dartthrow(1));
                button_visibility_toggle(R.id.button5, R.id.button2);
                numbers[current_number].set_dart_2(true);
                break;
            case R.id.button3:
                textView.setText(global.dartthrow(1));
                button_visibility_toggle(R.id.button6, R.id.button3);
                numbers[current_number].set_dart_3(true);
                break;
            case R.id.button4:
                textView.setText(global.dartthrow(-1));
                button_visibility_toggle(R.id.button, R.id.button4);
                numbers[current_number].set_dart_1(false);
                break;
            case R.id.button5:
                textView.setText(global.dartthrow(-1));
                button_visibility_toggle(R.id.button2, R.id.button5);
                numbers[current_number].set_dart_2(false);
                break;
            case R.id.button6:
                textView.setText(global.dartthrow(-1));
                button_visibility_toggle(R.id.button3, R.id.button6);
                numbers[current_number].set_dart_3(false);
                break;

        }
    }

    public void submit_button_pressed (View view) {
        counter++;
        average = 0;
        if (global.score > high_score) {
            high_score = global.score;
        }
        TextView average_score_text = (TextView) findViewById(R.id.number);
        TextView high_score_text = (TextView) findViewById(R.id.number);

        average_score_text.setText(Integer.toString(average));
        high_score_text.setText(Integer.toString(high_score));
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

                    if (current_number == 20) {
                        make_button_invisible(R.id.next_number);
                    }
                }

                break;
        }
    }
}
