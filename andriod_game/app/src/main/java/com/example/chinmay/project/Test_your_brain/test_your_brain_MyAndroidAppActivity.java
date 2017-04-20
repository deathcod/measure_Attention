package com.example.chinmay.project.Test_your_brain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.os.SystemClock;
import android.view.Menu;
import android.widget.Chronometer;
import android.media.SoundPool;

import com.example.chinmay.project.R;
import com.example.chinmay.project.SharedPreference;

import java.util.HashMap;

public class test_your_brain_MyAndroidAppActivity extends Activity {
    private SQLiteDatabase database;
    public static final int S1 = R.raw.test_your_brain_beep_right;
    public static final int S2 = R.raw.test_your_brain_beep_wrong;
	String sequence, answer;
	int score=0, hiScore=0, noOfClicks=0;
	TextView dispScore,textLevelView, textHs, textTimer, textTotalTime, averageRT;
	ImageButton buttonCross, buttonTick, buttonBack;
	ImageView direction, glow;
	ImageView image1, image2, image3, srcImage1, srcImage2;
	int position=0,orientation;
    int posS[]=new int[6];
    int level;
    int totalTime=20000,totalTimeS;
    boolean status=true;
    private int all_images[]={R.drawable.test_your_brain_bear, R.drawable.test_your_brain_cat, R.drawable.test_your_brain_cow, R.drawable.test_your_brain_crocodile, R.drawable.test_your_brain_dog, R.drawable.test_your_brain_elephant, R.drawable.test_your_brain_goat, R.drawable.test_your_brain_horse, R.drawable.test_your_brain_lion, R.drawable.test_your_brain_monkey, R.drawable.test_your_brain_rabbit, R.drawable.test_your_brain_sheep, R.drawable.test_your_brain_squirrel, R.drawable.test_your_brain_tiger, R.drawable.test_your_brain_wolf};
	private int image_resources[]= new int[6];
    Intent intentback;
    int soundStatus;
    int volume;
    private static SoundPool soundPool;

