package com.example.kandarp.mygurukul;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Kandarp on 6/26/2017.
 */

public class DBConnect extends AsyncTask<String, Void, String> {

    Context ctx;
    Response delegate;

    DBConnect(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String resp = "";
        try {
            URL url = new URL(params[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);

            if(params.length > 1) {
                urlConnection.setDoOutput(true);
                String data = URLEncoder.encode("usn", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                OutputStream OS = urlConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
            }

            InputStream IS = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
                resp = resp + line;
            bufferedReader.close();
            IS.close();
            urlConnection.disconnect();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            resp = e.getMessage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            resp = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            //if (e.getMessage().contains("failed to connect"))
            //  resp = "Server not reachable !!";
            resp = e.getMessage();
        }
        //Log.i("user", resp);
        return resp;
    }

    @Override
    protected void onPostExecute(String response) {
        Log.e("user", response);
        if (!isJSONValid(response))
            delegate.response(false, response);
        else
            delegate.response(true, response);

    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException e) {
                return false;
            }
        }
        return true;
    }
}
