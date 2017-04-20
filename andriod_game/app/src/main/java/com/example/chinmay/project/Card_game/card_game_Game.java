package com.example.chinmay.project.Card_game;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chinmay.project.R;
import com.example.chinmay.project.SharedPreference;

public class card_game_Game extends Activity {
    TextView level, timer, tScore;
    ImageView img[] = new ImageView[5];
    ImageButton a[] = new ImageButton[10];
    int l;
    int stopCond, score = 0;
    ImageButton back, forward;
    int times;
    int buttonNum[] = {R.id.a1, R.id.a2, R.id.a3, R.id.a4, R.id.a5, R.id.a6, R.id.a7, R.id.a8, R.id.a9, R.id.a10,};
    int imageNum[] = {R.id.q1, R.id.q2, R.id.q3, R.id.q4, R.id.q5};
    int random_no_array[] = new int[10];
    int save_index[] = new int[5];
    Chronometer stopWatch;
    long startTime, countUp;
    //char card_seq[] = {'K', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'D', 'J', 'Q'};
    private int all_cards[] = {R.drawable.card_game_kingofclubs, R.drawable.card_game_oneofclubs, R.drawable.card_game_twoofclubs, R.drawable.card_game_threeofclubs, R.drawable.card_game_fourofclubs, R.drawable.card_game_fiveofclubs, R.drawable.card_game_sixofclubs, R.drawable.card_game_sevenofclubs, R.drawable.card_game_eightofclubs, R.drawable.card_game_nineofclubs, R.drawable.card_game_tenofclubs, R.drawable.card_game_jackofclubs, R.drawable.card_game_queenofclubs,
            R.drawable.card_game_kingofdiamonds, R.drawable.card_game_oneofdiamonds, R.drawable.card_game_twoofdiamonds, R.drawable.card_game_threeofdiamonds, R.drawable.card_game_fourofdiamonds, R.drawable.card_game_fiveofdiamonds, R.drawable.card_game_sixofdiamonds, R.drawable.card_game_sevenofdiamonds, R.drawable.card_game_eightofdiamonds, R.drawable.card_game_nineofdiamonds, R.drawable.card_game_tenofdiamonds, R.drawable.card_game_jackofdiamonds, R.drawable.card_game_queenofdiamonds,
            R.drawable.card_game_kingofhearts, R.drawable.card_game_oneofhearts, R.drawable.card_game_twoofhearts, R.drawable.card_game_threeofhearts, R.drawable.card_game_fourofhearts, R.drawable.card_game_fiveofhearts, R.drawable.card_game_sixofhearts, R.drawable.card_game_sevenofhearts, R.drawable.card_game_eightofhearts, R.drawable.card_game_nineofhearts, R.drawable.card_game_tenofhearts, R.drawable.card_game_jackofhearts, R.drawable.card_game_queenofhearts,
            R.drawable.card_game_kingofspades, R.drawable.card_game_oneofspades, R.drawable.card_game_twoofspades, R.drawable.card_game_threeofspades, R.drawable.card_game_fourofspades, R.drawable.card_game_fiveofspades, R.drawable.card_game_sixofspades, R.drawable.card_game_sevenofspades, R.drawable.card_game_eightofspades, R.drawable.card_game_nineofspades, R.drawable.card_game_tenofspades, R.drawable.card_game_jackofspades, R.drawable.card_game_queenofspades};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.card_game_game_screen);
        level = (TextView) findViewById(R.id.txtLevel);
        timer = (TextView) findViewById(R.id.txtTimer);
        tScore = (TextView) findViewById(R.id.txtScore);
        back = (ImageButton) findViewById(R.id.imageBack);
        back.setVisibility(View.INVISIBLE);
        forward = (ImageButton) findViewById(R.id.imageForward);
        forward.setVisibility(View.INVISIBLE);
        stopWatch = (Chronometer) findViewById(R.id.chronometer);
        stopWatch.setVisibility(View.INVISIBLE);

        for (int i = 0; i < 5; i++) {
            img[i] = (ImageView) findViewById(imageNum[i]);
        }
        for (int i = 0; i < 10; i++) {
            a[i] = (ImageButton) findViewById(buttonNum[i]);
        }

        // SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        Bundle b1 = getIntent().getExtras();
        l = b1.getInt("level");
        times = b1.getInt("times");
        level.setText("Level: " + l);


        CountDownTimer cT = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished / 60000);
                int va = (int) ((millisUntilFinished % 60000) / 1000);
                timer.setText("" + v + ":" + String.format("%02d", va));
            }

            public void onFinish() {
                for (int i = 0; i < 5; i++) {
                    img[i].setImageResource(R.drawable.card_game_blank);
                }
                for (int i = 0; i < 10; i++) {
                    a[i].setVisibility(View.VISIBLE);
                }
                //Ekhane i/p dilam
                startTime = SystemClock.elapsedRealtime();
                stopWatch.setBase(SystemClock.elapsedRealtime());
                stopWatch.setVisibility(View.VISIBLE);
                stopWatch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer arg0) {
                        countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;
                        String asText = (countUp / 60) + ":" + (countUp % 60);
                        timer.setText(asText);
                        timer.setVisibility(View.INVISIBLE);
                    }
                });
                stopWatch.start();
            }
        };
        cT.start();
        change();
        addListenerOnButton();

    }

    public void change() {


        stopCond = 0;
        score = 0;
        int suite[] = new int[4], r;

        for (int i = 0; i < l; i++) {
            while (true) {
                boolean repeat = false;
                r = (int) (Math.random() * 4);
                for (int j = 0; j < i; j++) {
                    if (suite[j] == r) {
                        repeat = true;
                    }
                }
                if (!repeat) {
                    suite[i] = r;
                    break;
                }
            }
        }

        int ra[] = new int[10];

        for (int i = 0; i < 10; i++) {
            while (true) {
                boolean repeat = false;
                r = (int) (Math.random() * 13);
                for (int j = 0; j < i; j++) {
                    if (ra[j] == r) {
                        repeat = true;
                    }
                }
                if (!repeat) {
                    ra[i] = r;
                    break;
                }
            }
            int index = (int) (Math.random() * l);
            random_no_array[i] = suite[index] * 13 + ra[i];
        }

        for (int i = 0; i < 10; i++) {
            a[i].setVisibility(View.INVISIBLE);
            a[i].setImageResource(all_cards[random_no_array[i]]);
        }

        for (int i = 0; i < 5; i++) {
            while (true) {
                boolean repeat = false;
                r = (int) (Math.random() * 10);
                for (int j = 0; j < i; j++) {
                    if (ra[j] == r) {
                        repeat = true;
                    }
                }
                if (!repeat) {
                    ra[i] = r;
                    break;
                }
            }
            save_index[i] = ra[i];
            img[i].setImageResource(all_cards[random_no_array[ra[i]]]);
        }
    }


    public void addListenerOnButton() {
        for (int i = 0; i < 10; i++) {
            final int k = i;
            a[k].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    for (int j = 0; j < 5; j++) {
                        a[k].setEnabled(false);
                        if (k == save_index[j]) {
                            img[j].setImageResource(all_cards[random_no_array[save_index[j]]]);
                            score++;
                        }
                    }
                    tScore.setText(score + "");
                    stopCond++;
                    if (stopCond == 5) {
                        stopWatch.stop();
                        for (int j = 0; j < 10; j++)
                            a[j].setEnabled(false);
                        if (score == 5)
                            Toast.makeText(getApplicationContext(), "Congrates!! time taken: " +Long.toString(SystemClock.elapsedRealtime()-startTime) +"ms", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Turn Over!! time taken: " + Long.toString(SystemClock.elapsedRealtime()-startTime) +"ms", Toast.LENGTH_SHORT).show();
                        String remark = new SharedPreference("card").set_card_game_score(card_game_Game.this, Integer.toString(score), Long.toString(SystemClock.elapsedRealtime()-startTime), Integer.toString(l));
                        if (remark != null)                            Toast.makeText(card_game_Game.this, remark, Toast.LENGTH_SHORT).show();
                        stopWatch.setText(String.format("%s:%s", String.format("%02d", 0), String.format("%02d", 0)));
                        back.setVisibility(View.VISIBLE);
                        forward.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(card_game_Game.this, card_game_LevelChooser.class);
                i.putExtra("times", times);
                startActivity(i);
                finish();
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(card_game_Game.this, card_game_LevelChooser.class);
                i.putExtra("times", 0);
                startActivity(i);
                finish();
            }
        });
    }
}
