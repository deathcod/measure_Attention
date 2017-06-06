package com.myapp.subhnand.brain_challenges;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class game_screen extends Activity
{
    private static int SPLASH_TIME_OUT = 3000;
    final Random rnd = new Random();
    int level,time_to_diaplay;
    String str, game_name;
    Handler handler;
    ImageView image;
    GifImageView gif;
    TextView text;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle b1 = getIntent().getExtras();
        level= b1.getInt("level");    //receives the level number intended from level.java
        game_name = b1.getString("game_name");
        time_to_diaplay=rnd.nextInt(7000)+SPLASH_TIME_OUT;
        handler = new Handler();
        setContentView(R.layout.game_screen);

        image = (ImageView) findViewById(R.id.brain_image);  //image to be displayed
        gif   = (GifImageView) findViewById(R.id.brain_gif);
        text  = (TextView) findViewById(R.id.brain_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // I have 4 images named img0 to img3, so...

        /*
        *
        * each level has images of different format so using conditional statement we assign str the image names according to the format required for the particular level*/
        if (level <= 3)
        {
            str = game_name + "_" + Integer.toString(level) + "_" + Integer.toString(rnd.nextInt(5)+1);
            int drawable_id = getStringIdentifier(game_screen.this, "drawable", str);

            if (level == 1)
            {
                image.setImageDrawable(getResources().getDrawable(drawable_id));
                findViewById(R.id.brain_l_image).setVisibility(View.VISIBLE);
            }
            else
            {
                gif.setGifImageResource(drawable_id);
                findViewById(R.id.brain_l_gif).setVisibility(View.VISIBLE);
            }
        }
        else
        {
            findViewById(R.id.brain_l_text).setVisibility(View.VISIBLE);
            TextView brain_text = (TextView)findViewById(R.id.brain_text);
            Pair<String, Boolean> exp= expression_builder();

            brain_text.setText(exp.first);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*

                Intent s = new Intent(game_screen.this, quiz_screen.class);
                s.putExtra("time",time_to_diaplay);    // intend the time for which the image was displayed to quiz_screen.java
                s.putExtra("level",level);   // intend the level for which the image was displayed to quiz_screen.java
                startActivity(s);
                */
            }
        },time_to_diaplay);
    }

    private static int getStringIdentifier(Context context, String resource, String name) {
        return context.getResources().getIdentifier(name, resource, context.getPackageName());
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        finish();
    }

    private int calculate(int a,int b, String operator)
    {
        switch (operator)
        {
            case "+":
                return a+b;
            case "-":
                return a-b;
            case "*":
                return a*b;
            case "<":
                return (a<b)?1:0;
            case ">":
                return (a>b)?1:0;
            case "=":
                return (a==b)?1:0;
            case "<=":
                return (a<=b)?1:0;
            case ">=":
                return (a>=b)?1:0;
            case "!=":
                return (a!=b)?1:0;
            default:
                return 0;
        }
    }

    //Here the expression for the fourth level is build
    private Pair<String, Boolean> expression_builder()
    {
        String symbol[] = {"+", "-", "*","<",">","=","<=", ">=", "!="};
        int operand[] = {rnd.nextInt(100)+1, rnd.nextInt(100)+1, rnd.nextInt(100)+1, rnd.nextInt(100)+1};
        int operator1 = rnd.nextInt(3);
        int operator2 = rnd.nextInt(3);
        int comparator = rnd.nextInt(6) + 3;

        Boolean answer = (calculate(calculate(operand[0],operand[1],symbol[operator1]),calculate(operand[2],operand[3],symbol[operator2]),symbol[comparator]) == 1)?true : false;
        String expression = Integer.toString(operand[0]) + symbol[operator1] + Integer.toString(operand[1]) + symbol[comparator] + Integer.toString(operand[2]) + symbol[operator2] + Integer.toString(operand[3]);
        Pair<String, Boolean> x =new Pair(expression, answer);
        return x;
    }
}
