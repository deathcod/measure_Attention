package com.example.chinmay.project.Chess;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.chinmay.project.R;

public class chess_First_Page extends Activity {
    ImageView iv;
    ImageButton play, exit, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.chess_first_page);

        iv = (ImageView) findViewById(R.id.imgAll);
        exit = (ImageButton) findViewById(R.id.exit);
        play = (ImageButton) findViewById(R.id.play);

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
                finish();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
                Intent i = new Intent(chess_First_Page.this, chess_Level_Chooser.class);
                startActivity(i);
                finish();
            }
        });
    }

}
