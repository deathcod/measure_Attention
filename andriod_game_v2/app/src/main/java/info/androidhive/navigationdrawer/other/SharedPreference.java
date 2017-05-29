package info.androidhive.navigationdrawer.other;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import info.androidhive.navigationdrawer.R;

/**
 * Created by chinmay on 14-Apr-17.
 */

public class SharedPreference extends Activity {
    public FetchData fetchData;
    private String IP;
    private String HOSTNAME;
    private String DIR;
    private String API;
    private String GAME_NAME;
    private String DATA;

    public SharedPreference(String game_name) {
        GAME_NAME = game_name;
        DIR = "project/userapi/";
        HOSTNAME = "http://";
    }

    public void async_response_modified() {
        fetchData = new FetchData(API, DATA);
        fetchData.execute();
    }

    private void putString(Context context, String s) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences(GAME_NAME, MODE_PRIVATE).edit();
            JSONObject j = new JSONObject(s);
            Iterator<String> iter = j.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                String value = j.getString(key);
                Log.d(":", key + " " + value);
                editor.putString(key, value);
            }
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void clear_shared_preferences(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(GAME_NAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    private String generate_token() {
        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final int N = alphabet.length();
        Random r = new Random();

        String token = "";
        for (int i = 0; i < 20; i++)
            token += alphabet.charAt(r.nextInt(N));

        return token;
    }

    public String getString(Context context, String s) {
        String ret = "{}";
        try {
            SharedPreferences editor = context.getSharedPreferences(GAME_NAME, MODE_PRIVATE);

            JSONArray j = new JSONObject(s).getJSONArray("list");// get the json under the list {"list" : [, , ,]}
            JSONObject ret_json = new JSONObject();
            for (int i = 0; i < j.length(); i++) {
                String val = j.getString(i);
                ret_json.put(val, editor.getString(val, ""));
            }
            ret = ret_json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private String query_string(String s) {
        try {
            JSONObject j = new JSONObject(s);
            List<String> l = new ArrayList<String>();
            Iterator<String> iter = j.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                String value = j.getString(key);
                try {
                    key = URLEncoder.encode(key, "utf-8");
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                l.add(key + "=" + value);
            }
            return TextUtils.join("&", l);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String set_settings_after_call(Context context) {
        String ret = "";
        String[] game_names = context.getResources().getStringArray(R.array.nav_item_activity_titles);// get the string array from string.xml
        for (int i = 0; i < game_names.length; i++) {
            game_names[i] = game_names[i].toLowerCase();
            game_names[i] = game_names[i].trim();
            game_names[i] = game_names[i].replace(" ", "_");
        }

        JSONObject reply = null;
        try {
            reply = new JSONObject(fetchData.get_JSON());
            if (reply.get("status").equals("success")) {
                // reply = {name, ip, user_id, token, datetime}
                reply = new JSONObject(reply.getJSONArray("user").get(0).toString());
                reply.remove("datetime");
                reply.put("ip", IP);
                putString(context, reply.toString());
                // This is to get the token or userid if stored
                String ss = getString(context, "{\"list\" : [\"user_id\" , \"token\" , \"ip\"]}");
                JSONObject j_stored = new JSONObject(ss);

                for (int i = 0; i < game_names.length; i++) {
                    GAME_NAME = game_names[i];

                    // Checking if the token in the shared Prefernece is not equal to the value of the token, then clear all the Shared Preference
                    if (!j_stored.getString("token").equals(reply.getString("token")))
                        clear_shared_preferences(context);

                    putString(context, ss);
                }

                ret = "updated";

            } else {
                ret = reply.getString("remark");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void set_settings(String s) {
        try {

            JSONObject j = new JSONObject(s);
            if (j.getString("token").equals(""))
                j.put("token", generate_token());
            //String query = "json=" + URLEncoder.encode(j.toString(), "utf-8");
            DATA = "json=" + j.toString();

            // get the ip the user typed
            IP = j.getString("ip");

            API = HOSTNAME + IP + "/" + DIR + "user/update_details.php";

        } catch (JSONException e) {
            e.printStackTrace();
        } //catch (UnsupportedEncodingException e) {
        // e.printStackTrace();
        //}
    }

    public String get_game_score_after_call() {
        String ret = "";
        try {
            JSONObject j = new JSONObject(fetchData.get_JSON());
            ret = j.getString("remark");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (fetchData.flag == 0)
            ret = "data didn't get send";
        return ret;
    }

    public String set_game_score(Context context, JSONArray json_score) {

        String ret = "";
        try {
            String s = getString(context, "{\"list\" : [\"user_id\" , \"token\", \"ip\"]}");
            JSONObject j = new JSONObject(s);
            j.put("score", json_score);

            IP = j.getString("ip") + "/";
            String game_name_in_server = (GAME_NAME.indexOf('_') != -1) ? GAME_NAME.substring(0, GAME_NAME.indexOf('_')) : GAME_NAME;
            API = HOSTNAME + IP + DIR + game_name_in_server + "/score_add.php";// + "json=" + URLEncoder.encode(j.toString(), "utf-8");
            DATA = "json=" + j.toString();

            ret = API;
        } catch (JSONException e) {
            e.printStackTrace();
        } //catch (UnsupportedEncodingException e) {
        // e.printStackTrace();
        //}
        return ret;
    }


    public String get_one_String(Context context, String str) {
        String s = getString(context, "{\"list\" : [\"" + str + "\"]}");
        String ret = "";
        try {
            JSONObject j = new JSONObject(s);
            ret = j.getString(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String get_API() {
        return API;
    }

    public String get_DATA() {
        return DATA;
    }
}
