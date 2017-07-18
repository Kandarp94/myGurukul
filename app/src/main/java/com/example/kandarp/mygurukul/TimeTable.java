package com.example.kandarp.mygurukul;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Iterator;

public class TimeTable extends AppCompatActivity implements Response {

    private SessionManager session;
    private TableLayout table;
    private TableRow tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        session = new SessionManager(this);
        table = (TableLayout) findViewById(R.id.table);

        DBConnect dbConnect = new DBConnect(getApplicationContext());
        dbConnect.delegate = TimeTable.this;
        dbConnect.execute(URLs.URL_TIME_TABLE, session.getUserDetails().get(session.KEY_USN));


    }

    @Override
    public void response(boolean reply, String response) {
        if (reply) {
            JSONObject jsonObject = null;
            String key = null;
            JSONArray subjects = null;
            int i;
            try {
                jsonObject = new JSONObject(response);
                Iterator<?> keys = jsonObject.keys();

                i = 1;
                while (keys.hasNext()) {
                    tr = (TableRow) table.getChildAt(i);
                    key = (String) keys.next();
                    subjects = jsonObject.getJSONArray(key);

                    for (int j = 0, k = 0; j < subjects.length() + 1; j++) {
                        if (!key.equals("Saturday")) {
                            if (j == 3) {
                                if (key.equals("Friday"))
                                    tr.addView(addTextView(subjects.getString(k++)));
                                else
                                    tr.addView(addTextView(""));

                            } else {
                                if (j == 4)
                                    if (key.equals("Friday"))
                                        tr.addView(addTextView(""));
                                    else
                                        tr.addView(addTextView(subjects.getString(k++)));
                                else
                                    tr.addView(addTextView(subjects.getString(k++)));
                            }
                        } else {
                            if (j < 4)
                                tr.addView(addTextView(subjects.getString(k++)));
                            else
                                tr.addView(addTextView("-"));
                        }


                    }

                    /*table.addView(tr, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));*/
                    i++;
                    Log.i("i", i + "");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    LinearLayout addTextView(String text) {
        TextView label = new TextView(TimeTable.this);
        label.setText(text);
        label.setId(View.generateViewId());
        label.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        label.setPadding(pixelAsDp(10), pixelAsDp(10), pixelAsDp(10), pixelAsDp(10));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        label.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        label.setTextColor(Color.BLACK);
        label.setBackgroundResource(R.drawable.border);
        LinearLayout Ll = new LinearLayout(TimeTable.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 1, 0);
        Ll.addView(label, params);
        return Ll;
    }

    int pixelAsDp(int value) {
        float scale = getResources().getDisplayMetrics().density;
        int dp = (int) (value * scale + 0.5f);
        return dp;
    }
}
