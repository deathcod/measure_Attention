package info.androidhive.navigationdrawer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.navigationdrawer.R;

/**
 * Created by chinmay on 18-May-17.
 */

public class ScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        LinearLayout mL = (LinearLayout) findViewById(R.id.activity_LL_score);
        Bundle b1 = getIntent().getExtras();
        String s = b1.getString("data");


        try {
            JSONArray JA = new JSONArray(s);
            for (int i = 0; i < JA.length(); i++) {
                JSONObject JO = JA.optJSONObject(i);

                //LinearLayout child_mL = (LinearLayout) findViewById(R.id.score_xml);
                View child_mL = getLayoutInflater().inflate(R.layout.score, null);

                TextView score = (TextView) child_mL.findViewById(R.id.activity_score);
                score.setText(JO.getString("score"));

                TextView level = (TextView) child_mL.findViewById(R.id.activity_level);
                level.setText(Integer.toString(i + 1));

                TextView time = (TextView) child_mL.findViewById(R.id.activity_time);
                time.setText(JO.getString("time") + " ms");

                TextView correct = (TextView) child_mL.findViewById(R.id.activity_correct);
                correct.setText(JO.getString("correct"));

                TextView wrong = (TextView) child_mL.findViewById(R.id.activity_wrong);
                wrong.setText(JO.getString("wrong"));


                mL.addView(child_mL);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ScoreActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
