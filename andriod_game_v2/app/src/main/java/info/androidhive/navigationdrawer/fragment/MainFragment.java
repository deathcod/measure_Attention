package info.androidhive.navigationdrawer.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.GIFView;
import info.androidhive.navigationdrawer.other.JSONData;
import info.androidhive.navigationdrawer.other.SharedPreference;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Button game_enter = null;
    Handler handler;
    Runnable runnable;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static int getStringIdentifier(Context context, String resource, String name) {
        return context.getResources().getIdentifier(name, resource, context.getPackageName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            handler = new android.os.Handler();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout mL;
        // Inflate the layout for this fragment
        switch (mParam1) {
            case "settings":
                return settings((LinearLayout) inflater.inflate(R.layout.project_setting, container, false));


            default:
                mL = (LinearLayout) inflater.inflate(R.layout.main_fragment, container, false);
                TextView game_instruction = (TextView) mL.findViewById(R.id.game_name);
                GIFView gifView = (GIFView) mL.findViewById(R.id.nature_gif);
                gifView.setGifImageResource(R.drawable.nature);
                game_instruction.setText(getStringIdentifier(getActivity(), "string", "detail_" + mParam1));
                final Button button = (Button) mL.findViewById(R.id.game_enter);
                button.setText("ENTER");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //The focus fragment added
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .replace(R.id.frame, new FocusFragment().newInstance(mParam1, mParam2))
                                .commit();
                    }
                });
                return mL;
        }
    }

    private LinearLayout settings(final LinearLayout mL) {
        final TextView name = (TextView) mL.findViewById(R.id.name);
        final TextView email = (TextView) mL.findViewById(R.id.email);
        final TextView college = (TextView) mL.findViewById(R.id.college);
        final TextView semester = (TextView) mL.findViewById(R.id.semester);
        final TextView age = (TextView) mL.findViewById(R.id.age);
        final RadioGroup sex = (RadioGroup) mL.findViewById(R.id.sex);
        final RadioGroup hand = (RadioGroup) mL.findViewById(R.id.hand);
        final TextView IP = (TextView) mL.findViewById(R.id.IP);
        Button send_data = (Button) mL.findViewById(R.id.send_data);

        //calling the shared preference
        final SharedPreference sp = new SharedPreference("settings");
        if (!sp.get_one_String(getActivity(), "user_id").equals("")) {
            name.setText(sp.get_one_String(getActivity(), "name"));
            email.setText(sp.get_one_String(getActivity(), "email"));
            college.setText(sp.get_one_String(getActivity(), "college"));
            semester.setText(sp.get_one_String(getActivity(), "semester"));
            age.setText(sp.get_one_String(getActivity(), "age"));
            IP.setText(sp.get_one_String(getActivity(), "ip"));
            send_data.setText("update");

            if (sp.get_one_String(getActivity(), "sex").equals("male"))
                sex.check(sex.getChildAt(0).getId());
            else
                sex.check(sex.getChildAt(1).getId());

            if (sp.get_one_String(getActivity(), "hand").equals("right"))
                hand.check(hand.getChildAt(0).getId());
            else
                hand.check(hand.getChildAt(1).getId());
        }

        send_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if any fail case occured
                boolean flag_fail = false;

                //name check
                if (name.getText().toString().isEmpty() || name.getText().toString().length() <= 3) {
                    name.setError("enter a name of atleast 3 character");
                    flag_fail = true;
                } else
                    name.setError(null);

                //email check
                if (email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("enter a valid email");
                    flag_fail = true;
                } else
                    email.setError(null);


                //college check
                if (college.getText().toString().isEmpty()) {
                    college.setError("enter a valid college name");
                    flag_fail = true;
                } else
                    college.setError(null);

                //semester check
                if (semester.getText().toString().isEmpty()) {
                    semester.setError("enter a valid semester");
                    flag_fail = true;
                } else
                    semester.setError(null);
                age.setError("enter a valid age");

                //age check
                if (age.getText().toString().isEmpty()) {
                    flag_fail = true;
                } else if (Integer.parseInt(age.getText().toString()) > 123 || Integer.parseInt(age.getText().toString()) < 1) {
                    age.setError("why don't you apply in Guinness Book!!");
                    flag_fail = true;
                } else
                    age.setError(null);

                //IP check
                if (IP.getText().toString().isEmpty() || !Patterns.IP_ADDRESS.matcher(IP.getText().toString()).matches()) {
                    IP.setError("enter a valid IP");
                    flag_fail = true;
                } else
                    IP.setError(null);

                if (flag_fail)
                    return;

                //make JSONObject
                JSONData x = new JSONData("JO");
                x.set_extra_data("name", name.getText().toString().trim());
                x.set_extra_data("email", email.getText().toString().trim());
                x.set_extra_data("college", college.getText().toString().trim());
                x.set_extra_data("semester", semester.getText().toString().trim());
                x.set_extra_data("age", age.getText().toString().trim());
                x.set_extra_data("ip", IP.getText().toString().trim());
                x.set_extra_data("token", sp.get_one_String(getActivity(), "token"));

                int selectedId = sex.getCheckedRadioButtonId();
                Button radioButtonSex = (RadioButton) mL.findViewById(selectedId);
                x.set_extra_data("sex", radioButtonSex.getText().toString().toLowerCase());

                selectedId = hand.getCheckedRadioButtonId();
                Button radioButtonHand = (RadioButton) mL.findViewById(selectedId);
                x.set_extra_data("hand", radioButtonHand.getText().toString().toLowerCase());

                final Intent i = new Intent(getActivity(), MainActivity.class);
                //i.putExtra("data", x.get_data_string());

                sp.set_settings(x.get_data_string_without_intend());
                sp.async_response_modified(getActivity(), 10000, true);

                final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(getString(R.string.cs));
                progressDialog.show();

                runnable = new Runnable() {
                    public void run() {
                        startActivity(i);

                        //checking if the data has responded or not
                        if (sp.is_connected())
                            sp.set_settings_after_call(getActivity());
                        progressDialog.dismiss();
                    }
                };
                handler.postDelayed(
                        runnable, 10000);

            }
        });

        //

        return mL;
    }
    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}