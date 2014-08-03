package com.example.kaleb_000.dartdojo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.R.*;

import com.example.kaleb_000.dartdojo.MyGlobals;

public class Solitaire extends Activity {


    MyGlobals update_score = new MyGlobals();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solitaire);
        TextView textView = (TextView) findViewById(R.id.Score);

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
                textView.setText(update_score.dartthrow(1));
                break;
            case R.id.button2:
                textView.setText(update_score.dartthrow(1));
                break;
            case R.id.button3:
                textView.setText(update_score.dartthrow(1));
                break;
        }
    }


}
