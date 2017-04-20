package com.example.chinmay.project.Test_your_brain;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.chinmay.project.R;

public class test_your_brain_FirstPage extends Activity
{
    Button btnplay;
    Intent i;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.test_your_brain_first_page);
        btnplay=(Button)findViewById(R.id.playBtn);
        addListenerOnButton();
    }

    public void addListenerOnButton()
    {
        btnplay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                i = new Intent(test_your_brain_FirstPage.this, test_your_brain_LevelChooser.class);
                startActivity(i);
            }
        });

    }
}
