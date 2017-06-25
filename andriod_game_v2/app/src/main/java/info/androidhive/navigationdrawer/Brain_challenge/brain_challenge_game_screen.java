package info.androidhive.navigationdrawer.Brain_challenge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.LevelActivity;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.activity.ScoreActivity;
import info.androidhive.navigationdrawer.other.GIFView;
import info.androidhive.navigationdrawer.other.JSONData;
import info.androidhive.navigationdrawer.other.SharedPreference;

import static info.androidhive.navigationdrawer.R.string.l;

/**
 * Created by chinmay on 21-May-17.
 */

public class brain_challenge_game_screen extends Activity{

    private static int SPLASH_TIME_OUT = 3;
    private static int FADE_OUT_BUTTON = 4000;
    final Random rnd = new Random();
    int level, time_to_diaplay, exp_round_counter;
    String str, game_name;
    Handler handler;
    ImageView image;
    GIFView gif;
    TextView text, brain_exp_remark, brain_main_ques;
    Pair<String, Boolean> exp;
    int button_id[] = {
            R.id.brain_button_1,
            R.id.brain_button_2,
            R.id.brain_button_3,
            R.id.brain_button_4};
    LinearLayout current_view;

    Button buttons[] = new Button[4];
    Bundle b1;

    //this is for the score page
    JSONData click_detail;
    int click_count,score;
    long start_time,end_time;

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
        b1 = getIntent().getExtras();
        level= Integer.parseInt(b1.getString("game_level"));    //receives the level number intended from level.java
        game_name = b1.getString("game_name");
        time_to_diaplay = level + SPLASH_TIME_OUT;
        exp_round_counter = Integer.MAX_VALUE;
        handler = new Handler();
        setContentView(R.layout.brain_challenge_game_screen);

        image = (ImageView) findViewById(R.id.brain_image);  //image to be displayed
        gif   = (GIFView) findViewById(R.id.brain_gif);
        text  = (TextView) findViewById(R.id.brain_text);
        brain_exp_remark = (TextView) findViewById(R.id.brain_exp_remark);
        brain_main_ques=(TextView)findViewById(R.id.brain_main_ques);

        click_detail = new JSONData("JA");
        click_count = 0;
        score = 0;

