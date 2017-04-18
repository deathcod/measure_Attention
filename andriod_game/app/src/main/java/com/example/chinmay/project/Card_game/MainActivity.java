package com.example.chinmay.project.Card_game;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(MainActivity.this, LevelChooser.class);
      //  i.putExtra("times",0);
//        for(int j=1;j<=5000000;j++);
        startActivity(i);
        finish();

    }
}
