package com.example.kandarp.mygurukul;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Kandarp on 6/28/2017.
 */
public class Splash extends Activity {

    private static int SPLASH_DISPLAY_LENGTH = 1500;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        session = new SessionManager(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                boolean login = session.checkLogin();
                Log.e("login",login+"");
                if(login) {
                    Intent mainIntent = new Intent(Splash.this, Home.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