        for (int i = 0; i < 4; i++)
            buttons[i] = (Button) findViewById(button_id[i]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        start_time = System.currentTimeMillis();
        rounds();
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        Intent i = new Intent(brain_challenge_game_screen.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void rounds() {

        if (level <= 3)
        {
            str = game_name + "_" + Integer.toString(level) + "_" + Integer.toString((time_to_diaplay - level - SPLASH_TIME_OUT)/2+1);
            int drawable_id = getStringIdentifier(brain_challenge_game_screen.this, "drawable", str);

            if (level == 1)
            {
                image.setImageDrawable(getResources().getDrawable(drawable_id));
                current_view = (LinearLayout) findViewById(R.id.brain_l_image);
                current_view.setVisibility(View.VISIBLE);
                brain_main_ques.setText("FOR HOW MUCH TIME WAS THE FIGURE DISPLAYED?");
            }

            else
            {
                gif.setGifImageResource(drawable_id);
                current_view = (LinearLayout) findViewById(R.id.brain_l_gif);
                current_view.setVisibility(View.VISIBLE);
                brain_main_ques.setText("FOR HOW MUCH TIME WAS THE MOTION DISPLAYED?");
            }
        }
        else
        {
            current_view = (LinearLayout) findViewById(R.id.brain_l_text);
            current_view.setVisibility(View.VISIBLE);
            brain_main_ques.setText("FOR HOW MUCH TIME DID YOU ANSWER THE QUESTIONS??");
            exp_round_counter = (time_to_diaplay - level + 1)/2; // this will display 2,3,4,5,6
            brain_exp_remark.setText("Click atleast " + exp_round_counter + " correct answers");
            expression_round();
        }
        final int TIME_OUT = (time_to_diaplay + rnd.nextInt(7));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                current_view.setVisibility(View.GONE);
                int x = (rnd.nextInt(4) + 1) * -1;
                int time_in_button[] = {TIME_OUT + x + 1, TIME_OUT + x + 2, TIME_OUT + x + 3, TIME_OUT + x + 4};
                time_in_button = pickNrandom(time_in_button, 4);

                //setting up the animation on the text view
                final Animation out = new AlphaAnimation(1.0f, 0f);
                out.setDuration(FADE_OUT_BUTTON);

                if(level<=3 || exp_round_counter<=0)
                {
                    for (int i = 0; i < 4; i++) {

                        //initializing the buttons when question layout is set visible
                        buttons[i].setText(Integer.toString(time_in_button[i]));
                        buttons[i].setClickable(true);
                        buttons[i].setVisibility(View.VISIBLE);

                        final int finalI = i;
                        buttons[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String result = "W";
                                click_count++;
                                if(buttons[finalI].getText().equals(Integer.toString(TIME_OUT)))
                                {
                                    score++;
                                    result = "C";
                                }
                                click_detail.set_click_details(Long.toString(System.currentTimeMillis()/ 1000), result);
                                for(int i=0 ; i<4; i++ )
                                    buttons[i].setClickable(false);
                            }
                        });
                        buttons[i].startAnimation(out);
                    }
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // initialization in the questioning side
                        for (int i = 0; i < 4; i++) {
                            buttons[i].setVisibility(View.INVISIBLE);
                            buttons[i].setClickable(false);
                        }

                        if((time_to_diaplay - level - SPLASH_TIME_OUT)/2 +1 != 5)
                        {
                            time_to_diaplay += 2;
                            rounds();
                        }
                        else
                        {
                            //if there is no click then play the level again
                            if(click_count == 0)
                            {
                                level--;
                                Toast.makeText(brain_challenge_game_screen.this, "Since you didn't click any button, play the level again", Toast.LENGTH_LONG).show();
                            }

                            final Intent i = new Intent(brain_challenge_game_screen.this, (level <= 4)? LevelActivity.class : ScoreActivity.class);
                            b1.remove("game_level");

                            final JSONData data = new JSONData("JA", b1.getString("data"));
                            end_time = System.currentTimeMillis();
                            data.set_level_data(Integer.toString(score), Integer.toString(5-score), Integer.toString(score),Long.toString(end_time - start_time), click_detail.get_data_JA());

                            b1.remove("data");
                            i.putExtras(b1);
                            i.putExtra("data", data.get_data_string());
                            i.putExtra("game_level",Integer.toString(level + 1));

                            final SharedPreference sp = new SharedPreference(b1.getString("game_name"));
                            if (level > 4) {
                                sp.set_game_score(brain_challenge_game_screen.this, data.get_data_JA());
                                sp.async_response_modified(brain_challenge_game_screen.this, 10000, false);
                            }
                            final ProgressDialog progressDialog = new ProgressDialog(brain_challenge_game_screen.this, R.style.AppTheme_Dark_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage(getString((level <= 4) ? R.string.sd : R.string.cs));
                            progressDialog.show();

                            handler.postDelayed(new Runnable() {
                                public void run() {

                                    startActivity(i);
                                    if (level >4 && !sp.is_connected()) {
                                        sp.put_local_data(brain_challenge_game_screen.this, data.get_data_JA());
                                    }
                                    finish();
                                    progressDialog.dismiss();
                                }
                            }, (l <= 3) ? 1000 : 10000);
                        }

                    }
                },FADE_OUT_BUTTON);

            }
        }, TIME_OUT * 1000);
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
        brain_exp_correct.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        brain_exp_wrong.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

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
                        for(int i=0; i<4;i++) {
                            buttons[i].setVisibility(View.VISIBLE);
                            buttons[i].setText("");
//                            buttons[i].setBackgroundColor(getResources().getColor(R.color.iron));
                        }
                    }

                    if(exp_round_counter<=0)
                        brain_exp_remark.setText("Success!!");
                    else
                        brain_exp_remark.setText("Click atleast " + exp_round_counter + " correct answers");
                }

                x.setBackgroundColor(getResources().getColor((z == true) ? R.color.green : R.color.red));
                x.setClickable(false);
                y.setClickable(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        expression_round();
                    }
                }, 100);
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
