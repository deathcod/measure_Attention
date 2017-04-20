package com.example.chinmay.project.Test_your_brain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chinmay.project.R;
import com.example.chinmay.project.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class test_your_brain_LevelChooser extends Activity
{
    Button btnL0, btnL1, btnL2, btnL3, btnL4;
    ImageButton btnExit, btnSet;
    TextView ins1;
    Intent intent, intentSettings;
//    Bundle b = new Bundle();

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.test_your_brain_activity_start);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        btnL0=(Button)findViewById(R.id.btnLevel0);
        btnL1=(Button)findViewById(R.id.btnLevel1);
        btnL2=(Button)findViewById(R.id.btnLevel2);
        btnL3=(Button)findViewById(R.id.btnLevel3);
        btnL4=(Button)findViewById(R.id.btnLevel4);
        btnSet=(ImageButton)findViewById(R.id.btnSettings);
        btnExit=(ImageButton)findViewById(R.id.btnExit);

        ins1=(TextView)findViewById(R.id.txtIns1);

        disable_played_levels();
        addListenerOnButton();

    }

    private void disable_played_levels()
    {
        String s = new SharedPreference("test").getString( test_your_brain_LevelChooser.this, "{\"list\" : [\"score_0\", \"score_1\" , \"score_2\", \"score_3\", \"score_4\"]}");
        try {
            JSONObject j = new JSONObject(s);
            if (!j.getString("score_0").equals(""))
                btnL0.setVisibility(View.GONE);
            if (!j.getString("score_1").equals(""))
                btnL1.setVisibility(View.GONE);
            if (!j.getString("score_2").equals(""))
                btnL2.setVisibility(View.GONE);
            if (!j.getString("score_3").equals(""))
                btnL3.setVisibility(View.GONE);
            if (!j.getString("score_4").equals(""))
                btnL4.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void addListenerOnButton()
    {
        btnL1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                intent = new Intent(test_your_brain_LevelChooser.this, test_your_brain_StartActivity.class);
                intent.putExtra("level",1);
                startActivity(intent);
                finish();

            }
        });

        btnL0.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                intent = new Intent(test_your_brain_LevelChooser.this, test_your_brain_StartActivity.class);
                intent.putExtra("level",0);
                startActivity(intent);
                finish();

            }
        });



        btnL2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                intent = new Intent(test_your_brain_LevelChooser.this, test_your_brain_StartActivity.class);
                intent.putExtra("level",2);
                startActivity(intent);
                finish();

            }
        });

        btnL3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
               intent = new Intent(test_your_brain_LevelChooser.this, test_your_brain_StartActivity.class);
               intent.putExtra("level", 3);
                startActivity(intent);
                finish();

            }
        });

        btnL4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
               intent = new Intent(test_your_brain_LevelChooser.this, test_your_brain_StartActivity.class);
                intent.putExtra("level", 4);
                startActivity(intent);
                finish();

            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    intentSettings = new Intent(test_your_brain_LevelChooser.this, test_your_brain_Settings.class);

                    //intent.putExtra("level", level);
                    startActivity(intentSettings);
                    finish();
                }
            });
        btnExit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}
