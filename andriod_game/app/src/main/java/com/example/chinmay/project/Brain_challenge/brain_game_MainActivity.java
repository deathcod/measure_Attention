package com.example.chinmay.project.Brain_challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.chinmay.project.R;

public class brain_game_MainActivity extends Activity {
    ImageButton start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brain_game_activity_main);
        start = (ImageButton) findViewById(R.id.start_button);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(brain_game_MainActivity.this, brain_game_game_screen.class);
                startActivity(i);
            }
        });
    }
}
