package com.example.kandarp.mygurukul;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

public class DBConnect extends AsyncTask<String, Void, Boolean> {

    Context ctx;
    Response delegate;

    DBConnect(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String resp = "";
        try {
            URL url = new URL("http://kandarp.890m.com/check_user.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);

            String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                    URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
            OutputStream OS = urlConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();

            Log.e("user",params[0]+params[1]);

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
        }

        if (resp.equals("true"))
            return true;
        else
            return false;
    }

    @Override
    protected void onPostExecute(Boolean userExists) {
        delegate.response(userExists);
        Log.e("user", userExists.toString());
    }
}
