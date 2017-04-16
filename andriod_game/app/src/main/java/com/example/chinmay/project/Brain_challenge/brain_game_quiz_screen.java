package com.example.chinmay.project.Brain_challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chinmay.project.R;

/**
 * Created by dell on 18/2/17.
 */
public class brain_game_quiz_screen extends Activity {
    public static int counter = 0;
    public static int[] array_of_actual_time = new int[10];
    public static int[] array_of_user_time = new int[10];
    public static double[] array_of_error_percentage = new double[10];
    public int actual_time = 0;
    public int user_time = 0;
    public double error_percentage = 0.0;
    int time_of_button1, time_of_button2, time_of_button3, time_of_button4;
    int timer;
    int total_actual_time = 0;
    int total_user_time = 0;
    double total_error = 0.0;
    Button buttonpane1, buttonpane2, buttonpane3, buttonpane4;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brain_game_quiz_screen);
        buttonpane1 = (Button) findViewById(R.id.buttonPane1);
        buttonpane2 = (Button) findViewById(R.id.buttonPane2);
        buttonpane3 = (Button) findViewById(R.id.buttonPane3);
        buttonpane4 = (Button) findViewById(R.id.buttonPane4);
        Bundle extras = getIntent().getExtras();
        timer = extras.getInt("time");

        time_of_button1 = (timer + 4000) / 1000;
        buttonpane1.setText(String.format(Integer.toString(time_of_button1)));
        time_of_button2 = timer / 1000;
        buttonpane2.setText(String.format(Integer.toString(time_of_button2)));
        time_of_button3 = (timer - 2000) / 1000;
        buttonpane3.setText(String.format(Integer.toString(time_of_button3)));
        time_of_button4 = (timer + 1000) / 1000;
        buttonpane4.setText(String.format(Integer.toString(time_of_button4)));
        if (brain_game_quiz_screen.counter < 10) {
            actual_time = timer / 1000;
            brain_game_quiz_screen.array_of_actual_time[brain_game_quiz_screen.counter] = actual_time;
        }
        addListenerOnButton();

    }

    public void addListenerOnButton() {
        buttonpane1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_time = time_of_button1;
                error_percentage = ((double) (actual_time - user_time) / actual_time) * 100.0;
                brain_game_quiz_screen.array_of_user_time[brain_game_quiz_screen.counter] = time_of_button1;
                brain_game_quiz_screen.array_of_error_percentage[brain_game_quiz_screen.counter] = error_percentage;
                brain_game_quiz_screen.counter = brain_game_quiz_screen.counter + 1;
                if (brain_game_quiz_screen.counter < 10) {
                    Intent n = new Intent(brain_game_quiz_screen.this, brain_game_game_screen.class);
                    startActivity(n);
                } else {
                    int k;
                    for (k = 0; k < 10; k++) {
                        total_actual_time = total_actual_time + brain_game_quiz_screen.array_of_actual_time[k];
                        total_user_time = total_user_time + brain_game_quiz_screen.array_of_user_time[k];
                        total_error = total_error + brain_game_quiz_screen.array_of_error_percentage[k];

                    }
                    brain_game_quiz_screen.counter = 0;

                    Intent j = new Intent(brain_game_quiz_screen.this, brain_game_result_screen.class);
                    j.putExtra("actual_time", total_actual_time);
                    j.putExtra("user_time", total_user_time);
                    j.putExtra("error_time", total_error);
                    startActivity(j);
                }

            }
        });

        buttonpane2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_time = time_of_button2;
                error_percentage = ((double) (actual_time - user_time) / actual_time) * 100.0;
                brain_game_quiz_screen.array_of_user_time[brain_game_quiz_screen.counter] = time_of_button2;
                brain_game_quiz_screen.array_of_error_percentage[brain_game_quiz_screen.counter] = error_percentage;
                brain_game_quiz_screen.counter = brain_game_quiz_screen.counter + 1;
                if (brain_game_quiz_screen.counter < 10) {
                    Intent n = new Intent(brain_game_quiz_screen.this, brain_game_game_screen.class);
                    startActivity(n);
                } else {
                    int k;
                    for (k = 0; k < 10; k++) {
                        total_actual_time = total_actual_time + brain_game_quiz_screen.array_of_actual_time[k];
                        total_user_time = total_user_time + brain_game_quiz_screen.array_of_user_time[k];
                        total_error = total_error + brain_game_quiz_screen.array_of_error_percentage[k];

                    }
                    brain_game_quiz_screen.counter = 0;

                    Intent j = new Intent(brain_game_quiz_screen.this, brain_game_result_screen.class);
                    j.putExtra("actual_time", total_actual_time);
                    j.putExtra("user_time", total_user_time);
                    j.putExtra("error_time", total_error);
                    startActivity(j);
                }

            }
        });

        buttonpane3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_time = time_of_button3;
                error_percentage = ((double) (actual_time - user_time) / actual_time) * 100.0;
                brain_game_quiz_screen.array_of_user_time[brain_game_quiz_screen.counter] = time_of_button3;
                brain_game_quiz_screen.array_of_error_percentage[brain_game_quiz_screen.counter] = error_percentage;
                brain_game_quiz_screen.counter = brain_game_quiz_screen.counter + 1;
                if (brain_game_quiz_screen.counter < 10) {
                    Intent n = new Intent(brain_game_quiz_screen.this, brain_game_game_screen.class);
                    startActivity(n);
                } else {
                    int k;
                    for (k = 0; k < 10; k++) {
                        total_actual_time = total_actual_time + brain_game_quiz_screen.array_of_actual_time[k];
                        total_user_time = total_user_time + brain_game_quiz_screen.array_of_user_time[k];
                        total_error = total_error + brain_game_quiz_screen.array_of_error_percentage[k];

                    }
                    brain_game_quiz_screen.counter = 0;

                    Intent j = new Intent(brain_game_quiz_screen.this, brain_game_result_screen.class);
                    j.putExtra("actual_time", total_actual_time);
                    j.putExtra("user_time", total_user_time);
                    j.putExtra("error_time", total_error);
                    startActivity(j);
                }

            }
        });

        buttonpane4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_time = time_of_button4;
                error_percentage = ((double) (actual_time - user_time) / actual_time) * 100.0;
                brain_game_quiz_screen.array_of_user_time[brain_game_quiz_screen.counter] = time_of_button4;
                brain_game_quiz_screen.array_of_error_percentage[brain_game_quiz_screen.counter] = error_percentage;
                brain_game_quiz_screen.counter = brain_game_quiz_screen.counter + 1;
                if (brain_game_quiz_screen.counter < 10) {
                    Intent n = new Intent(brain_game_quiz_screen.this, brain_game_game_screen.class);
                    startActivity(n);
                } else {
                    int k;
                    for (k = 0; k < 10; k++) {
                        total_actual_time = total_actual_time + brain_game_quiz_screen.array_of_actual_time[k];
                        total_user_time = total_user_time + brain_game_quiz_screen.array_of_user_time[k];
                        total_error = total_error + brain_game_quiz_screen.array_of_error_percentage[k];

                    }
                    brain_game_quiz_screen.counter = 0;

                    Intent j = new Intent(brain_game_quiz_screen.this, brain_game_result_screen.class);
                    j.putExtra("actual_time", total_actual_time);
                    j.putExtra("user_time", total_user_time);
                    j.putExtra("error_time", total_error);
                    startActivity(j);
                }

            }
        });
    }
}

