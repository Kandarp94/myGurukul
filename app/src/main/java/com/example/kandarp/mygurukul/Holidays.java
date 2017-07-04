package com.example.kandarp.mygurukul;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Holidays extends AppCompatActivity implements Response{

    TableLayout tableHoliday;
    TableRow tr;
    int textBackground1, textBackground2;
    int colors[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);

        textBackground1 = ContextCompat.getColor(Holidays.this, R.color.textBackground1);
        textBackground2 = ContextCompat.getColor(Holidays.this, R.color.textBackground2);
        colors = new int[]{textBackground1, textBackground2};

        tableHoliday = (TableLayout) findViewById(R.id.table_holiday);
        DBConnect dbConnect = new DBConnect(Holidays.this);
        dbConnect.delegate = Holidays.this;
        dbConnect.execute(URLs.URL_FETCH_HOLIDAYS);
    }

    @Override
    public void response(boolean reply, String response) {
        if (reply) {
            try {
                JSONArray jsonarray = new JSONArray(response);
                Log.e("user", jsonarray.length() + "");

                for (int i = 0; i < jsonarray.length(); i++) {
                    tr = new TableRow(Holidays.this);
                    JSONObject jsonobject = jsonarray.getJSONObject(i);

                    LinearLayout date = createTextView(jsonobject, "Date", colors[i % 2]);
                    tr.addView((View) date);
                    LinearLayout desc = createTextView(jsonobject, "Desc", colors[i % 2]);
                    tr.addView((View) desc);

                    tableHoliday.addView(tr, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(Holidays.this, response, Toast.LENGTH_SHORT).show();

    }

    int pixelAsDp(int value) {
        float scale = getResources().getDisplayMetrics().density;
        int dp = (int) (value * scale + 0.5f);
        return dp;
    }

    LinearLayout createTextView(JSONObject jsonobject, String key, int backgroundColor) {
        String name = null;
        try {
            name = jsonobject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView label = new TextView(Holidays.this);
        if (key.equals("Date")) {
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-M-d").parse(name);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(date);
            String newDate = new SimpleDateFormat("dd-MMM-yy").format(date);

            Log.i("date", dayOfWeek);
            name = dayOfWeek + "," + newDate;
        }
        label.setText(name.equals("null") ? "-" : name);
        label.setId(View.generateViewId());
        label.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        label.setPadding(pixelAsDp(10), pixelAsDp(10), pixelAsDp(10), pixelAsDp(10));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        label.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        label.setBackgroundColor(backgroundColor);
        label.setGravity(Gravity.CENTER);
        label.setTextColor(Color.WHITE);
        LinearLayout Ll = new LinearLayout(Holidays.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 1, 0);
        Ll.addView(label, params);
        return Ll;
    }
}
