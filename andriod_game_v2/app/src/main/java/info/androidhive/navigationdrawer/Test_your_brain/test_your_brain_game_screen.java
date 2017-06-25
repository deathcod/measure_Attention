package info.androidhive.navigationdrawer.Test_your_brain;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.LevelActivity;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.activity.ScoreActivity;
import info.androidhive.navigationdrawer.other.JSONData;
import info.androidhive.navigationdrawer.other.SharedPreference;


public class test_your_brain_game_screen extends Activity {

    private static SoundPool soundPool;
    String sequence, answer;
    int wrong_answer = 0, correct_answer = 0;
    int score = 0, noOfClicks = 0;
    TextView dispScore, textLevelView, textTimer;
    ImageButton buttonCross, buttonTick;
    ImageView direction, glow;
    ImageView image1, image2, image3, srcImage1, srcImage2;
    int position = 0, orientation;
    int posS[] = new int[6];
    int level;
    int totalTime = 60000;
    boolean status = true;
    int soundStatus = 1;
    int volume = 100;
    Bundle b1 = null;
    JSONData click_detail;
    Handler handler;
    private int all_images[] = {R.drawable.test_your_brain_bear, R.drawable.test_your_brain_cat, R.drawable.test_your_brain_cow, R.drawable.test_your_brain_crocodile, R.drawable.test_your_brain_dog, R.drawable.test_your_brain_elephant, R.drawable.test_your_brain_goat, R.drawable.test_your_brain_horse, R.drawable.test_your_brain_lion, R.drawable.test_your_brain_monkey, R.drawable.test_your_brain_rabbit, R.drawable.test_your_brain_sheep, R.drawable.test_your_brain_squirrel, R.drawable.test_your_brain_tiger, R.drawable.test_your_brain_wolf};
    private int image_resources[] = new int[6];

    public static void initSounds(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        soundPool.load(context, R.raw.test_your_brain_beep_right, 1);
        soundPool.load(context, R.raw.test_your_brain_beep_wrong, 2);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.test_your_game_screen);

        b1 = getIntent().getExtras();
        level = Integer.parseInt(b1.getString("game_level"));

        handler = new android.os.Handler();
        textLevelView = (TextView) findViewById(R.id.textLevelView);
        textLevelView.setText("Level : " + level);
        level--;

        textTimer = (TextView) findViewById(R.id.textTimer);

        dispScore = (TextView) findViewById(R.id.displayScore);
        direction = (ImageView) findViewById(R.id.imageArrow);
        glow = (ImageView) findViewById(R.id.glowArrow);
        glow.setImageResource(R.drawable.test_your_brain_yellow);

        switch (level) {
            case 0:
                direction.setImageResource(R.drawable.test_your_brain_belonsto);
                break;
            case 1:
                direction.setImageResource(R.drawable.test_your_brain_subset);
                break;
            case 2:
                direction.setImageResource(R.drawable.test_your_brain_arrow_right);
                break;
            case 3:
                direction.setImageResource(R.drawable.test_your_brain_arrow_left);
                break;
            default:
                orientation = (int) (Math.random() * 2);
                if (orientation == 0)
                    direction.setImageResource(R.drawable.test_your_brain_arrow_right);
                else
                    direction.setImageResource(R.drawable.test_your_brain_arrow_left);
        }

        image1 = (ImageView) findViewById(R.id.imageView1);
        image2 = (ImageView) findViewById(R.id.imageView2);
        image3 = (ImageView) findViewById(R.id.imageView3);

        srcImage1 = (ImageView) findViewById(R.id.pattern1);
        srcImage2 = (ImageView) findViewById(R.id.pattern2);

        buttonCross = (ImageButton) findViewById(R.id.crossButton);
        buttonTick = (ImageButton) findViewById(R.id.tickButton);
        buttonCross.setImageResource(R.drawable.test_your_brain_cross);
        buttonTick.setImageResource(R.drawable.test_your_brain_tick);
        click_detail = new JSONData("JA");

        addListenerOnButton();

        change();

