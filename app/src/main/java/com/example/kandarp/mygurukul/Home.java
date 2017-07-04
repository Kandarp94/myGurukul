package com.example.kandarp.mygurukul;

/**
 * Created by Kandarp on 6/28/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ViewFlipper;

public class Home extends AppCompatActivity {

    GridView gridView;
    private SessionManager session;
    static final String[] TITLE = new String[]{"My Profile", "Performance Details", "Institutional Holidays", "Time Table"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        session = new SessionManager(Home.this);

        final ViewFlipper myView = (ViewFlipper) findViewById(R.id.viewF);
        myView.setFlipInterval(2000);
        myView.startFlipping();

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(Home.this, TITLE));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(Home.this, MyProfile.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:{
                        Intent intent = new Intent(Home.this, PerformanceDetails.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(Home.this, Holidays.class);
                        startActivity(intent);
                        break;
                    }
                    case 3:
                        break;
                }
            }
        });
    }
}
