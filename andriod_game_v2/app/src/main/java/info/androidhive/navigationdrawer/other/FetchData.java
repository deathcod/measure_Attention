package info.androidhive.navigationdrawer.other;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by chinmay on 14-Apr-17.
 */

public class FetchData extends AsyncTask<Void, Void, String> {

    public int flag;
    private String API, JSON, DATA;
    private Context context;

    public FetchData(Context context, String API, String DATA) {
        this.API = API;
        this.DATA = DATA;
        this.context = context;
        JSON = "{}";
        flag = 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        Log.d(":", API);
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            byte[] postData = DATA.getBytes(StandardCharsets.UTF_8);
            int postLength = postData.length;

            URL url = new URL(API);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(postLength));
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);

            try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream())) {
                wr.write(postData);
            }


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
                //if failure show timeout
                CreateToken(context, "TimeOut");
                return null;
            }
            forecastJsonStr = buffer.toString();
            this.JSON = forecastJsonStr;
            this.flag = 1;

            //if successful show the remark
            JSONObject j = new JSONObject(this.JSON);
            CreateToken(context, j.getString("remark"));

            return forecastJsonStr;
        } catch (java.net.SocketTimeoutException e) {
            return null;
        } catch (Exception e) {
            Log.e("PlaceholderFragment", "Error ", e);
            return null;
        } finally {
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

    public String get_JSON() {
        return JSON;
    }

    //Show the token of response
    private void CreateToken(Context context, String remark) {
        Toast.makeText(context, remark, Toast.LENGTH_SHORT).show();
    }
}
