package com.example.chinmay.project.Card_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.chinmay.project.R;


public class card_game_LevelChooser extends Activity {

    ImageButton help, exit;
    Intent i;
    AlertDialog adl;
    Button l1, l2, l3, l4;
    int times;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b1 = getIntent().getExtras();
        times = b1.getInt("times");
        if (times > 0 && times < 12) {
            i = new Intent(card_game_LevelChooser.this, card_game_Game.class);
            i.putExtra("level", times / 3 + 1);
            i.putExtra("times", ++times);
            startActivity(i);
            finish();
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.card_game_level_chooser);
        help = (ImageButton) findViewById(R.id.helpBtn);
        exit = (ImageButton) findViewById(R.id.exitBtn);
        l1 = (Button) findViewById(R.id.btnLevel1);
        l2 = (Button) findViewById(R.id.btnLevel2);
        l3 = (Button) findViewById(R.id.btnLevel3);
        l4 = (Button) findViewById(R.id.btnLevel4);

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        l1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i = new Intent(card_game_LevelChooser.this, card_game_Game.class);
                i.putExtra("level", 1);
                i.putExtra("times", 1);
                startActivity(i);
                finish();
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i = new Intent(card_game_LevelChooser.this, card_game_Game.class);
                i.putExtra("level", 2);
                i.putExtra("times", 4);
                startActivity(i);
                finish();
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i = new Intent(card_game_LevelChooser.this, card_game_Game.class);
                i.putExtra("level", 3);
                i.putExtra("times", 7);
                startActivity(i);
                finish();
            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i = new Intent(card_game_LevelChooser.this, card_game_Game.class);
                i.putExtra("level", 4);
                i.putExtra("times", 10);
                startActivity(i);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //i = new Intent(LevelChooser.this, MainActivity.class);
                //startActivity(i);
                finish();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
                LinearLayout ll = new LinearLayout(getApplicationContext());
                adl = new AlertDialog.Builder(b.getContext()).setView(ll).setTitle("Instructions").setMessage("Correctly identify and tap the cards that appear for 10 seconds in the next screen.").show();
            }
        });

    }
}
