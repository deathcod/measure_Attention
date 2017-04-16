package com.example.chinmay.project.Stroop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.chinmay.project.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by chinmay on 27-Jan-17.
 */

public class stroop_game_play extends Activity
{
    ImageButton play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stroop_game_play);
        play = (ImageButton) findViewById(R.id.play);
        addListenerOnButton();
    }

    public void addListenerOnButton()
    {
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(stroop_game_play.this, stroop_game_screen.class);
                startActivity(i);
            }
        });
    }
}