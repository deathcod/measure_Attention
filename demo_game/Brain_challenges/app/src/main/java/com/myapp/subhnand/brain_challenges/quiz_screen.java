package com.myapp.subhnand.brain_challenges;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by dell on 18/2/17.
 */
public class quiz_screen extends Activity {
    public static int counter = 0;
    public int actual_time=0;
    public int user_time=0;
    public double error_percentage=0.0;
    public static int[] array_of_actual_time = new int[10];
    public static int[] array_of_user_time = new int[10];
    public static double[] array_of_error_percentage = new double[10];
    int time_of_button1, time_of_button2, time_of_button3, time_of_button4;
    int timer;
    int level;
    int total_actual_time = 0;
    int total_user_time = 0;
    double total_error=0.0;
    Button buttonpane1, buttonpane2, buttonpane3, buttonpane4;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_screen);
        buttonpane1 = (Button) findViewById(R.id.buttonPane1);
        buttonpane2 = (Button) findViewById(R.id.buttonPane2);
        buttonpane3 = (Button) findViewById(R.id.buttonPane3);
        buttonpane4 = (Button) findViewById(R.id.buttonPane4);
        Bundle extras = getIntent().getExtras();
        timer = extras.getInt("time");
        level = extras.getInt("level");

        time_of_button1=(timer+4000)/1000;
        buttonpane1.setText(Integer.toString(time_of_button1));
        time_of_button2=timer/1000;
        buttonpane2.setText(Integer.toString(time_of_button2));
        time_of_button3=(timer-2000)/1000;
        buttonpane3.setText(Integer.toString(time_of_button3));
        time_of_button4=(timer+1000)/1000;
        buttonpane4.setText(Integer.toString(time_of_button4));
        if(quiz_screen.counter<10)
        {
            actual_time=timer/1000;
            quiz_screen.array_of_actual_time[quiz_screen.counter]=actual_time;
        }
        addListenerOnButton();

    }

    public void addListenerOnButton() {
        buttonpane1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_time=time_of_button1;
                error_percentage=((double)(actual_time-user_time)/actual_time)*100.0;
                quiz_screen.array_of_user_time[quiz_screen.counter]=time_of_button1;
                quiz_screen.array_of_error_percentage[quiz_screen.counter]=error_percentage;
                quiz_screen.counter=quiz_screen.counter+1;
                if(quiz_screen.counter<10) {
                    Intent n = new Intent(quiz_screen.this, game_screen.class);
                    n.putExtra("level", level);   //reflects the current level of execution(intending the level no.to game_screen page to select images accordingly)
                    startActivity(n);
                }
                else{
                    int k;
                    for(k=0;k<10;k++)
                    {
                        total_actual_time=total_actual_time+quiz_screen.array_of_actual_time[k];
                        total_user_time=total_user_time+quiz_screen.array_of_user_time[k];
                        total_error=total_error+quiz_screen.array_of_error_percentage[k];

                    }
                    quiz_screen.counter=0;

                    Intent j = new Intent(quiz_screen.this, result_screen.class);
                    j.putExtra("actual_time",total_actual_time);
                    j.putExtra("user_time",total_user_time);
                    j.putExtra("error_time",total_error);
                    startActivity(j);
                }

            }
        });

        buttonpane2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_time=time_of_button2;
                error_percentage=((double)(actual_time-user_time)/actual_time)*100.0;
                quiz_screen.array_of_user_time[quiz_screen.counter]=time_of_button2;
                quiz_screen.array_of_error_percentage[quiz_screen.counter]=error_percentage;
                quiz_screen.counter=quiz_screen.counter+1;
                if(quiz_screen.counter<10) {
                    Intent n = new Intent(quiz_screen.this, game_screen.class);
                    n.putExtra("level", level);   //reflects the current level of execution(intending the level no.to game_screen page to select images accordingly)
                    startActivity(n);
                }
                else{
                    int k;
                    for(k=0;k<10;k++)
                    {
                        total_actual_time=total_actual_time+quiz_screen.array_of_actual_time[k];
                        total_user_time=total_user_time+quiz_screen.array_of_user_time[k];
                        total_error=total_error+quiz_screen.array_of_error_percentage[k];

                    }
                    quiz_screen.counter=0;

                    Intent j = new Intent(quiz_screen.this, result_screen.class);
                    j.putExtra("actual_time",total_actual_time);
                    j.putExtra("user_time",total_user_time);
                    j.putExtra("error_time",total_error);
                    startActivity(j);
                }

            }
        });

        buttonpane3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_time=time_of_button3;
                error_percentage=((double)(actual_time-user_time)/actual_time)*100.0;
                quiz_screen.array_of_user_time[quiz_screen.counter]=time_of_button3;
                quiz_screen.array_of_error_percentage[quiz_screen.counter]=error_percentage;
                quiz_screen.counter=quiz_screen.counter+1;
                if(quiz_screen.counter<10) {
                    Intent n = new Intent(quiz_screen.this, game_screen.class);
                    n.putExtra("level", level);    //reflects the current level of execution(intending the level no.to game_screen page to select images accordingly)
                    startActivity(n);
                }
                else{
                    int k;
                    for(k=0;k<10;k++)
                    {
                        total_actual_time=total_actual_time+quiz_screen.array_of_actual_time[k];
                        total_user_time=total_user_time+quiz_screen.array_of_user_time[k];
                        total_error=total_error+quiz_screen.array_of_error_percentage[k];

                    }
                    quiz_screen.counter=0;

                    Intent j = new Intent(quiz_screen.this, result_screen.class);
                    j.putExtra("actual_time",total_actual_time);
                    j.putExtra("user_time",total_user_time);
                    j.putExtra("error_time",total_error);
                    startActivity(j);
                }

            }
        });

        buttonpane4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_time=time_of_button4;
                error_percentage=((double)(actual_time-user_time)/actual_time)*100.0;
                quiz_screen.array_of_user_time[quiz_screen.counter]=time_of_button4;
                quiz_screen.array_of_error_percentage[quiz_screen.counter]=error_percentage;
                quiz_screen.counter=quiz_screen.counter+1;
                if(quiz_screen.counter<10) {
                    Intent n = new Intent(quiz_screen.this, game_screen.class);
                    n.putExtra("level", level);     //reflects the current level of execution(intending the level no.to game_screen page to select images accordingly)
                    startActivity(n);
                }
                else{
                    int k;
                    for(k=0;k<10;k++)
                    {
                        total_actual_time=total_actual_time+quiz_screen.array_of_actual_time[k];
                        total_user_time=total_user_time+quiz_screen.array_of_user_time[k];
                        total_error=total_error+quiz_screen.array_of_error_percentage[k];

                    }
                    quiz_screen.counter=0;

                    Intent j = new Intent(quiz_screen.this, result_screen.class);
                    j.putExtra("actual_time",total_actual_time);
                    j.putExtra("user_time",total_user_time);
                    j.putExtra("error_time",total_error);
                    startActivity(j);
                }

            }
        });
    }
    }

