package com.example.chinmay.project.Stroop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chinmay.project.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by chinmay on 27-Jan-17.
 */

public class stroop_game_screen extends Activity {

    Button buttonpane1,buttonpane2,buttonpane3,buttonpane4;
    TextView text;
    String color[][]= {
        {"black","#1e1e1e"},
        {"blue","#FF33B5E5"},
        {"green","#FF99CC00"},
        {"orange","#FFFFBB33"},
        {"yellow","#ffff00"},
        {"red","#FFFF4444"},
        {"Pink","#FFC0CB"},
        {"White","#FFFFFF"},
        {"Violet","#EE82EE"},
        {"Brown","#A52A2A"},
    };
    String msg;
    int a[] = new int[color.length];
    int randomPick_colors[];
    int randomPick_color_text;
    int randomPick_color_color;
    int correct = 0;
    int wrong = 0;
    int count = 0;
    long now,then;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stroop_game_screen);
        buttonpane1 = (Button)findViewById(R.id.buttonPane1);
        buttonpane2 = (Button)findViewById(R.id.buttonPane2);
        buttonpane3 = (Button)findViewById(R.id.buttonPane3);
        buttonpane4 = (Button)findViewById(R.id.buttonPane4);
        text = (TextView) findViewById(R.id.text);

        for(int i=0;i<color.length;i++)
            a[i]=i;

        msg = "Android : ";
    }

    @Override
    protected void onStart() {

        super.onStart();
        now = System.currentTimeMillis();
        change();
    }

    public void change()
    {
        if (count ==10) {
            buttonpane1.setEnabled(false);
            buttonpane2.setEnabled(false);
            buttonpane3.setEnabled(false);
            buttonpane4.setEnabled(false);
            then = System.currentTimeMillis();
            long difftime = (long) (then - now);
            Intent i = new Intent(stroop_game_screen.this, stroop_score_screen.class);
            i.putExtra("time", difftime);
            i.putExtra("correct", correct);
            i.putExtra("wrong", wrong);
            startActivity(i);
            finish();
            return;
        }

        randomPick_colors = pickNrandom(a, 4);
        randomPick_color_text = pickNrandom(randomPick_colors, 1)[0];

        buttonpane1.setText(color[randomPick_colors[0]][0]);
        buttonpane2.setText(color[randomPick_colors[1]][0]);
        buttonpane3.setText(color[randomPick_colors[2]][0]);
        buttonpane4.setText(color[randomPick_colors[3]][0]);
        if(count <=3)
        {
            randomPick_color_color = randomPick_color_text;
        }
        else
        {
            do{
                randomPick_color_color = pickNrandom(randomPick_colors, 1)[0];
            }while(randomPick_color_color == randomPick_color_text);
        }
        text.setText(color[randomPick_color_text][0]);
        text.setTextColor(Color.parseColor(color[randomPick_color_color][1]));
        addListenerOnButton();
        count++;
    }
    public void addListenerOnButton()
    {
        buttonpane1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (randomPick_colors[0] == randomPick_color_color)
                    correct++;
                else
                    wrong++;
                change();
            }
        });
        buttonpane2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (randomPick_colors[1] == randomPick_color_color)
                    correct++;
                else
                    wrong++;
                change();
            }
        });
        buttonpane3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (randomPick_colors[2] == randomPick_color_color)
                    correct++;
                else
                    wrong++;
                change();
            }
        });
        buttonpane4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (randomPick_colors[3] == randomPick_color_color)
                    correct++;
                else
                    wrong++;
                change();
            }
        });
        Log.d(msg,Integer.toString(correct));
        Log.d(msg,Integer.toString(wrong));
    }

    public static int[] pickNrandom(int[] array, int n)
    {
        List<Integer> list = new ArrayList<Integer>(array.length);
        for(int i:array)
            list.add(i);
        Collections.shuffle(list);

        int[] answer = new int[n];
        for (int i=0;i<n;i++)
            answer[i] = list.get(i);

        return answer;
    }
}
