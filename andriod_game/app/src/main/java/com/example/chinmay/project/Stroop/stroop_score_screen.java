package com.example.chinmay.project.Stroop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chinmay.project.MainActivity;
import com.example.chinmay.project.R;
import com.example.chinmay.project.SharedPreference;

/**
 * Created by chinmay on 28-Jan-17.
 */

public class stroop_score_screen extends Activity {

    String SCORE;
    String TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stroop_score_screen);

        Bundle bundle = getIntent().getExtras();
        long time = bundle.getLong("time");
        int correct = bundle.getInt("correct");
        int wrong = bundle.getInt("wrong");

        TIME = Double.toString((double)Math.round((double)time/10)/100);
        SCORE = Integer.toString(correct);

        TextView t = (TextView)findViewById(R.id.time);
        t.setText(TIME + " s");
        t = (TextView)findViewById(R.id.correct);
        t.setText(SCORE + "/10");
        t = (TextView)findViewById(R.id.wrong);
        t.setText(Integer.toString(wrong)+"/10");

        Button buttonpane1 = (Button)findViewById(R.id.button);
        buttonpane1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(stroop_score_screen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public void onStart()
    {
        super.onStart();
        String remark = new SharedPreference("stroop").set_Stroop_score(this, SCORE, TIME);
        Toast.makeText(stroop_score_screen.this,remark, Toast.LENGTH_SHORT).show();
    }


}
