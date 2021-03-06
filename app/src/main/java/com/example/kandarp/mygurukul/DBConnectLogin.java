package com.example.kandarp.mygurukul;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

public class DBConnectLogin extends AsyncTask<String, Void, String> {

    Context ctx;
    Response delegate;

    DBConnectLogin(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String resp = "";
        try {
            URL url = new URL("http://kandarps.heliohost.org/check_user.php");
            //URL url = new URL("http://kandarp.890m.com/check_user.php");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);

            String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                    URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
            OutputStream OS = urlConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            if (e.getMessage().contains("failed to connect"))
                resp = "Server not reachable !!";
        }

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