    public static void initSounds(Context context)
    {
       soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
       soundPool.load(context, R.raw.test_your_brain_beep_right, 1);
       soundPool.load(context, R.raw.test_your_brain_beep_wrong, 2);
    }

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.test_your_brain_main);
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        soundStatus = prefs.getInt("soundStatus",1);
        volume=prefs.getInt("vol",100);
        Bundle b1 = getIntent().getExtras();
        level = b1.getInt("level");
        totalTimeS = prefs.getInt("totalTimeS", 45);
        database=openOrCreateDatabase("mapandmatch", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        String sql="create table if not exists highscores (level integer not null, seconds integer not null, highscore integer not null,primary key(level,seconds))";
        database.execSQL(sql);
        sql=String.format("select highscore from  highscores where level='%d' and seconds='%d'",level,totalTimeS);
    	Cursor c=database.rawQuery(sql,null);
    	if(c.getCount()>0)
    	{
            c.moveToFirst();
            hiScore=c.getInt(c.getColumnIndex("highscore"));
            System.out.println(hiScore);
    	}
        else
            hiScore=0;
        totalTime=totalTimeS*1000;
		textLevelView= (TextView) findViewById(R.id.textLevelView);
		textLevelView.setText("Level : " + level);
        textHs=(TextView)findViewById(R.id.textHS);
        textHs.setText("High Score : " + hiScore);
        textTotalTime=(TextView)findViewById(R.id.textTotalTime);
        textTotalTime.setText("Total time : "+(totalTime/1000)+"s");
        textTimer=(TextView)findViewById(R.id.textTimer);
        averageRT=(TextView)findViewById(R.id.textART);
        dispScore= (TextView) findViewById(R.id.displayScore);
		direction = (ImageView) findViewById(R.id.imageArrow);
        glow = (ImageView) findViewById(R.id.glowArrow);
        glow.setImageResource(R.drawable.test_your_brain_yellow);

        switch(level)
		{
            case 0:
                direction.setImageResource(R.drawable.test_your_brain_belonsto); break;
            case 1:
				direction.setImageResource(R.drawable.test_your_brain_subset); break;
			case 2:
				direction.setImageResource(R.drawable.test_your_brain_arrow_right);break;
			case 3:
				direction.setImageResource(R.drawable.test_your_brain_arrow_left);break;
			case 4:
			default :
                orientation=(int)(Math.random()*2);
                if(orientation==0)
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

        buttonBack=(ImageButton) findViewById(R.id.imageback);
        buttonBack.setVisibility(View.INVISIBLE);
        averageRT.setVisibility(View.INVISIBLE);
        addListenerOnButton();

		change();

        CountDownTimer cT =  new CountDownTimer(totalTime, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                String v = String.format("%02d", millisUntilFinished/60000);
                int va = (int)( (millisUntilFinished%60000)/1000);
                textTimer.setText("" +v+":"+String.format("%02d",va));
            }

            public void onFinish()
            {
                textTimer.setText("over!");
                buttonTick.setEnabled(false);
                buttonCross.setEnabled(false);
                buttonBack.setVisibility(View.VISIBLE);
                if(noOfClicks>0)
                {
                    String average_time = Integer.toString(totalTime / noOfClicks);
                    averageRT.setText("Average response time : " + average_time +"ms");
                    String remark = new SharedPreference("test").set_test_your_brain_score(test_your_brain_MyAndroidAppActivity.this, Integer.toString(score), average_time, Integer.toString(level));
                    if (remark != null)
                        Toast.makeText(test_your_brain_MyAndroidAppActivity.this, remark, Toast.LENGTH_SHORT).show();

                    averageRT.setVisibility(View.VISIBLE);

//                  System.out.println((totalTime / noOfClicks));
                }
                else
                {
                    averageRT.setText("You did not give any correct answer.");
                    averageRT.setVisibility(View.VISIBLE);
                }

                if (score > hiScore)
                {
                    Context context = getApplicationContext();
                    CharSequence text = "You have a new high score!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, text, duration).show();
                }
            }
        };
        change();
        cT.start();
    }

	public void addListenerOnButton() {

				buttonCross.setOnClickListener(new OnClickListener() {
				public void onClick(View a) {
                    switch(level)
                    {
                        case 0:
                            if (sequence.contains("" + answer.charAt(0))) status=false;
                            else status=true;
                            break;
                        case 1:
                            if (sequence.contains(""+answer.charAt(0))&& sequence.contains(""+answer.charAt(1))) status=false;
                            else status=true;
                            break;
                        case 2:
                            if (sequence.contains(answer)||(sequence.charAt(0)==answer.charAt(0)&&sequence.charAt(2)==answer.charAt(1))) status=false;
                            else status=true;
                            break;

                        case 3:
                            if (sequence.contains(new StringBuffer(answer).reverse().toString())||(sequence.charAt(0)==answer.charAt(1)&&sequence.charAt(2)==answer.charAt(0))) status=false;
                            else status=true;
                            break;
                        case 4:
                        default :
                            if(orientation==0)
                            {
                                if (sequence.contains(answer)||(sequence.charAt(0)==answer.charAt(0)&&sequence.charAt(2)==answer.charAt(1))) status=false;
                                else status=true;
                            }
                            else
                            {
                                if (sequence.contains(new StringBuffer(answer).reverse().toString())||(sequence.charAt(0)==answer.charAt(1)&&sequence.charAt(2)==answer.charAt(0)))
                                    status=false;
                                else status=true;

                            }
                            orientation=(int)(Math.random()*2);
                            if(orientation==0)
                                direction.setImageResource(R.drawable.test_your_brain_arrow_right);
                            else
                                direction.setImageResource(R.drawable.test_your_brain_arrow_left);
                            break;
                    }
                    if(status) {score++;glow.setImageResource(R.drawable.test_your_brain_green);noOfClicks++;
                          if (soundStatus==1) playSound(getApplicationContext(),1);}
                    else{score--;glow.setImageResource(R.drawable.test_your_brain_red);
                          if (soundStatus==1) playSound(getApplicationContext(),2);}
					dispScore.setText("Score : " + score);
                    change();
				}
			});

			buttonTick.setOnClickListener(new OnClickListener() {
				public void onClick(View a) {
                    switch(level) {
                        case 0: if (sequence.contains("" +answer.charAt(0))) status=true;
                        else status=false;
                                break;
                        case 1:
                            if (sequence.contains("" + answer.charAt(0)) && sequence.contains("" + answer.charAt(1)))
                                status=true;
                            else status=false;
                            break;
                        case 2:
                            if (sequence.contains(answer)||(sequence.charAt(0)==answer.charAt(0)&&sequence.charAt(2)==answer.charAt(1)) ) status=true;
                            else status=false;
                            break;

                        case 3:
                            if (sequence.contains(new StringBuffer(answer).reverse().toString())||(sequence.charAt(0)==answer.charAt(1)&&sequence.charAt(2)==answer.charAt(0)))
                                status=true;
                            else status=false;
                            break;

                        case 4:
                        default:
                            if(orientation==0)
                            {
                                if (sequence.contains(answer)||(sequence.charAt(0)==answer.charAt(0)&&sequence.charAt(2)==answer.charAt(1)) ) status=true;
                                else status=false;
                            }
                            else
                            {
                                if (sequence.contains(new StringBuffer(answer).reverse().toString())||(sequence.charAt(0)==answer.charAt(1)&&sequence.charAt(2)==answer.charAt(0)))
                                    status=true;
                                else status=false;

                            }
                            orientation=(int)(Math.random()*2);
                            if(orientation==0)
                                direction.setImageResource(R.drawable.test_your_brain_arrow_right);
                            else
                                direction.setImageResource(R.drawable.test_your_brain_arrow_left);
                            break;
                    }
                    if(status==true) {score++;glow.setImageResource(R.drawable.test_your_brain_green);noOfClicks++;
                          if (soundStatus==1) playSound(getApplicationContext(),1);
                    }
                    else{score--;glow.setImageResource(R.drawable.test_your_brain_red);
                          if (soundStatus==1) playSound(getApplicationContext(),2);}
					dispScore.setText("Score : " + score);
                    change();

				}
			});

        buttonBack.setOnClickListener (new OnClickListener(){
            public void onClick(View view)
            {

                intentback = new Intent(test_your_brain_MyAndroidAppActivity.this, test_your_brain_LevelChooser.class);
                startActivity(intentback);
                finish();
            }
        });


}
    /** Play a given sound in the soundPool */

    public  void playSound(Context context, int soundID) {

        if(soundPool == null ){
            initSounds(context);
        }
        float volumeLeft = 1.0f;// whatever in the range = 0.0 to 1.0
        float volumeRight = 1.0f;
        soundPool.play(soundID, volume/1000f, volume/1000f, 1, 0, 1f);
        //Play sounds with given left & right volume & no repeats of the same sound

    }

	public void change() {
        int temp;
        int check, check2,check3,check4,check5;

        temp=(int)(Math.random()*all_images.length);
        check=temp;
        image_resources[0]=all_images[check];

        while ((temp = (int)(Math.random() * all_images.length)) == check);
        check2=temp;
        image_resources[1]=all_images[check2];

        while ((temp = (int)(Math.random() * all_images.length)) == check || check2==temp);
        check3=temp;
        image_resources[2]=all_images[check3];

        while ((temp = (int)(Math.random() * all_images.length)) == check || check2==temp ||check3==temp);
        check4=temp;
        image_resources[3]=all_images[temp];

        while ((temp = (int)(Math.random() * all_images.length)) == check || check2==temp ||check3==temp||check4==temp);
        check5=temp;
        image_resources[4]=all_images[temp];

        while ((temp = (int)(Math.random() * all_images.length)) == check || check2==temp ||check3==temp||check4==temp||check5==temp);
        image_resources[5]=all_images[temp];

        temp=0;
        check=0;

        //position=(position+1)%6;
        int range = 3;
        int range0=4;
        if (level== 0) range0=6;
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

        if(level==0) {
            while (((temp = (int) (Math.random() * range)) == check) || check2 == temp || check3 == temp )
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

        if(level==0)
            srcImage2.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (score > hiScore)
        {
            SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.commit();
            String sql=String.format("INSERT OR REPLACE INTO highscores VALUES ( '%d','%d','%d')",level,totalTimeS,score);
            database.execSQL(sql);
        }
    }
}