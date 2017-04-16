package com.example.chinmay.project;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chinmay on 14-Apr-17.
 */

public class FetchData extends AsyncTask<Void, Void, String> {

    private String API, JSON;
    public int flag;
    public FetchData(String API)
    {
        this.API = API;
        JSON = "{}";
        flag = 0;
    }
    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        Log.d(":", API);
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            URL url = new URL(API);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            forecastJsonStr = buffer.toString();
            this.JSON = forecastJsonStr;
            this.flag = 1;
            return forecastJsonStr;
        } catch (Exception e) {
            Log.e("PlaceholderFragment", "Error ", e);
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }

    public String get_JSON()
    {
        return JSON;
    }
}
