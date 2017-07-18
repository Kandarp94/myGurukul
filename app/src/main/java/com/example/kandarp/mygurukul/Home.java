package com.example.kandarp.mygurukul;

/**
 * Created by Kandarp on 6/28/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Home extends AppCompatActivity {

    GridView gridView;
    private SessionManager session;
    static final String[] TITLE = new String[]{"My Profile", "Performance Details", "Institutional Holidays", "Time Table"};

    private ActionBarDrawerToggle mtoggle;
    private Toolbar mtoolbar;
    private TextView usnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usnText = (TextView) findViewById(R.id.usnText);

        session = new SessionManager(Home.this);

        mtoolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mtoolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ViewFlipper myView = (ViewFlipper) findViewById(R.id.viewF);
        myView.setFlipInterval(2000);
        myView.startFlipping();

        String name = session.getUserDetails().get(session.KEY_FIRST_NAME);
        String usn = session.getUserDetails().get(session.KEY_USN);
        usnText.setText(name + "(" + usn + ")");

        gridView = (GridView) findViewById(R.id.gridview);

        gridView.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });


        gridView.setAdapter(new ImageAdapter(Home.this, TITLE));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(Home.this, MyProfile.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
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
                        Intent intent = new Intent(Home.this, TimeTable.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                session.logoutUser();
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
