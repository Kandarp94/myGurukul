package com.example.kandarp.mygurukul;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity implements ResponseLogin {
    Button b1;
    TextInputLayout userIdLayout, pwdLayout;
    String user_id;
    String pwd;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        b1 = (Button) findViewById(R.id.sign_in_button);
        userIdLayout = (TextInputLayout) findViewById(R.id.user_id);
        pwdLayout = (TextInputLayout) findViewById(R.id.password);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    void attemptLogin() {
        user_id = userIdLayout.getEditText().getText().toString();
        pwd = pwdLayout.getEditText().getText().toString();
        DBConnectLogin dbConnectLogin = new DBConnectLogin(this);
        dbConnectLogin.delegate = Login.this;
        dbConnectLogin.execute(user_id, pwd);
    }

    @Override
    public void response(boolean userExists) {
        if(userExists) {
            session.createLoginSession(user_id);
            Intent intent = new Intent(Login.this,Home.class);
            startActivity(intent);
        }
        else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
            alertDialog.setTitle("Login failed...")
                    .setMessage("Username/Password is incorrect")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
    }
}
