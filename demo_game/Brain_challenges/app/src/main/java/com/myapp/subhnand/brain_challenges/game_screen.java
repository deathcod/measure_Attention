package com.myapp.subhnand.brain_challenges;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class game_screen extends Activity
{
    private static int SPLASH_TIME_OUT = 3;
    private static int FADE_OUT_BUTTON = 3000;
    final Random rnd = new Random();
    int level, time_to_diaplay, exp_round_counter;
    String str, game_name;
    Handler handler;
    ImageView image;
    GifImageView gif;
    TextView text, brain_exp_remark;
    Pair<String, Boolean> exp;
    int button_id[] = {
            R.id.brain_button_1,
            R.id.brain_button_2,
            R.id.brain_button_3,
            R.id.brain_button_4};
    LinearLayout current_view;

    Button buttons[] = new Button[4];

    private static int getStringIdentifier(Context context, String resource, String name) {
        return context.getResources().getIdentifier(name, resource, context.getPackageName());
    }

    private static int[] pickNrandom(int[] array, int n) {
        List<Integer> list = new ArrayList<Integer>(array.length);
        for (int i : array)
            list.add(i);
        Collections.shuffle(list);

        int[] answer = new int[n];
        for (int i = 0; i < n; i++)
            answer[i] = list.get(i);

        return answer;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle b1 = getIntent().getExtras();
        level= b1.getInt("level");    //receives the level number intended from level.java
        game_name = b1.getString("game_name");
        time_to_diaplay = level + SPLASH_TIME_OUT;
        exp_round_counter = Integer.MAX_VALUE;
        handler = new Handler();
        setContentView(R.layout.game_screen);

        image = (ImageView) findViewById(R.id.brain_image);  //image to be displayed
        gif   = (GifImageView) findViewById(R.id.brain_gif);
        text  = (TextView) findViewById(R.id.brain_text);
        brain_exp_remark = (TextView) findViewById(R.id.brain_exp_remark);
        for (int i = 0; i < 4; i++)
            buttons[i] = (Button) findViewById(button_id[i]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rounds();
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        finish();
    }

    private void rounds() {

        // initialization in the questioning side
        for (int i = 0; i < 4; i++) {
            buttons[i].setText("");
            buttons[i].setClickable(false);
        }

        if (level <= 3)
        {
            str = game_name + "_" + Integer.toString(level) + "_" + Integer.toString((time_to_diaplay - level - SPLASH_TIME_OUT)/2+1);
            int drawable_id = getStringIdentifier(game_screen.this, "drawable", str);

            if (level == 1)
            {
                image.setImageDrawable(getResources().getDrawable(drawable_id));
                current_view = (LinearLayout) findViewById(R.id.brain_l_image);
                current_view.setVisibility(View.VISIBLE);
            }
            else
            {
                gif.setGifImageResource(drawable_id);
                current_view = (LinearLayout) findViewById(R.id.brain_l_gif);
                current_view.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            current_view = (LinearLayout) findViewById(R.id.brain_l_text);
            current_view.setVisibility(View.VISIBLE);
            exp_round_counter = (time_to_diaplay - level + 1)/2; // this will display 2,3,4,5,6
            brain_exp_remark.setText("Click atleast " + exp_round_counter + " correct answer");
            for (int i=0 ;i<4;i++) {
                buttons[i].setEnabled(false);
                buttons[i].setBackgroundColor(getResources().getColor(R.color.monsoon));
            }
            expression_round();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                current_view.setVisibility(View.GONE);
                int x = (rnd.nextInt(4) + 1) * -1;
                int time_in_button[] = {time_to_diaplay + x + 1, time_to_diaplay + x + 2, time_to_diaplay + x + 3, time_to_diaplay + x + 4};
                time_in_button = pickNrandom(time_in_button, 4);

                //setting up the animation on the text view
                final Animation out = new AlphaAnimation(1.0f, 0.4f);
                out.setDuration(FADE_OUT_BUTTON);

                for (int i = 0; i < 4; i++) {
                    buttons[i].setText(Integer.toString(time_in_button[i]));
                    buttons[i].setClickable(true);
                    buttons[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    buttons[i].startAnimation(out);
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        time_to_diaplay += 2;
                        rounds();
                        }
                    },FADE_OUT_BUTTON);

                /*
                Intent s = new Intent(game_screen.this, quiz_screen.class);
                s.putExtra("time",time_to_diaplay);    // intend the time for which the image was displayed to quiz_screen.java
                s.putExtra("level",level);   // intend the level for which the image was displayed to quiz_screen.java
                startActivity(s);
                */
            }
        }, (time_to_diaplay + rnd.nextInt(7)) * 1000);
    }

    // this is for the fourth and fifth round
    private void expression_round() {
        //initialization of expression round
        TextView brain_text = (TextView) findViewById(R.id.brain_text);
        final Button brain_exp_correct = (Button) findViewById(R.id.brain_exp_correct);
        final Button brain_exp_wrong = (Button) findViewById(R.id.brain_exp_wrong);

        //game screen initialization
        exp = expression_builder();
        brain_text.setText(exp.first);
        brain_exp_correct.setClickable(true);
        brain_exp_wrong.setClickable(true);
        brain_exp_correct.setBackgroundColor(getResources().getColor(R.color.white));
        brain_exp_wrong.setBackgroundColor(getResources().getColor(R.color.white));

        //setting up the buttons
        expression_round_button(brain_exp_correct, brain_exp_wrong, exp.second == true);
        expression_round_button(brain_exp_wrong, brain_exp_correct, exp.second == false);
    }

    // To change anything in the button in the expression round do here
    private void expression_round_button(final Button x, final Button y, final boolean z) {
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //incrementing the counter value if correct value is clicked
                if(z == true)
                {
                    exp_round_counter--;
                    if(exp_round_counter == 0)
                    {
                        for(int i=0; i<4;i++)
                        {
                            buttons[i].setEnabled(true);
                            buttons[i].setBackgroundColor(getResources().getColor(R.color.iron));
                        }
                        brain_exp_remark.setText("Success!!");
                    }
                    else
                        brain_exp_remark.setText("Click atleast " + exp_round_counter + " correct answer");
                }

                x.setBackgroundColor(getResources().getColor((z == true) ? R.color.green : R.color.red));
                x.setClickable(false);
                y.setClickable(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        expression_round();
                    }
                }, 200);
            }
        });
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
            default:
                return 0;
        }
    }

    //Here the expression for the fourth and fifth level is build
    private Pair<String, Boolean> expression_builder()
    {
        String symbol[] = {"+", "-", "*", "<", ">", "=", "<=", ">="};
        int value = 10;
        int operand[] = {rnd.nextInt(value) + 1, rnd.nextInt(value) + 1, rnd.nextInt(value) + 1, rnd.nextInt(value) + 1};
        int operator1 = rnd.nextInt(3);
        int operator2 = rnd.nextInt(3);
        int comparator = ((level == 4) ? rnd.nextInt(3) : rnd.nextInt(5)) + 3;

        Boolean answer = false;
        String expression = "";

        int exp_pattern = 0;
        int internal_round = (time_to_diaplay - level - SPLASH_TIME_OUT)/2 +1;

        if(level == 4)
        {
            if(internal_round == 3)
                exp_pattern = rnd.nextInt(2) == 0? 0: 1;
            else if(internal_round == 4)
                exp_pattern = rnd.nextInt(2) == 0? 0: 2;
            else if(internal_round == 5)
                exp_pattern = rnd.nextInt(2) == 0? 0: 3;
        }

        if(level == 5)
        {
            if(internal_round <=2)
                exp_pattern = internal_round;
            else if(internal_round == 3)
                exp_pattern = 1 + rnd.nextInt(3);
            else if(internal_round == 4)
                exp_pattern = 2 + rnd.nextInt(2);
            else if(internal_round == 5)
                exp_pattern = rnd.nextInt(4);
        }

        switch (exp_pattern) {
            case 0:
                // pattern X < Y
                answer = calculate(operand[0], operand[1], symbol[comparator]) == 1;
                expression = Integer.toString(operand[0]) + symbol[comparator] + Integer.toString(operand[1]);
                break;

            case 1:
                // pattern X + Y < Z
                answer = calculate(calculate(operand[0], operand[1], symbol[operator1]), operand[2], symbol[comparator]) == 1;
                expression = Integer.toString(operand[0]) + symbol[operator1] + Integer.toString(operand[1]) + symbol[comparator] + Integer.toString(operand[2]);
                break;

            case 2:
                // pattern X < Y + Z
                answer = calculate(operand[0], calculate(operand[1], operand[2], symbol[operator1]), symbol[comparator]) == 1;
                expression = Integer.toString(operand[0]) + symbol[comparator] + Integer.toString(operand[1]) + symbol[operator1] + Integer.toString(operand[2]);
                break;

            case 3:
                // pattern W + X < Y + Z
                answer = calculate(calculate(operand[0], operand[1], symbol[operator1]), calculate(operand[2], operand[3], symbol[operator2]), symbol[comparator]) == 1;
                expression = Integer.toString(operand[0]) + symbol[operator1] + Integer.toString(operand[1]) + symbol[comparator] + Integer.toString(operand[2]) + symbol[operator2] + Integer.toString(operand[3]);
                break;
        }

        Pair<String, Boolean> x =new Pair(expression, answer);
        return x;
    }
}