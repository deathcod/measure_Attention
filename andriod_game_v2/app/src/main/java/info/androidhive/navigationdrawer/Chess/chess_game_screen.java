package info.androidhive.navigationdrawer.Chess;

import android.app.Activity;
import android.app.ProgressDialog;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.LevelActivity;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.activity.ScoreActivity;
import info.androidhive.navigationdrawer.other.JSONData;
import info.androidhive.navigationdrawer.other.SharedPreference;

public class chess_game_screen extends Activity {

    ImageView question[] = new ImageView[6];
    ImageButton answer[] = new ImageButton[6];
    TextView timer, dispScore, dispLevel, txtART;
    ImageButton back;
    Chronometer stopWatch;
    Bundle b1;

    int l;
    int clickCount = 0, score = 0;
    long startTime, countUp, beforePause;
    int temp_store[] = new int[6];
    JSONData click_detail;
    int imgNum[] = {
            R.id.imageView1,
            R.id.imageView2,
            R.id.imageView3,
            R.id.imageView4,
            R.id.imageView5,
            R.id.imageView6};
    int buttonNum[] = {
            R.id.imageBtn1,
            R.id.imageBtn2,
            R.id.imageBtn3,
            R.id.imageBtn4,
            R.id.imageBtn5,
            R.id.imageBtn6};
    private int[] image_resources;
    private int[] ans_resources;
    private int chessObjs[] = {
            R.drawable.chess_blackking,
            R.drawable.chess_blackqueen,
            R.drawable.chess_blackbishop,
            R.drawable.chess_blackknight,
            R.drawable.chess_blackrook,
            R.drawable.chess_blackpawn,
            R.drawable.chess_whiteking,
            R.drawable.chess_whitequeen,
            R.drawable.chess_whitebishop,
            R.drawable.chess_whiteknight,
            R.drawable.chess_whiterook,
            R.drawable.chess_whitepawn};

    public static int[] pickNrandom(int[] array, int n) {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.chess_game_screen);

        clickCount = 0;

        for (int i = 0; i < 6; i++) {
            question[i] = (ImageView) findViewById(imgNum[i]);
            answer[i] = (ImageButton) findViewById(buttonNum[i]);
        }

        timer = (TextView) findViewById(R.id.txtTimer);
        dispScore = (TextView) findViewById(R.id.txtScore);
        dispLevel = (TextView) findViewById(R.id.txtLevel);
        txtART = (TextView) findViewById(R.id.textArt);
        txtART.setVisibility(View.INVISIBLE);
        stopWatch = (Chronometer) findViewById(R.id.chronometer);
        stopWatch.setVisibility(View.INVISIBLE);

        b1 = getIntent().getExtras();
        l = Integer.parseInt(b1.getString("game_level"));
        dispLevel.setText("Level: " + l);

        click_detail = new JSONData("JA");
    }

    @Override
    protected void onStart() {
        super.onStart();
        CountDownTimer cT = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished / 60000);
                int va = (int) ((millisUntilFinished % 60000) / 1000);
                timer.setText("" + v + ":" + String.format("%02d", va));
            }

            public void onFinish() {
                for (int i = 0; i < 6; i++) {
                    question[i].setVisibility(View.INVISIBLE);
                    answer[i].setVisibility(View.VISIBLE);
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
        addListenerOnButton();
        change();
    }

    private void onFinish() {
        for (int i = 0; i < 6; i++)
            answer[i].setEnabled(false);

        beforePause = SystemClock.elapsedRealtime() - stopWatch.getBase();
        txtART.setVisibility(View.VISIBLE);
        stopWatch.setVisibility(View.INVISIBLE);
        stopWatch.stop();

        final Intent i = new Intent(chess_game_screen.this, (l <= 3) ? LevelActivity.class : ScoreActivity.class);
        b1.remove("game_level");

        final JSONData data = new JSONData("JA", b1.getString("data"));
        data.set_level_data(Integer.toString(score), Integer.toString(6 - score), Integer.toString(score), Long.toString(beforePause), click_detail.get_data_JA());

        b1.remove("data");
        i.putExtras(b1);
        i.putExtra("data", data.get_data_string());
        i.putExtra("game_level", Integer.toString(l + 1));

        final SharedPreference sp = new SharedPreference(b1.getString("game_name"));
        if (l > 3) {
            sp.set_game_score(chess_game_screen.this, data.get_data_JA());
            sp.async_response_modified(chess_game_screen.this, 10000);
        }
        final ProgressDialog progressDialog = new ProgressDialog(chess_game_screen.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString((l <= 3) ? R.string.sd : R.string.cs));
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        startActivity(i);
                        if (l > 3 && !sp.is_connected()) {
                            sp.put_local_data(chess_game_screen.this, data.get_data_JA());
                        }
                        finish();
                        progressDialog.dismiss();
                    }
                }, (l <= 3) ? 1000 : 10000);


    }

    public void addListenerOnButton() {

        for (int i = 0; i < 6; i++) {
            final int k = i;
            answer[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    int x = (l == 2 || l == 4) ? 5 - clickCount : clickCount;
                    int flag = 0;
                    if (image_resources[x] == ans_resources[k]) {
                        flag = 1;
                        question[clickCount].setVisibility(View.VISIBLE);
                        question[clickCount].setImageResource(chessObjs[image_resources[x]]);
                        answer[k].setEnabled(false);
                        score++;
                        dispScore.setText("Score: " + score);
                    } else
                        Toast.makeText(chess_game_screen.this, "Wrong!", Toast.LENGTH_SHORT).show();

                    //set the click_details
                    click_detail.set_click_details(Long.toString(System.currentTimeMillis() / 1000), (flag == 0) ? "W" : "C");

                    if (clickCount++ == 5)
                        onFinish();
                }
            });
        }
    }

    public void change() {

        int array_0_to_5[] = {0, 1, 2, 3, 4, 5};
        int array_6_to_11[] = {6, 7, 8, 9, 10, 11};
        int black_or_white = (int) (Math.random() * 2);
        int array_0_to_11[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        ans_resources = (l <= 2) ? ((black_or_white == 0) ? pickNrandom(array_0_to_5, 6) : pickNrandom(array_6_to_11, 6)) : pickNrandom(array_0_to_11, 6);
        image_resources = pickNrandom(ans_resources, 6);

        for (int i = 0; i < 6; i++) {
            //setting the question
            answer[i].setImageResource(chessObjs[ans_resources[i]]);
            answer[i].setVisibility(View.INVISIBLE);

            //setting the answer
            question[i].setImageResource(chessObjs[image_resources[i]]);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(chess_game_screen.this, MainActivity.class);
        startActivity(i);
    }
}
