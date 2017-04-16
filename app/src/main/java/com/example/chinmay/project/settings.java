package com.example.chinmay.project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chinmay.project.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.chinmay.project.R.layout.stroop_game_play;

/**
 * Created by chinmay on 06-Apr-17.
 */
public class settings extends Activity{

    final String MY_PREF_NAME = "MyPrefFile";
    SharedPreference sp = new SharedPreference();
    TextView name, IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_settings);

        name = (TextView)findViewById(R.id.name);
        IP = (TextView)findViewById(R.id.IP);

        try {
            JSONObject settings = new JSONObject(sp.getString(com.example.chinmay.project.settings.this, "{\"list\" : [\"name\" , \"ip\"]}"));

            if(settings.getString("name").equals(""))
                name.setText("Shaktimaan");
            else
                name.setText(settings.getString("name"));

            if(settings.getString("ip").equals(""))
                IP.setText("192.168.1.112");
            else
                IP.setText(settings.getString("ip"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button home = (Button)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(settings.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        final Button update = (Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setEnabled(false);
                String remark = sp.set_settings(settings.this, "{\"name\" : \""+name.getText().toString()+"\", \"ip\" : \""+ IP.getText().toString() +"\"}");
                Toast.makeText(settings.this,remark, Toast.LENGTH_SHORT).show();
                update.setEnabled(true);
            }
        });


    }
}
