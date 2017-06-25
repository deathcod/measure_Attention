package info.androidhive.navigationdrawer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.other.SharedPreference;

public class LocalDataActivity extends AppCompatActivity {

    TextView text_stroop, text_chess, text_card_game, text_test_your_brain, text_brain_challenge;
    SharedPreference sp;
    int RadioCheckButton[] = {
            R.id.check_stroop,
            R.id.check_chess,
            R.id.check_card_game,
            R.id.check_test_your_brain,
            R.id.check_brain_challenge
    };
    Button check_upload, check_clear;
    RadioGroup check_game;

    int TIME_OUT_SUPER_FAST = 1000;
    int TIME_OUT_FAST = 5000;
    int TIME_OUT_MEDIUM = 10000;
    int TIME_OUT_SLOW = 20000;
    int TIME_OUT_SUPER_SLOW = 30000;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handler = new android.os.Handler();

        check_game = (RadioGroup) findViewById(R.id.check_game);

        text_stroop = (TextView) findViewById(R.id.text_stroop);
        text_chess = (TextView) findViewById(R.id.text_chess);
        text_card_game = (TextView) findViewById(R.id.text_card_game);
        text_test_your_brain = (TextView) findViewById(R.id.text_test_your_brain);
        text_brain_challenge = (TextView) findViewById(R.id.text_brain_challenge);

        check_upload = (Button) findViewById(R.id.check_upload);
        check_clear = (Button) findViewById(R.id.check_clear);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // get the string array from string.xml
        String[] game_names = this.getResources().getStringArray(R.array.nav_item_activity_titles);
        int total_no_of_games = game_names.length - 1;

        //game flag from the shared Preference of each game
        int[] game_flag = new int[total_no_of_games];

        //game data from the shared Preference of each game
        int[] game_data = new int[total_no_of_games];

        for (int i = 0; i < game_names.length; i++) {
            game_names[i] = game_names[i].toLowerCase();
            game_names[i] = game_names[i].trim();
            game_names[i] = game_names[i].replace(" ", "_");
        }

        //get the data in each shared preference and also the flag status
        for (int i = 0; i < total_no_of_games; i++) {
            sp = new SharedPreference(game_names[i]);
            game_flag[i] = Integer.parseInt(sp.get_one_String(this, "flag"));
            game_data[i] = sp.get_local_data_length(this);
        }

        //set the data on the text view
        text_stroop.setText(Integer.toString(game_data[0]));
        text_chess.setText(Integer.toString(game_data[1]));
        text_card_game.setText(Integer.toString(game_data[2]));
        text_test_your_brain.setText(Integer.toString(game_data[3]));
        text_brain_challenge.setText(Integer.toString(game_data[4]));

        //This stores the first index which is clickable check button
        int can_click_button = -1;
        for (int i = 0; i < total_no_of_games; i++) {
            if (game_flag[i] == 1 || game_data[i] == 0)
                findViewById(RadioCheckButton[i]).setClickable(false);
            else
                can_click_button = i;
        }

        if (can_click_button != -1) {
            check_game.check(check_game.getChildAt(can_click_button).getId());
            addListenerOnButton();
        }
    }

    private void addListenerOnButton() {
        check_upload.setClickable(true);
        check_clear.setClickable(true);

        int selectionId = check_game.getCheckedRadioButtonId();
        RadioButton check_game_button = (RadioButton) findViewById(selectionId);
        final String game_name = check_game_button.getText().toString().toLowerCase().replace(" ", "_");
        sp = new SharedPreference(game_name);

        check_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO This has to be refactored as games name shouldn't be written exclusively here. Think for dynamicity!!
                int set_time_out = TIME_OUT_MEDIUM;
                if (game_name.equals("stroop") || game_name.equals("brain_challenge"))
                    set_time_out = TIME_OUT_FAST;
                else if(game_name.equals("test_your_brain"))
                    set_time_out = TIME_OUT_SLOW;

                sp.upload_local_data(LocalDataActivity.this);
                check_upload.setClickable(false);
                delay(set_time_out, R.string.cs);

            }
        });

        check_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.clear_load_data(LocalDataActivity.this);
                check_clear.setClickable(false);
                delay(TIME_OUT_SUPER_FAST, R.string.cd);
            }
        });

    }

    private void delay(int timeout, int String_id) {
        final ProgressDialog progressDialog = new ProgressDialog(LocalDataActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(String_id));
        progressDialog.show();

        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        Intent i = new Intent(LocalDataActivity.this, LocalDataActivity.class);
                        startActivity(i);
                        finish();
                        progressDialog.dismiss();
                    }
                }, timeout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        finish();
    }
}
