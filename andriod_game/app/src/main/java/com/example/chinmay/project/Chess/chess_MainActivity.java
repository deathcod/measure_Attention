package com.example.chinmay.project.Chess;

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

public class chess_MainActivity extends Activity {

    ImageView question[] = new ImageView[6];
    ImageButton answer[] = new ImageButton[6];
    TextView timer, dispScore, dispLevel, txtDisp, txtART;
    ImageButton back;
    Chronometer stopWatch;

    int imgNum[] = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView5, R.id.imageView6};
    int buttonNum[] = {R.id.imageBtn1, R.id.imageBtn2, R.id.imageBtn3, R.id.imageBtn4, R.id.imageBtn5, R.id.imageBtn6};
    int l;
    int clickCount = 0, score = 0;
    long startTime, countUp, beforePause;
    int temp_store[] = new int[6];
    private int chessObjs[] = {R.drawable.chess_blackking,
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
    private int image_resources[] = new int[6];
    private int ans_resources[] = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.chess_activity_main);

        clickCount = 0;

        for (int i = 0; i < 6; i++) {
            question[i] = (ImageView) findViewById(imgNum[i]);
            answer[i] = (ImageButton) findViewById(buttonNum[i]);
        }

        timer = (TextView) findViewById(R.id.txtTimer);
        dispScore = (TextView) findViewById(R.id.txtScore);
        dispLevel = (TextView) findViewById(R.id.txtLevel);
        back = (ImageButton) findViewById(R.id.imgBack);
        back.setVisibility(View.INVISIBLE);
        txtART = (TextView) findViewById(R.id.textArt);
        txtART.setVisibility(View.INVISIBLE);
        stopWatch = (Chronometer) findViewById(R.id.chronometer);
        stopWatch.setVisibility(View.INVISIBLE);

        Bundle b1 = getIntent().getExtras();
        l = b1.getInt("level");
        dispLevel.setText("Level: " + l);

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
                timer.setVisibility(View.INVISIBLE);
                startTime = SystemClock.elapsedRealtime();
                txtDisp = (TextView) findViewById(R.id.txtGoesHere);
                stopWatch.setBase(SystemClock.elapsedRealtime());
                stopWatch.setVisibility(View.VISIBLE);
                stopWatch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer arg0) {
                        countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;
                        String asText = (countUp / 60) + ":" + (countUp % 60);
                        txtDisp.setText(asText);
                        txtDisp.setVisibility(View.INVISIBLE);
                    }
                });
                stopWatch.start();
            }
        };
        cT.start();
        change();
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(chess_MainActivity.this, chess_Level_Chooser.class);
                startActivity(i);
                finish();
            }
        });


        switch (l) {

            case 1:
            case 3:

                for (int i = 0; i < 6; i++) {
                    final int k = i;
                    answer[k].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {

                            System.out.println(image_resources[clickCount] + " " + k);
                            if (image_resources[clickCount] == ans_resources[k]) //changed
                            {
                                question[clickCount].setVisibility(View.VISIBLE);
                                question[clickCount].setImageResource(chessObjs[image_resources[clickCount]]);
                                answer[k].setEnabled(false);
                                score++;
                                dispScore.setText("Score: " + score);

                            } else {
                                Toast.makeText(chess_MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                            }

                            if (clickCount >= 5) {
                                for (int i = 0; i < 6; i++)
                                    answer[i].setEnabled(false);
                                beforePause = SystemClock.elapsedRealtime() - stopWatch.getBase();
                                txtART.setText("Average Response Time " + beforePause + " ms");
                                String remark = new SharedPreference("chess").set_chess_score(chess_MainActivity.this, Integer.toString(score), Long.toString(beforePause), Integer.toString(l));
                                if (remark != null)
                                    Toast.makeText(chess_MainActivity.this, remark, Toast.LENGTH_SHORT).show();


                                txtART.setVisibility(View.VISIBLE);
                                stopWatch.setVisibility(View.INVISIBLE);
                                back.setVisibility(View.VISIBLE);
                                stopWatch.stop();

                                return;
                            }
                            clickCount++;
                        }
                    });
                }
                break;

            case 2:
            case 4:
                for (int i = 0; i < 6; i++) {
                    final int k = i;
                    answer[k].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {

                            System.out.println(clickCount + " " + temp_store[clickCount] + " " + k);
                            if (temp_store[clickCount] == ans_resources[k]) //change
                            {
                                question[clickCount].setVisibility(View.VISIBLE);
                                question[clickCount].setImageResource(chessObjs[temp_store[clickCount]]);
                                answer[k].setEnabled(false);
                                score++;
                                dispScore.setText("Score: " + score);

                            } else {
                                Toast.makeText(chess_MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                            }
                            if (clickCount >= 5) {
                                for (int i = 0; i < 6; i++)
                                    answer[i].setEnabled(false);
                                beforePause = SystemClock.elapsedRealtime() - stopWatch.getBase();
                                txtART.setText("Average Response Time" + beforePause + "ms");
                                String remark = new SharedPreference("chess").set_chess_score(chess_MainActivity.this, Integer.toString(score), Long.toString(beforePause), Integer.toString(l));
                                if (remark != null)
                                    Toast.makeText(chess_MainActivity.this, remark, Toast.LENGTH_SHORT).show();


                                txtART.setVisibility(View.VISIBLE);
                                stopWatch.setVisibility(View.INVISIBLE);
                                back.setVisibility(View.VISIBLE);
                                stopWatch.stop();
                                return;
                            }
                            clickCount++;
                        }
                    });
                }
                break;
        }
    }


    public void change() {
        int bw = 0, r, k;
        int ra[] = new int[6];
        int sixTwelve = 12;

        switch (l) {
            case 1:
            case 2:
                sixTwelve = 6;
                bw = (int) (Math.random() * 2); //to decide whether black objects or white objects will appear

            case 3:
            case 4:
                for (int i = 0; i < 6; i++) {
                    while (true) {
                        boolean repeat = false;
                        r = (int) (Math.random() * sixTwelve) + ((l < 3) ? bw * 6 : 0);
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
                    ans_resources[i] = ra[i];
                }


                for (int i = 0; i < 6; i++) {
                    answer[i].setImageResource(chessObjs[ans_resources[i]]); //change
                    answer[i].setVisibility(View.INVISIBLE);
                }

/*              for(int i=0;i<6;i++)
               {
                    temp=bw*sixTwelve+i;
                      ans_resources[i]=chessObjs[temp];
                }
*/
                for (int i = 0; i < 6; i++) {
                    while (true) {
                        boolean repeat = false;
                        r = (int) (Math.random() * 6);
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
                    image_resources[i] = ans_resources[ra[i]];
                }
                k = 5;
                for (int i = 0; i < 6; i++) {
                    temp_store[k] = image_resources[i];
                    k--;
                }

                for (int i = 0; i < 6; i++) {
                    question[i].setImageResource(chessObjs[image_resources[i]]);
                }
                break;
        }
    }
}
