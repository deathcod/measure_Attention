package com.myapp.subhnand.brain_challenges;

/**
 * Created by dell on 23/4/17.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class level extends Activity {

   // ImageButton exit;
    Button l1, l2, l3, l4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
        // exit = (ImageButton) findViewById(R.id.exitBtn);
        l1 = (Button) findViewById(R.id.btnLevel1);
        l2 = (Button) findViewById(R.id.btnLevel2);
        l3 = (Button) findViewById(R.id.btnLevel3);
        l4 = (Button) findViewById(R.id.btnLevel4);
        addListenerOnButton();
    }
/*
*
*
*each one of l1,l2,l3 represents level1,level2,level3 buttons respectively and below are onclick functions for each of them*/
    public void addListenerOnButton() {

        l1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(level.this, game_screen.class);
                i.putExtra("level", 1);
                i.putExtra("game_name", "brain_challenge");
                startActivity(i);
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(level.this, game_screen.class);
                i.putExtra("level", 2);
                i.putExtra("game_name", "brain_challenge");
                startActivity(i);
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(level.this, game_screen.class);
                i.putExtra("level", 3);
                i.putExtra("game_name", "brain_challenge");
                startActivity(i);
            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(level.this, game_screen.class);
                i.putExtra("level", 4);
                i.putExtra("game_name", "brain_challenge");
                startActivity(i);
            }
        });



       /* exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //i = new Intent(LevelChooser.this, MainActivity.class);
                //startActivity(i);
                finish();
            }
        }); */


    }
}