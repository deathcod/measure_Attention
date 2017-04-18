package com.example.chinmay.project.Card_game;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chinmay.project.R;

public class FirstScreen extends Activity {

    ImageButton play,exit;
    Intent i;
    TextView tTimer;

    public void addListenerOnButton() {
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i = new Intent(FirstScreen.this, LevelChooser.class);
                i.putExtra("times",0);
                startActivity(i);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.first_screen);
        tTimer=(TextView)findViewById(R.id.txtTimer);
        play=(ImageButton)findViewById(R.id.playBtn);
        exit=(ImageButton)findViewById(R.id.exitBtn);

        //for(int i=1;i<=10000;i++)
        //    for(int j=1;j<=10000;j++);

        try
        {addListenerOnButton();}
        catch (Exception e){
            System.out.print("Error");}
    }



}