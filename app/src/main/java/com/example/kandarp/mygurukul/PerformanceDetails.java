package com.example.kandarp.mygurukul;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PerformanceDetails extends AppCompatActivity implements Response{

    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_details);

        DBConnect dbConnect = new DBConnect(getApplicationContext());
        dbConnect.delegate = PerformanceDetails.this;
        dbConnect.execute(URLs.URL_FETCH_PERFORMANCE_DETAILS,session.getUserDetails().get(session.KEY_USN));
    }

    @Override
    public void response(boolean reply, String response) {

    }
}
