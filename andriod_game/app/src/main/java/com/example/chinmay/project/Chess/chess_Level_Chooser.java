package com.example.chinmay.project.Chess;

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
import com.example.chinmay.project.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class chess_Level_Chooser extends Activity {

    Button l1, l2, l3, l4;
    ImageButton helpL1, helpL2, helpL3, helpL4, back, exit;
    AlertDialog adl;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.chess_level_chooser);

        l1 = (Button) findViewById(R.id.btnLevel1);
        l2 = (Button) findViewById(R.id.btnLevel2);
        l3 = (Button) findViewById(R.id.btnLevel3);
        l4 = (Button) findViewById(R.id.btnLevel4);

        helpL1 = (ImageButton) findViewById(R.id.btnHelpL1);
        helpL2 = (ImageButton) findViewById(R.id.btnHelpL2);
        helpL3 = (ImageButton) findViewById(R.id.btnHelpL3);
        helpL4 = (ImageButton) findViewById(R.id.btnHelpL4);

        back = (ImageButton) findViewById(R.id.btnBack);
        exit = (ImageButton) findViewById(R.id.btnExit);

        disable_played_levels();
        addListenerOnButton();
    }

    private void set_invisible(Button x, ImageButton y)
    {
        x.setVisibility(View.GONE);
        y.setVisibility(View.GONE);
    }

    private void disable_played_levels()
    {
        String s = new SharedPreference("chess").getString( chess_Level_Chooser.this, "{\"list\" : [\"score_1\" , \"score_2\", \"score_3\", \"score_4\"]}");
        try {
            JSONObject j = new JSONObject(s);
            if (!j.getString("score_1").equals(""))
                set_invisible(l1, helpL1);
            if (!j.getString("score_2").equals(""))
                set_invisible(l2, helpL2);
            if (!j.getString("score_3").equals(""))
                set_invisible(l3, helpL3);
            if (!j.getString("score_4").equals(""))
                set_invisible(l4, helpL4);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void addListenerOnButton() {

        l1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i = new Intent(chess_Level_Chooser.this, chess_MainActivity.class);
                i.putExtra("level", 1);
                startActivity(i);
                finish();
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i = new Intent(chess_Level_Chooser.this, chess_MainActivity.class);
                i.putExtra("level", 2);
                startActivity(i);
                finish();
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i = new Intent(chess_Level_Chooser.this, chess_MainActivity.class);
                i.putExtra("level", 3);
                startActivity(i);
                finish();
            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                i = new Intent(chess_Level_Chooser.this, chess_MainActivity.class);
                i.putExtra("level", 4);
                startActivity(i);
                finish();
            }
        });


        helpL1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
                LinearLayout ll = new LinearLayout(getApplicationContext());
                adl = new AlertDialog.Builder(b.getContext()).setView(ll).setTitle("Instructions").setMessage("6 types of chess objects shall appear in the screen for 10 seconds. Try to remember the 'left to right order' in which they appear").show();
            }
        });

        helpL2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
                LinearLayout ll = new LinearLayout(getApplicationContext());
                adl = new AlertDialog.Builder(b.getContext()).setView(ll).setTitle("Instructions").setMessage("6 types of chess objects shall appear in the screen for 10 seconds. Try to remember the 'right to left order' in which they appear").show();
            }
        });

        helpL3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
                LinearLayout ll = new LinearLayout(getApplicationContext());
                adl = new AlertDialog.Builder(b.getContext()).setView(ll).setTitle("Instructions").setMessage("6 types of chess objects shall appear in the screen for 10 seconds. Try to remember the 'left to right order' in which they appear").show();
            }
        });

        helpL4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
                LinearLayout ll = new LinearLayout(getApplicationContext());
                adl = new AlertDialog.Builder(b.getContext()).setView(ll).setTitle("Instructions").setMessage("6 types of chess objects shall appear in the screen for 10 seconds. Try to remember the 'right to left order' in which they appear").show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
                i = new Intent(chess_Level_Chooser.this, chess_First_Page.class);
                startActivity(i);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View b) {
                finish();
            }
        });
    }
}

