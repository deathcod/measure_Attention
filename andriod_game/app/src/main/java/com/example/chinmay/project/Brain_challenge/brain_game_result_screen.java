package com.example.chinmay.project.Brain_challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chinmay.project.MainActivity;
import com.example.chinmay.project.R;

/**
 * Created by dell on 18/2/17.
 */
public class brain_game_result_screen extends Activity {
    String ACTUAL_TIME, USER_TIME, ERROR_PERCENTAGE;
    TextView t1, t2, t3;
    Button home;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brain_game_result_screen);
        t1 = (TextView) findViewById(R.id.expected_time);
        t2 = (TextView) findViewById(R.id.your_time);
        t3 = (TextView) findViewById(R.id.error_percentage);


        Bundle extras = getIntent().getExtras();
        int actual_timer = extras.getInt("actual_time");
        int user_timer = extras.getInt("user_time");
        double error = extras.getDouble("error_time");


        ACTUAL_TIME = String.format(Integer.toString(actual_timer));
        USER_TIME = String.format(Integer.toString(user_timer));
        ERROR_PERCENTAGE = String.format((Double.toString(Math.abs(Math.round(error / 10.0))))) + "%";


        t1.setText(ACTUAL_TIME);
        t2.setText(USER_TIME);
        t3.setText(ERROR_PERCENTAGE);
        home = (Button) findViewById(R.id.home);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(brain_game_result_screen.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}