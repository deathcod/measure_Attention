package info.androidhive.navigationdrawer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import info.androidhive.navigationdrawer.R;

/**
 * Created by chinmay on 18-May-17.
 */

public class ScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Bundle b1 = getIntent().getExtras();
        String s = Integer.toString(b1.getString("data").length());
        s += "\n\n" + b1.getString("data");

        TextView t1 = (TextView) findViewById(R.id.score);
        t1.setMovementMethod(new ScrollingMovementMethod());
        t1.setText(s);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ScoreActivity.this, MainActivity.class);
        startActivity(i);
    }
}
