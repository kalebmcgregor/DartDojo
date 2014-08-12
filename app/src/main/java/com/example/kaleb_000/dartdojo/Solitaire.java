package com.example.kaleb_000.dartdojo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.kaleb_000.dartdojo.Number;

import org.w3c.dom.Text;

public class Solitaire extends Activity {


    MyGlobals global = new MyGlobals();
    Number[] numbers = new Number[21];
    int current_number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solitaire);

        //Fill the dart Array
        for(int i=0; i< numbers.length; i++){
            numbers[i] = new Number();
            numbers[i].set_number(i+1);
        }
    }

    public void button_visibility_toggle(int visible, int invisible) {
        Button button_visible = (Button) findViewById(visible);
        button_visible.setVisibility(View.VISIBLE);
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

    //Function used when next_number button and previous_number button is pressed
    public void next_previous_button_pressed (View view) {
        //Set int button_id to the id of the button that was pressed
        int button_id = view.getId();
        TextView textView = (TextView) findViewById(R.id.number);

        //Select the right button and do actions depending on which dart was selected
        switch(button_id) {
            case R.id.previous_number:
                if (current_number >= 1) {
                    current_number--;
                    set_dart(numbers[current_number]);
                    textView.setText(numbers[current_number].get_number());
                }
                break;
            case R.id.next_number:
                if (current_number <= 19) {
                    dart_default_visibility();
                    current_number++;
                    set_dart(numbers[current_number]);
                    textView.setText(numbers[current_number].get_number());
                }
                break;
        }
    }
}
