package info.androidhive.navigationdrawer.Stroop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.activity.ScoreActivity;
import info.androidhive.navigationdrawer.other.JSONData;
import info.androidhive.navigationdrawer.other.SharedPreference;

/**
 * Created by chinmay on 27-Jan-17.
 */

public class stroop_game_screen extends Activity {

    Button b[] = new Button[4];
    Bundle b1;

    int button_id[] = {
            R.id.buttonPane1,
            R.id.buttonPane2,
            R.id.buttonPane3,
            R.id.buttonPane4};

    TextView text;
    String color[][] = {
            {"black", "#1e1e1e"},
            {"blue", "#FF33B5E5"},
            {"green", "#FF99CC00"},
            {"orange", "#FFFFBB33"},
            {"yellow", "#ffff00"},
            {"red", "#FFFF4444"},
            {"Pink", "#FFC0CB"},
            {"White", "#FFFFFF"},
            {"Violet", "#EE82EE"},
            {"Brown", "#A52A2A"},
    };

    int a[] = new int[color.length];
    int randomPick_colors[];
    int randomPick_color_text;
    int randomPick_color_color;
    int correct = 0;
    int wrong = 0;
    int count = 0;
    long now, then;
    JSONData click_detail;

    public static int[] pickNrandom(int[] array, int n) {
        List<Integer> list = new ArrayList<Integer>(array.length);
        for (int i : array)
            list.add(i);
        Collections.shuffle(list);

        int[] answer = new int[n];
        for (int i = 0; i < n; i++)
            answer[i] = list.get(i);

        return answer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stroop_game_screen);

        for (int i = 0; i < 4; i++)
            b[i] = (Button) findViewById(button_id[i]);
        text = (TextView) findViewById(R.id.text);
        for (int i = 0; i < color.length; i++)
            a[i] = i;

        click_detail = new JSONData("JA");
        b1 = getIntent().getExtras();
    }

    @Override
    protected void onStart() {

        super.onStart();
        now = System.currentTimeMillis();
        change();
    }

    public void change() {
        if (count == 10) {
            for (int i = 0; i < 4; i++)
                b[i].setEnabled(false);

            then = System.currentTimeMillis();
            long difftime = then - now;
            final Intent i = new Intent(stroop_game_screen.this, ScoreActivity.class);

            JSONData data = new JSONData("JA", b1.getString("data"));
            data.set_level_data(Integer.toString(correct), Integer.toString(wrong), Integer.toString(correct), Long.toString(difftime), click_detail.get_data_JA());

            b1.remove("data");
            i.putExtras(b1);
            i.putExtra("data", data.get_data_string());


            final SharedPreference sp = new SharedPreference(b1.getString("game_name"));
            sp.set_game_score(stroop_game_screen.this, data.get_data_JA());
            sp.async_response_modified(stroop_game_screen.this);

            final ProgressDialog progressDialog = new ProgressDialog(stroop_game_screen.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getString((R.string.cs)));
            progressDialog.show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            startActivity(i);
//                            if (sp.fetchData.flag == 0)
//                                Toast.makeText(stroop_game_screen.this, "network_error", Toast.LENGTH_SHORT).show();
//                            else
//                                Toast.makeText(stroop_game_screen.this, "successfully uploaded", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(stroop_game_screen.this, remark, Toast.LENGTH_LONG).show();
                            finish();
                            progressDialog.dismiss();
                        }
                    }, 5000);

        }

        randomPick_colors = pickNrandom(a, 4);
        randomPick_color_text = pickNrandom(randomPick_colors, 1)[0];

        for (int i = 0; i < 4; i++)
            b[i].setText(color[randomPick_colors[i]][0]);

        if (count <= 3)
            randomPick_color_color = randomPick_color_text;
        else {
            do {
                randomPick_color_color = pickNrandom(randomPick_colors, 1)[0];
            } while (randomPick_color_color == randomPick_color_text);
        }
        text.setText(color[randomPick_color_text][0]);
        text.setTextColor(Color.parseColor(color[randomPick_color_color][1]));
        addListenerOnButton();
        count++;
    }

    public void addListenerOnButton() {
        for (int i = 0; i < 4; i++) {
            final int k = i;
            b[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int flag = 0;
                    if (randomPick_colors[k] == randomPick_color_color) {
                        correct++;
                        flag = 1;
                    } else
                        wrong++;
                    //set the click_details
                    click_detail.set_click_details(Long.toString(System.currentTimeMillis() / 1000), (flag == 0) ? "W" : "C");

                    change();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(stroop_game_screen.this, MainActivity.class);
        startActivity(i);
    }
}
