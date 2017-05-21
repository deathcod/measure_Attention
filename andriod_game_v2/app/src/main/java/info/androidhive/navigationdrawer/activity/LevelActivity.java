package info.androidhive.navigationdrawer.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.androidhive.navigationdrawer.R;

/**
 * Created by chinmay on 16-May-17.
 */

public class LevelActivity extends Activity {

    public static int getStringIdentifier(Context context, String resource, String name) {
        return context.getResources().getIdentifier(name, resource, context.getPackageName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        final Bundle b1 = getIntent().getExtras();

        final String game_name = b1.getString("game_name");
        String game_level = b1.getString("game_level");
        String game_orientation = b1.getString("game_orientation");

        //LEVEL text set
        TextView game_level_view = (TextView) findViewById(R.id.game_level);
//        game_level_view.setText("LEVEL "+game_level);
        game_level_view.setText(game_level);

        //LEVEL instruction set
        TextView game_instruction = (TextView) findViewById(R.id.game_instruction);
        game_instruction.setText(getResources().getString(getStringIdentifier(LevelActivity.this, "string", game_name + "_ins_L" + game_level)));

        //LEVEL orientation set
        if (game_orientation.equals("landscape"))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Creating the background
        LinearLayout mL = (LinearLayout) findViewById(R.id.level_background);
        String s = (game_orientation.equals("landscape")) ? "l" : "p";
        s += "_" + game_level;

        mL.setBackgroundResource(getStringIdentifier(LevelActivity.this, "drawable", s));

        //LEVEL button set
        final Button button = (Button) findViewById(R.id.game_play);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String class_name = "info.androidhive.navigationdrawer.";
                    class_name += Character.toUpperCase(game_name.charAt(0));
                    class_name += game_name.substring(1);
                    class_name += "." + game_name + "_game_screen";

                    final Intent i = new Intent(LevelActivity.this, Class.forName(class_name));
                    i.putExtras(b1);

                    final ProgressDialog progressDialog = new ProgressDialog(LevelActivity.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage(getString(R.string.l));
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {

                                    startActivity(i);
                                    finish();
                                    progressDialog.dismiss();
                                }
                            }, 1000);


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                button.setClickable(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LevelActivity.this, MainActivity.class);
        startActivity(i);
    }
}
