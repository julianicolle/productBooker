package com.example.admin.barecodescanningapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by admin on 14/01/16.
 */
public class PostOnServerTask extends AsyncTask<String, Integer, Boolean> {
    private PostOnServerTaskListener postOnServerTaskListener;
    private  String resultString = null;


    public interface PostOnServerTaskListener {
        void onPostExecute(Boolean success, String result);
    }

    public PostOnServerTask(PostOnServerTaskListener listener){
        postOnServerTaskListener = listener;
    }


    @Override
    protected Boolean doInBackground(String... params) {
        try {
            postData(params[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (resultString != null) {
            postOnServerTaskListener.onPostExecute(success, resultString);
        }
    }


    public Boolean postData(String param) throws JSONException {

        HttpURLConnection urlConn = null;
        StringBuilder result = null;
        Integer responseCode = 0;

        JSONObject json = new JSONObject();
        json.put("url info", param);
        Log.d("postTask: JO", json.toString());
        try {
            URL url;
            String address = "http://172.20.10.3:8888/product/getId";
            Log.d("sendPost", address);
            url = new URL(address);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.connect();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConn.getOutputStream());
            outputStreamWriter.write(param);
            outputStreamWriter.flush();
            outputStreamWriter.close();
            responseCode = urlConn.getResponseCode();

            InputStream inputStream = new BufferedInputStream(urlConn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            resultString = result != null ? result.toString() : null;
            if (resultString != null) {
                Log.i("responseWeb",resultString);
            }
            urlConn.disconnect();
        }
        return responseCode == HttpURLConnection.HTTP_OK;
    }
}
