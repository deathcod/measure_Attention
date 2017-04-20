package com.example.chinmay.project.Test_your_brain;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chinmay.project.R;

public class test_your_brain_Settings extends Activity //implements OnSeekBarChangeListener
{
    ImageButton btnSound;
    Button btnExit;
    int soundStatus, vol;
    Intent intentback;
    int totalTimeS;
    RadioGroup radioGroup;
    RadioButton radioButton30, radioButton60, radioButton90, radioButton120;
    SeekBar sbar;
    Button showdbhs,resetdb;
    private SQLiteDatabase db;
    AlertDialog adl;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.test_your_brain_settings);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        soundStatus = prefs.getInt("soundStatus", 1);
        totalTimeS = prefs.getInt("totalTimeS", 120);
        vol = prefs.getInt("vol", 100);

        sbar = (SeekBar) findViewById(R.id.seekBarVolume);
        sbar.setProgress(vol);

        radioButton30 = (RadioButton) findViewById(R.id.radioButton30);
        radioButton60 = (RadioButton) findViewById(R.id.radioButton60);
        radioButton90 = (RadioButton) findViewById(R.id.radioButton90);
        radioButton120 = (RadioButton) findViewById(R.id.radioButton120);
        switch (totalTimeS) {
            case 30:
                radioButton30.setChecked(true);
                break;
            case 60:
                radioButton60.setChecked(true);
                break;
            case 90:
                radioButton90.setChecked(true);
                break;
            case 120:
            default:
                radioButton120.setChecked(true);
                break;
        }

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnSound = (ImageButton) findViewById(R.id.imageSound);
        showdbhs = (Button) findViewById(R.id.btnShow);
        resetdb = (Button) findViewById(R.id.btnReset);


        if (soundStatus == 1)
            btnSound.setImageResource(R.drawable.test_your_brain_note_green);
        else
            btnSound.setImageResource(R.drawable.test_your_brain_note_red);
        btnExit = (Button) findViewById(R.id.btnExitSet);
        addListenerOnButton();

    }

    public void addListenerOnButton() {
        btnSound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View a) {
                soundStatus = (soundStatus + 1) % 2;
                if (soundStatus == 1)
                    btnSound.setImageResource(R.drawable.test_your_brain_note_green);
                else
                    btnSound.setImageResource(R.drawable.test_your_brain_note_red);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                intentback = new Intent(test_your_brain_Settings.this, test_your_brain_LevelChooser.class);
                startActivity(intentback);
                finish();
            }
        });


        showdbhs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
                TableLayout tala = new TableLayout(getApplicationContext());
                buildTable(tala, 6, 5);
                adl=new AlertDialog.Builder(b.getContext()).setView(tala).setTitle("High Scores").show();
            }

        });

        resetdb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
            AlertDialog.Builder ad = new AlertDialog.Builder(b.getContext());
            ad.setTitle("Reset High Scores");

            ad.setMessage("Do you want to reset all the high scores?");
            ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    db = openOrCreateDatabase("mapandmatch", Context.MODE_PRIVATE, null);//?
                    db.delete("highscores", null, null);
                    }
            });
            ad.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {}
            });
            ad.show();
        }
    //)
    //upto this much

    });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void buildTable(TableLayout tala, int rows, int cols)
         {
             db = openOrCreateDatabase("mapandmatch", Context.MODE_PRIVATE, null);//?
             String sql = String.format("select * from highscores order by 1,2");
             Cursor c = db.rawQuery(sql, null);
             if (c.getCount() > 0)    c.moveToNext();
             Button bbtn = new Button(this);
             bbtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     adl.cancel();
                 }
             });

             int heading[]={0,30,60,90,120};
             TableRow row=new TableRow(this);
             row.addView(bbtn);
             Bitmap bm= BitmapFactory.decodeResource(getResources(), R.drawable.test_your_brain_backtiny);
             Bitmap scale= Bitmap.createScaledBitmap(bm,70,70,false);
             bbtn.setBackground(new BitmapDrawable(getResources(),scale));
             for (int j = 1; j < cols; j++)
             {
                 TextView tv = new TextView(this);
                 tv.setPadding(25, 5,25, 5);
                 tv.setText(heading[j] + "s");
                 row.addView(tv);
             }
             tala.addView(row);
            for (int i = 0; i < rows-1; i++)
            {
                 row = new TableRow(this);
                 for (int j = 0; j < cols; j++)
                {
                    TextView tv = new TextView(this);
                    tv.setPadding(25, 5, 25, 5);
                    if (j==0)tv.setText("level "+i);
                    else
                    {
                        if (!c.isAfterLast()) {
                            if (c.getInt(0) == i && c.getInt(1) == heading[j]) {
                                System.out.println(c.getInt(2));
                                tv.setText("" +c.getInt(2));
                                if (!c.isAfterLast()) {
                                    c.moveToNext();
                                }

                            } else
                                tv.setText("-");
                        }
                        else
                                tv.setText("-");

                    }
                    row.addView(tv);
                }
                tala.addView(row);
            }
         }
    public void onRadioButtonClicked(View view) {
      boolean checked = ((RadioButton) view).isChecked();
                switch (view.getId()) {
                    case R.id.radioButton30:
                        if(checked)
                        {radioButton30.setActivated(checked);
                        totalTimeS = 30;}
                        break;
                    case R.id.radioButton60:
                        if(checked)
                        {radioButton60.setActivated(checked);
                        totalTimeS = 60;}
                        break;
                    case R.id.radioButton90:
                        if(checked)
                        {radioButton90.setActivated(checked);
                        totalTimeS = 90;}
                        break;
                    case R.id.radioButton120:
                    default:
                        if(checked)
                        {radioButton120.setActivated(checked);
                        totalTimeS = 120;}
                        break;
                }
            }



    @Override
    protected void onStop()
    {
        super.onStop();

            SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("soundStatus", soundStatus);
            editor.putInt("totalTimeS", totalTimeS);
            editor.putInt("vol", sbar.getProgress());
            editor.commit();
        }
 }

