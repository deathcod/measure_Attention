package com.myapp.subhnand.brain_challenges;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by dell on 18/2/17.
 */
public class result_screen extends Activity
{
    TextView t1,t2,t3;
    Button home;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen);
        t1 = (TextView) findViewById(R.id.expected_time);
        t2 = (TextView) findViewById(R.id.your_time);
        t3 = (TextView) findViewById(R.id.error_percentage);


        Bundle extras = getIntent().getExtras();
        int actual_timer=extras.getInt("actual_time");
        int user_timer=extras.getInt("user_time");
        double error=extras.getDouble("error_time");

        t1.setText(Integer.toString(actual_timer));
        t2.setText(Integer.toString(user_timer));
        t3.setText((Double.toString(Math.abs(Math.round(error/10.0))))+"%");
        home = (Button) findViewById(R.id.home);
        addListenerOnButton();




    }
    public void addListenerOnButton() {
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(result_screen.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}