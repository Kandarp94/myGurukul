package com.example.kandarp.mygurukul;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class MyProfile extends AppCompatActivity implements Response {

    TableRow tr;
    TableLayout tl;
    String tags[] = {"USN", "First Name", "Last Name", "DOB", "Email Id", "Mob no."};
    private SessionManager session;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        session = new SessionManager(getApplicationContext());
        profilePic = (ImageView) findViewById(R.id.proPic);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DownloadImageTask downloadImage = new DownloadImageTask();
                downloadImage.execute(session.getUserDetails().get(session.KEY_USN));
            }
        };
        new Thread(runnable).start();

        tl = (TableLayout) findViewById(R.id.table);
        DBConnect dbConnect = new DBConnect(MyProfile.this);
        dbConnect.delegate = MyProfile.this;
        Log.i("usn", session.getUserDetails().get(session.KEY_USN));
        dbConnect.execute(URLs.URL_FETCH_DETAILS, session.getUserDetails().get(session.KEY_USN));
    }

    @Override
    public void response(boolean reply, String response) {
        if (reply) {
            String key = null;
            try {
                JSONArray jsonarray = new JSONArray(response);
                Log.e("user", jsonarray.length() + "");

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    Iterator<?> keys = jsonobject.keys();
                    int j = 0;
                    while (keys.hasNext()) {
                        key = (String) keys.next();
                        tr = (TableRow) View.inflate(this, R.layout.profile_table_row, null);
                        tr.setId(View.generateViewId());

                        TextView tv1 = (TextView) tr.getChildAt(0);
                        tv1.setText(tags[j++]);

                        tv1 = (TextView) tr.getChildAt(1);
                        tv1.setText(jsonobject.getString(key));

                        tl.addView(tr);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... params) {
            Bitmap myBitmap = null;
            try {
                URL urlImage = new URL("http://kandarps.heliohost.org/get_image.php?usn=" + params[0]);
                HttpURLConnection urlCon = (HttpURLConnection) urlImage.openConnection();
                urlCon.setDoInput(true);
                urlCon.connect();
                InputStream input = urlCon.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
                Log.i("image", myBitmap.toString());

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return myBitmap;
        }

        protected void onPostExecute(Bitmap result) {
            profilePic.setImageBitmap(result);
        }
    }
}
