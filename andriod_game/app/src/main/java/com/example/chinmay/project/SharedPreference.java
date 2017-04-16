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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by chinmay on 14-Apr-17.
 */

public class SharedPreference extends Activity{
    private String IP;
    private String HOSTNAME = "http://";
    private String DIR = "project/userapi/";
    private String USERID;
    private String TOKEN;
    private String API;
    final private String MY_PREF_NAME = "MyPrefFile";

    private boolean wait_for_async_response(FetchData x)
    {
        int count = 0;
        while(x.flag!=1)
        {
            SystemClock.sleep(1000);
            Log.d(":",x.get_JSON());
            count +=1;
            if(count == 10)break;
        }
        return (count == 10 && x.flag==0);
    }

    private void putString(Context context, String s)
    {
        try{
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE).edit();
        JSONObject j = new JSONObject(s);
        Iterator<String> iter = j.keys();
        while (iter.hasNext())
        {
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
            SharedPreferences editor = context.getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE);

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
        String ret = null;
        try{
            JSONObject j = new JSONObject(s);
            String query = query_string(s);
            String ip = j.getString("ip");
            API = HOSTNAME + ip +"/" + DIR + "user/update_details.php?" + query;
            FetchData x = new FetchData(API);
            x.execute();

            if(wait_for_async_response(x))
                return "network error";

            JSONObject reply = new JSONObject(x.get_JSON());
            if (reply.get("status").equals("success"))
            {
                putString(context, reply.getJSONArray("user").get(0).toString());
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
        String ret = "{}";
        try {
            String s = getString(context,"{\"list\" : [\"user_id\" , \"token\", \"ip\"]}");
            JSONObject j = new JSONObject(s);

            if(j.getString("ip").equals(""))
                return "IP not set";

            j.put("score", score);
            j.put("time", time);

            IP = j.getString("ip") + "/";
            API = HOSTNAME + IP + DIR + "stroop/score_add.php?" + query_string(j.toString());

            FetchData x = new FetchData(API);
            x.execute();

            if(wait_for_async_response(x))
                return "network error";

            JSONObject reply = new JSONObject(x.get_JSON());
            ret = reply.getString("remark");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
