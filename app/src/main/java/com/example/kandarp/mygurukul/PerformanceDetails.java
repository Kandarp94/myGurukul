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


public class PerformanceDetails extends AppCompatActivity implements Response {
    TableLayout tl;
    TableRow tr;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_details);

        session = new SessionManager(getApplicationContext());
        tl = (TableLayout) findViewById(R.id.table);
        tr = (TableRow) View.inflate(this, R.layout.performance_details_table_row, null);
        tr.setId(View.generateViewId());
        tl.addView(tr);

        DBConnect dbConnect = new DBConnect(getApplicationContext());
        dbConnect.delegate = PerformanceDetails.this;
        dbConnect.execute(URLs.URL_FETCH_PERFORMANCE_DETAILS, session.getUserDetails().get(session.KEY_USN));
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

                    tr = (TableRow) View.inflate(this, R.layout.performance_details_table_row, null);
                    tr.setId(View.generateViewId());
                    int j=0;

                    while (keys.hasNext()) {
                        key = (String) keys.next();
                        TextView tv1 = (TextView) tr.getChildAt(j++);
                        tv1.setText(jsonobject.getString(key));
                    }
                    tl.addView(tr);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }
}