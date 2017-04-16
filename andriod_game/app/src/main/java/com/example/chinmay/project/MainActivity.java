package com.example.chinmay.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chinmay.project.Brain_challenge.brain_game_game_screen;
import com.example.chinmay.project.Stroop.stroop_game_play;

public class MainActivity extends Activity {

    Button stroop, settings, brain_game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stroop = (Button)findViewById(R.id.stroop);
        brain_game = (Button) findViewById(R.id.brain_game);
        settings = (Button) findViewById(R.id.settings);
        addListenerOnButton();
    }

    public void addListenerOnButton()
    {
        stroop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, stroop_game_play.class);
                startActivity(i);
            }
        });

        brain_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, brain_game_game_screen.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, settings.class);
                startActivity(i);
            }
        });
    }

}
