package info.androidhive.navigationdrawer.other;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chinmay on 18-May-17.
 */

public class JSONData {
    private JSONArray JA;
    private JSONObject JO;
    private String select;


    //Created a new private json object
    //select here defines the purpose, is JSONArray to be used or JSONOBject.
    public JSONData(String select) {
        this.JA = new JSONArray();
        this.JO = new JSONObject();
        this.select = select;
    }

    public JSONData(String select, String s) {
        try {
            if (select.equals("JA")) {
                this.JA = new JSONArray(s);
                this.JO = new JSONObject();
            } else {
                this.JO = new JSONObject(s);
                this.JA = new JSONArray();
            }
            this.select = select;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //store JSONArray
    public void set_level_data(String score, String wrong_answer, String correct_answer, String time, JSONArray click_details) {
        JSONObject temp_level = new JSONObject();
        try {
            temp_level.put("score", score);
            temp_level.put("wrong", wrong_answer);
            temp_level.put("correct", correct_answer);
            temp_level.put("time", time);
            temp_level.put("click", click_details);

            //storing the level data in the array
            this.JA.put(temp_level);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //store JSONObject Overloaded function
    public void set_extra_data(String s, JSONArray a) {
        try {
            this.JO.put(s, a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void set_extra_data(String s, String a) {
        try {
            this.JO.put(s, a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void set_extra_data(String s, int a) {
        try {
            this.JO.put(s, a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Store JSONArray
    public void set_click_details(String time_of_click, String status_of_click) {
        JSONObject temp_click_details = new JSONObject();
        try {
            temp_click_details.put("datetime", time_of_click);
            temp_click_details.put("status", status_of_click);

            //storing the click_details
            this.JA.put(temp_click_details);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray get_data_JA() {
        return this.JA;
    }

    public JSONObject get_data_JO() {
        return this.JO;
    }

    public String get_data_string() {
        String s = "[]";
        try {
            if (select.equals("JA"))
                s = this.JA.toString(4);
            else
                s = this.JO.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    public String get_data_string_without_intend() {
        String s = "[]";
        if (select.equals("JA"))
            s = this.JA.toString();
        else
            s = this.JO.toString();
        return s;
    }
}
