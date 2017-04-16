package com.example.chinmay.project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.chinmay.project.Stroop.*;

public class MainActivity extends Activity {

    Button stroop,settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stroop = (Button)findViewById(R.id.stroop);
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

        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, settings.class);
                startActivity(i);
            }
        });
    }

}
