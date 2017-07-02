package com.example.kandarp.mygurukul;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MyProfile extends AppCompatActivity implements Response{

    TableRow tr;
    TableLayout tl;
    String tags[] = {"USN","First Name", "Last Name", "DOB", "Email Id", "Mob no."};
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        session = new SessionManager(getApplicationContext());

        tl = (TableLayout) findViewById(R.id.table);
        DBConnect dbConnect = new DBConnect(MyProfile.this);
        dbConnect.delegate = MyProfile.this;
        Log.i("usn",session.getUserDetails().get(session.KEY_USN));
        dbConnect.execute(URLs.URL_FETCH_DETAILS,session.getUserDetails().get(session.KEY_USN));
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
                    int j=0;
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
}
