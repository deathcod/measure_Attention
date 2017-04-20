package com.example.chinmay.project.Test_your_brain;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chinmay.project.R;

public class test_your_brain_StartActivity extends Activity
{
    ImageButton playBtn, exitBtn, backBtn;
    Intent intent, intentback;
    int level;
    TextView ins2;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.test_your_brain_instructions);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        playBtn = (ImageButton)findViewById(R.id.playButton);
        playBtn.setImageResource(R.drawable.test_your_brain_play);
        exitBtn=(ImageButton)findViewById(R.id.exitButton);
        exitBtn.setImageResource(R.drawable.test_your_brain_powerbutton);
        backBtn = (ImageButton)findViewById(R.id.backButton);
        backBtn.setImageResource(R.drawable.test_your_brain_backmedium);

        ins2=(TextView)findViewById(R.id.txtIns2);
        Bundle b1 = getIntent().getExtras();
        level = b1.getInt("level");

        switch (level)
        {
            case 0:ins2.setText("One image presented in hexagonal shape and three images presented in square shape appear in the screen. If the image present in hexagonal shape is present among the images presented in square shapes, tap on the green tick button. Otherwise tap on the red cross button.");
                break;
            case 1: ins2.setText("Two images presented in hexagonal shape and three images presented in square shape appear in the screen. If both the images present in hexagonal shape are present among the images presented in square shapes, tap on the green tick button. Otherwise tap on the red cross button");
                break;
            case 2: ins2.setText("Two images presented in hexagonal shape and three images presented in square shape appear in the screen. If both the images present in hexagonal shape are present among the images presented in square shapes in “left to right order” tap on the green tick button. Otherwise tap on the red cross button.");
                break;
            case 3: ins2.setText("Two images presented in hexagonal shape and three images presented in square shape appear in the screen. If both the images present in hexagonal shape are present among the images presented in square shapes in “right to left order” tap on the green tick button. Otherwise tap on the red cross button.");
                break;
            case 4:
            default:ins2.setText("Two images presented in hexagonal shape and three images presented in square shape appear in the screen. If both the images present in hexagonal shape are present among the images presented in square shapes in the order indicated by the violet arrow that appears showing left or right randomly on the screen each turn, tap on the green tick button to score. Otherwise tap on the red cross button.");
        }

        addListenerOnButton();
    }
    public void addListenerOnButton()
    {
        playBtn.setOnClickListener(new View.OnClickListener()
        {
                @Override
                public void onClick(View view)
                {
                   intent = new Intent(test_your_brain_StartActivity.this, test_your_brain_MyAndroidAppActivity.class);

                    intent.putExtra("level", level);
                    startActivity(intent);
                    finish();
                }
        });

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                intentback = new Intent(test_your_brain_StartActivity.this, test_your_brain_LevelChooser.class);
                startActivity(intentback);
                finish();
            }
        });

       exitBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}
