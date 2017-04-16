package com.example.chinmay.project.Brain_challenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.chinmay.project.R;

import java.util.Random;

public class brain_game_game_screen
        extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    final Random rnd = new Random();
    private int time1 = rnd.nextInt(7000) + SPLASH_TIME_OUT;

    protected final static int getResourceID
            (final String resName, final String resType, final Context ctx) {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0) {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        } else {
            return ResourceID;
        }
    }

    @Override
    protected void onCreate(
            final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.brain_game_game_screen);
        final ImageView img = (ImageView) findViewById(R.id.imgRandom);
        // I have 4 images named img0 to img3, so...
        final String str = "brain_game_img" + rnd.nextInt(9);
        //time1=rnd1.nextInt(10000-30000)+30000;
        img.setImageDrawable
                (
                        getResources().getDrawable(brain_game_game_screen.getResourceID(str, "drawable",
                                getApplicationContext()))
                );


        boolean b = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //final Random r = new Random();
                //timer = r.nextInt(3000000) + 3000000;
                Intent s = new Intent(brain_game_game_screen.this, brain_game_quiz_screen.class);
                s.putExtra("time", time1);
                startActivity(s);
            }
        }, time1);
        //final Random r = new Random();
        /*int timer=1000;
        for (int p=1;p<timer;p++)
        {


            if (p==timer-1)
            {
                img.setImageDrawable
                        (
                                getResources().getDrawable(game_screen.getResourceID(str, "drawable",
                                        getApplicationContext()))
                        );
                Intent s = new Intent(game_screen.this, MainActivity.class);
                //i.putExtra("time",timer);
                startActivity(s);
            }
        }*/

    }
}
