package com.example.chinmay.project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
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

/**
 * Created by chinmay on 14-Apr-17.
 */

public class SharedPreference extends Activity{
    private String IP;
    private String HOSTNAME;
    private String DIR;
    private String API;
    private String GAME_NAME;

    public SharedPreference(String game_name) {
        GAME_NAME = game_name;
        DIR = "project/userapi/";
        HOSTNAME = "http://";
    }

    private String async_response()
    {
        FetchData x = new FetchData(API);
        x.execute();

        int count = 0;
        while(x.flag!=1)
        {
            SystemClock.sleep(1000);
            Log.d(":",x.get_JSON());
            count +=1;
            if(count == 10)break;
        }
        if (count == 10 && x.flag == 0)
            return null;
        else
            return x.get_JSON();
    }

    private void putString(Context context, String s)
    {
        try{
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

    public String getString(Context context, String s)
    {
        String ret = "{}";
        try
        {
            SharedPreferences editor = context.getSharedPreferences(GAME_NAME, MODE_PRIVATE);

            JSONArray j = new JSONObject(s).getJSONArray("list");// get the json under the list {"list" : [, , ,]}
            JSONObject ret_json = new JSONObject();
            for (int i=0; i<j.length(); i++) {
                String val = j.getString(i);
                ret_json.put(val, editor.getString(val, ""));
            }
            ret = ret_json.toString();
        } catch (JSONException e) {
                e.printStackTrace();
            }
        return ret;
    }

    private String query_string(String s)
    {
        try {
        JSONObject j = new JSONObject(s);
        List<String> l = new ArrayList<String>();
        Iterator<String> iter = j.keys();
        while (iter.hasNext())
        {
            String key = iter.next();
            String value = j.getString(key);
            try {
                key = URLEncoder.encode(key,  "utf-8");
                value = URLEncoder.encode(value,  "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            l.add(key +"="+ value);
        }
        return TextUtils.join("&",l);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String set_settings(Context context, String s)
    {
        String game_names[] = {"stroop", "chess", "settings"};
        String ret = null;
        try{
            JSONObject j = new JSONObject(s);
            String query = query_string(s);
            String ip = j.getString("ip");
            API = HOSTNAME + ip +"/" + DIR + "user/update_details.php?" + query;

            String response = async_response();
            if (response == null)
                return "network_error";

            JSONObject reply = new JSONObject(response);
            if (reply.get("status").equals("success"))
            {
                for (int i = 0; i < game_names.length; i++) {
                    GAME_NAME = game_names[i];
                    putString(context, reply.getJSONArray("user").get(0).toString());
                }

                ret = "updated";
            }
            else
            {
                ret = reply.getString("remark");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  ret;
    }
    public String set_Stroop_score(Context context, String score,String time)
    {
        String ret = "error";
        try {
            String s = getString(context,"{\"list\" : [\"user_id\" , \"token\", \"ip\"]}");
            JSONObject j = new JSONObject(s);

            if(j.getString("ip").equals(""))
                return "IP not set";

            j.put("score", score);
            j.put("time", time);

            IP = j.getString("ip") + "/";
            API = HOSTNAME + IP + DIR + GAME_NAME + "/score_add.php?" + query_string(j.toString());

            String response = async_response();
            if (response == null)
                return "network_error";

            JSONObject reply = new JSONObject(response);
            ret = reply.getString("remark");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String set_chess_score(Context context, String score, String time, String level) {
        String s = "{" + "\"score_" + level + "\":\"" + score + "\", \"time_" + level + "\":\"" + time + "\"}";

        putString(context, s);

        String ret = "error";
        try {
            s = getString(context, "{\"list\" : [\"user_id\" , \"token\", \"ip\", \"score_1\" , \"score_2\", \"score_3\", \"score_4\", \"time_1\" , \"time_2\", \"time_3\", \"time_4\"]}");
            JSONObject j = new JSONObject(s);
            if (j.getString("score_1").equals("") ||
                    j.getString("score_2").equals("") ||
                    j.getString("score_3").equals("") ||
                    j.getString("score_4").equals(""))
                return null;

            if (j.getString("ip").equals(""))
                return "IP not set";

            IP = j.getString("ip") + "/";
            API = HOSTNAME + IP + DIR + GAME_NAME + "/score_add.php?" + query_string(j.toString());
            String response = async_response();
            if (response == null)
                return "network_error";

            JSONObject reply = new JSONObject(response);
            ret = reply.getString("remark");

            if (reply.getString("status").equals("success")) {
                s = "{ \"score_1\" : \"\", \"score_2\" : \"\", \"score_3\" : \"\", \"score_4\" : \"\"}";
                putString(context, s);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;

    }
}
