package com.myapp.subhnand.brain_challenges;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (ImageButton) findViewById(R.id.start_button);
        addListenerOnButton();
    }
    public void addListenerOnButton() {
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, level.class);
                startActivity(i);
            }
        });
}
}
