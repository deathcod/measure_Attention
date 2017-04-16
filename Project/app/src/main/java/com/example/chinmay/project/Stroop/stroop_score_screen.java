package com.example.chinmay.project.Stroop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Long2;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chinmay.project.FetchData;
import com.example.chinmay.project.MainActivity;
import com.example.chinmay.project.R;
import com.example.chinmay.project.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

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
        String remark = new SharedPreference().set_Stroop_score(this, SCORE, TIME);
        Toast.makeText(stroop_score_screen.this,remark, Toast.LENGTH_SHORT).show();
    }


}
