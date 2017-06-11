package info.androidhive.navigationdrawer.Card_game;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.LevelActivity;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.activity.ScoreActivity;
import info.androidhive.navigationdrawer.other.JSONData;
import info.androidhive.navigationdrawer.other.SharedPreference;

public class card_game_game_screen extends Activity {
    TextView level, timer, tScore;
    ImageView img[] = new ImageView[5];
    ImageButton a[] = new ImageButton[10];
    int stopCond, score = 0;
    int buttonNum[] = {R.id.a1, R.id.a2, R.id.a3, R.id.a4, R.id.a5, R.id.a6, R.id.a7, R.id.a8, R.id.a9, R.id.a10,};
    int imageNum[] = {R.id.q1, R.id.q2, R.id.q3, R.id.q4, R.id.q5};
    int random_no_array[] = new int[10];
    int l = 0; // level number
    int save_index[] = new int[5];
    Chronometer stopWatch;
    long startTime, countUp;
    Bundle b1 = null; // bundle;
    JSONData click_detail;
    Handler handler;
    //char card_seq[] = {'K', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'D', 'J', 'Q'};
    private int all_cards[] = {R.drawable.card_game_kingofclubs, R.drawable.card_game_oneofclubs, R.drawable.card_game_twoofclubs, R.drawable.card_game_threeofclubs, R.drawable.card_game_fourofclubs, R.drawable.card_game_fiveofclubs, R.drawable.card_game_sixofclubs, R.drawable.card_game_sevenofclubs, R.drawable.card_game_eightofclubs, R.drawable.card_game_nineofclubs, R.drawable.card_game_tenofclubs, R.drawable.card_game_jackofclubs, R.drawable.card_game_queenofclubs,
            R.drawable.card_game_kingofdiamonds, R.drawable.card_game_oneofdiamonds, R.drawable.card_game_twoofdiamonds, R.drawable.card_game_threeofdiamonds, R.drawable.card_game_fourofdiamonds, R.drawable.card_game_fiveofdiamonds, R.drawable.card_game_sixofdiamonds, R.drawable.card_game_sevenofdiamonds, R.drawable.card_game_eightofdiamonds, R.drawable.card_game_nineofdiamonds, R.drawable.card_game_tenofdiamonds, R.drawable.card_game_jackofdiamonds, R.drawable.card_game_queenofdiamonds,
            R.drawable.card_game_kingofhearts, R.drawable.card_game_oneofhearts, R.drawable.card_game_twoofhearts, R.drawable.card_game_threeofhearts, R.drawable.card_game_fourofhearts, R.drawable.card_game_fiveofhearts, R.drawable.card_game_sixofhearts, R.drawable.card_game_sevenofhearts, R.drawable.card_game_eightofhearts, R.drawable.card_game_nineofhearts, R.drawable.card_game_tenofhearts, R.drawable.card_game_jackofhearts, R.drawable.card_game_queenofhearts,
            R.drawable.card_game_kingofspades, R.drawable.card_game_oneofspades, R.drawable.card_game_twoofspades, R.drawable.card_game_threeofspades, R.drawable.card_game_fourofspades, R.drawable.card_game_fiveofspades, R.drawable.card_game_sixofspades, R.drawable.card_game_sevenofspades, R.drawable.card_game_eightofspades, R.drawable.card_game_nineofspades, R.drawable.card_game_tenofspades, R.drawable.card_game_jackofspades, R.drawable.card_game_queenofspades};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.card_game_game_screen);
        timer = (TextView) findViewById(R.id.txtTimer);
        tScore = (TextView) findViewById(R.id.txtScore);
        stopWatch = (Chronometer) findViewById(R.id.chronometer);
        stopWatch.setVisibility(View.INVISIBLE);
        handler = new android.os.Handler();

        for (int i = 0; i < 5; i++) {
            img[i] = (ImageView) findViewById(imageNum[i]);
        }
        for (int i = 0; i < 10; i++) {
            a[i] = (ImageButton) findViewById(buttonNum[i]);
        }
        click_detail = new JSONData("JA");

        // SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        b1 = getIntent().getExtras();
        l = Integer.parseInt(b1.getString("game_level"));


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

    private void calculate_random(int[] suite, int i, int n) {
        while (true) {
            boolean repeat = false;
            int r = (int) (Math.random() * n);
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

    private void change() {
        stopCond = 0;
        score = 0;
        int suite[] = new int[4], r;

        for (int i = 0; i < l; i++) {
            calculate_random(suite, i, 4);
        }

        int ra[] = new int[10];

        for (int i = 0; i < 10; i++) {
            calculate_random(ra, i, 13);
            int index = (int) (Math.random() * l);
            random_no_array[i] = suite[index] * 13 + ra[i];
        }

        for (int i = 0; i < 10; i++) {
            a[i].setVisibility(View.INVISIBLE);
            a[i].setImageResource(all_cards[random_no_array[i]]);
        }

        for (int i = 0; i < 5; i++) {
            calculate_random(ra, i, 10);
            save_index[i] = ra[i];
            img[i].setImageResource(all_cards[random_no_array[ra[i]]]);
        }
    }


    public void addListenerOnButton() {
        for (int i = 0; i < 10; i++) {
            final int k = i;
            a[k].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    a[k].setEnabled(false);
                    int flag = 0;
                    for (int j = 0; j < 5; j++) {
                        if (k == save_index[j]) {
                            flag = 1;
                            img[j].setImageResource(all_cards[random_no_array[save_index[j]]]);
                            score++;
                        }
                    }
                    //set the click_details
                    click_detail.set_click_details(Long.toString(System.currentTimeMillis() / 1000), (flag == 0) ? "W" : "C");

                    tScore.setText(score + "");
                    stopCond++;
                    if (stopCond == 5) {
                        stopWatch.stop();
                        for (int j = 0; j < 10; j++)
                            a[j].setEnabled(false);

                        final Intent i = new Intent(card_game_game_screen.this, (l <= 3) ? LevelActivity.class : ScoreActivity.class);

                        final JSONData data = new JSONData("JA", b1.getString("data"));
                        data.set_level_data(Integer.toString(score), Integer.toString(5 - score), Integer.toString(score), Long.toString(SystemClock.elapsedRealtime() - startTime), click_detail.get_data_JA());

                        b1.remove("game_level");
                        b1.remove("data");
                        i.putExtras(b1);
                        i.putExtra("data", data.get_data_string());
                        i.putExtra("game_score_L" + Integer.toString(l), score);
                        i.putExtra("game_level", Integer.toString(l + 1));

                        final SharedPreference sp = new SharedPreference(b1.getString("game_name"));
                        if (l > 3) {
                            sp.set_game_score(card_game_game_screen.this, data.get_data_JA());
                            sp.async_response_modified(card_game_game_screen.this, 10000);
                        }
                        final ProgressDialog progressDialog = new ProgressDialog(card_game_game_screen.this,
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage(getString((l <= 3) ? R.string.sd : R.string.cs));
                        progressDialog.show();

                        handler.postDelayed(
                                new Runnable() {
                                    public void run() {

                                        startActivity(i);
                                        if (l > 3 && !sp.is_connected()) {
                                            sp.put_local_data(card_game_game_screen.this, data.get_data_JA());
                                        }
                                        finish();
                                        progressDialog.dismiss();
                                    }
                                }, (l <= 3) ? 1000 : 10000);


                        //String remark = new SharedPreference("card").set_card_game_score(card_game_Game.this, Integer.toString(score), Long.toString(SystemClock.elapsedRealtime()-startTime), Integer.toString(l));
                        //if (remark != null)                            Toast.makeText(card_game_Game.this, remark, Toast.LENGTH_SHORT).show();
                        stopWatch.setText(String.format("%s:%s", String.format("%02d", 0), String.format("%02d", 0)));
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        Intent i = new Intent(card_game_game_screen.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