        CountDownTimer cT = new CountDownTimer(totalTime, 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished / totalTime);
                int va = (int) ((millisUntilFinished % totalTime) / 1000);
                textTimer.setText("" + v + ":" + String.format("%02d", va));
            }

            public void onFinish() {
                textTimer.setText("over!");
                buttonTick.setEnabled(false);
                buttonCross.setEnabled(false);
                String AVERAGE_TIME = "";
                if (noOfClicks != 0)
                    AVERAGE_TIME = Integer.toString(totalTime / noOfClicks);
                else {
                    Toast.makeText(test_your_brain_game_screen.this, "Since you didn't click any button, play the level again", Toast.LENGTH_LONG).show();
                    level--;
                }
                level++;
                final Intent i = new Intent(test_your_brain_game_screen.this, (level <= 4) ? LevelActivity.class : ScoreActivity.class);

                final JSONData data = new JSONData("JA", b1.getString("data"));
                data.set_level_data(Integer.toString(score), Integer.toString(wrong_answer), Integer.toString(correct_answer), Integer.toString(totalTime), click_detail.get_data_JA());

                b1.remove("game_level");
                b1.remove("data");

                i.putExtras(b1);

                i.putExtra("data", data.get_data_string_without_intend());
                i.putExtra("game_level", Integer.toString(level + 1));

                final SharedPreference sp = new SharedPreference(b1.getString("game_name"));
                if (level > 4) {
                    sp.set_game_score(test_your_brain_game_screen.this, data.get_data_JA());
                    i.removeExtra("data");
                    i.putExtra("data", sp.get_API() + "\n\n" + sp.get_DATA());
                    sp.async_response_modified(test_your_brain_game_screen.this, 10000, true);
                }
                final ProgressDialog progressDialog = new ProgressDialog(test_your_brain_game_screen.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(getString((level <= 4) ? R.string.sd : R.string.cs));
                progressDialog.show();

                handler.postDelayed(
                        new Runnable() {
                            public void run() {

                                startActivity(i);
                                if (level > 4 && !sp.is_connected()) {
                                    sp.put_local_data(test_your_brain_game_screen.this, data.get_data_JA());
                                }
                                finish();
                                progressDialog.dismiss();
                            }
                        }, (level <= 4) ? 1000 : 10000);


            }
        };
        change();
        cT.start();
    }

    public void addListenerOnButton() {

        buttonCross.setOnClickListener(new OnClickListener() {
            public void onClick(View a) {
                switch (level) {
                    case 0:
                        status = !sequence.contains("" + answer.charAt(0));
                        break;
                    case 1:
                        status = !(sequence.contains("" + answer.charAt(0)) && sequence.contains("" + answer.charAt(1)));
                        break;
                    case 2:
                        status = !(sequence.contains(answer) || (sequence.charAt(0) == answer.charAt(0) && sequence.charAt(2) == answer.charAt(1)));
                        break;

                    case 3:
                        status = !(sequence.contains(new StringBuffer(answer).reverse().toString()) || (sequence.charAt(0) == answer.charAt(1) && sequence.charAt(2) == answer.charAt(0)));
                        break;
                    case 4:
                    default:
                        if (orientation == 0) {
                            status = !(sequence.contains(answer) || (sequence.charAt(0) == answer.charAt(0) && sequence.charAt(2) == answer.charAt(1)));
                        } else {
                            status = !(sequence.contains(new StringBuffer(answer).reverse().toString()) || (sequence.charAt(0) == answer.charAt(1) && sequence.charAt(2) == answer.charAt(0)));

                        }
                        orientation = (int) (Math.random() * 2);
                        if (orientation == 0)
                            direction.setImageResource(R.drawable.test_your_brain_arrow_right);
                        else
                            direction.setImageResource(R.drawable.test_your_brain_arrow_left);
                        break;
                }
                if (status) {
                    score++;
                    glow.setImageResource(R.drawable.test_your_brain_green);
                    noOfClicks++;
                    if (soundStatus == 1)
                        playSound(getApplicationContext(), 1);
                } else {
                    score--;
                    glow.setImageResource(R.drawable.test_your_brain_red);
                    if (soundStatus == 1)
                        playSound(getApplicationContext(), 2);
                }
                //set the click_details
                click_detail.set_click_details(Long.toString(System.currentTimeMillis() / 1000), (status) ? "C" : "W");
                if (status) correct_answer++;
                else wrong_answer++;
                dispScore.setText("Score : " + score);
                change();
            }
        });

        buttonTick.setOnClickListener(new OnClickListener() {
            public void onClick(View a) {
                switch (level) {
                    case 0:
                        status = sequence.contains("" + answer.charAt(0));
                        break;
                    case 1:
                        status = sequence.contains("" + answer.charAt(0)) && sequence.contains("" + answer.charAt(1));
                        break;
                    case 2:
                        status = sequence.contains(answer) || (sequence.charAt(0) == answer.charAt(0) && sequence.charAt(2) == answer.charAt(1));
                        break;

                    case 3:
                        status = sequence.contains(new StringBuffer(answer).reverse().toString()) || (sequence.charAt(0) == answer.charAt(1) && sequence.charAt(2) == answer.charAt(0));
                        break;

                    case 4:
                    default:
                        if (orientation == 0) {
                            status = sequence.contains(answer) || (sequence.charAt(0) == answer.charAt(0) && sequence.charAt(2) == answer.charAt(1));
                        } else {
                            status = sequence.contains(new StringBuffer(answer).reverse().toString()) || (sequence.charAt(0) == answer.charAt(1) && sequence.charAt(2) == answer.charAt(0));

                        }
                        orientation = (int) (Math.random() * 2);
                        if (orientation == 0)
                            direction.setImageResource(R.drawable.test_your_brain_arrow_right);
                        else
                            direction.setImageResource(R.drawable.test_your_brain_arrow_left);
                        break;
                }
                if (status == true) {
                    score++;
                    glow.setImageResource(R.drawable.test_your_brain_green);
                    noOfClicks++;
                    if (soundStatus == 1) playSound(getApplicationContext(), 1);
                } else {
                    score--;
                    glow.setImageResource(R.drawable.test_your_brain_red);
                    if (soundStatus == 1) playSound(getApplicationContext(), 2);
                }
                //set the click_details
                click_detail.set_click_details(Long.toString(System.currentTimeMillis() / 1000), (status) ? "C" : "W");
                if (status) correct_answer++;
                else wrong_answer++;
                dispScore.setText("Score : " + score);
                change();

            }
        });


    }

    /**
     * Play a given sound in the soundPool
     */

    public void playSound(Context context, int soundID) {

        if (soundPool == null) {
            initSounds(context);
        }
        soundPool.play(soundID, volume / 1000f, volume / 1000f, 1, 0, 1f);
        //Play sounds with given left & right volume & no repeats of the same sound

    }

    public void change() {
        int temp;
        int check, check2, check3, check4, check5;

        temp = (int) (Math.random() * all_images.length);
        check = temp;
        image_resources[0] = all_images[check];

        while ((temp = (int) (Math.random() * all_images.length)) == check) ;
        check2 = temp;
        image_resources[1] = all_images[check2];

        while ((temp = (int) (Math.random() * all_images.length)) == check || check2 == temp) ;
        check3 = temp;
        image_resources[2] = all_images[check3];

        while ((temp = (int) (Math.random() * all_images.length)) == check || check2 == temp || check3 == temp)
            ;
        check4 = temp;
        image_resources[3] = all_images[temp];

        while ((temp = (int) (Math.random() * all_images.length)) == check || check2 == temp || check3 == temp || check4 == temp)
            ;
        check5 = temp;
        image_resources[4] = all_images[temp];

        while ((temp = (int) (Math.random() * all_images.length)) == check || check2 == temp || check3 == temp || check4 == temp || check5 == temp)
            ;
        image_resources[5] = all_images[temp];

        temp = 0;
        check = 0;

        //position=(position+1)%6;
        int range = 3;
        int range0 = 4;
        if (level == 0) range0 = 6;
        if (level == 0) range = 6;
        if (level == 1) range = 4;

        sequence = "";
        temp = (int) (Math.random() * range0);
        posS[0] = temp;
        check = temp;
        sequence += (char) (check + 97);
        image1.setImageResource(image_resources[check]);

        while ((temp = (int) (Math.random() * range0)) == check) ;
        check2 = temp;
        posS[1] = check2;
        sequence += (char) (check2 + 97);
        image2.setImageResource(image_resources[check2]);

        while (((temp = (int) (Math.random() * range0)) == check) || (check2 == temp)) ;
        check3 = temp;
        posS[2] = check3;
        sequence += (char) (check3 + 97);
        image3.setImageResource(image_resources[check3]);
        posS[3] = 6 - posS[0] - posS[1] - posS[2];

        if (level == 0) {
            while (((temp = (int) (Math.random() * range)) == check) || check2 == temp || check3 == temp)
                ;
            check4 = temp;
            posS[3] = check4;

            while (((temp = (int) (Math.random() * range)) == check) || check2 == temp || check3 == temp || check4 == temp)
                ;
            check5 = temp;
            posS[4] = check5;
            while (((temp = (int) (Math.random() * range)) == check) || check2 == temp || check3 == temp || check4 == temp || check5 == temp)
                ;
            posS[5] = temp;
        }
        answer = "";
        position = (int) (Math.random() * range);
        answer += (char) (posS[position] + 97);
        srcImage1.setBackgroundResource(image_resources[posS[position]]);

        while ((temp = (int) (Math.random() * range)) == position) ;
        position = temp;
        answer += (char) (posS[position] + 97);
        srcImage2.setBackgroundResource(image_resources[posS[position]]);

        if (level == 0)
            srcImage2.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        Intent i = new Intent(test_your_brain_game_screen.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}