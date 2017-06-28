package com.example.kandarp.mygurukul;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Login extends AppCompatActivity implements Response {
    Button b1;
    TextInputLayout user_id, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1 = (Button) findViewById(R.id.sign_in_button);
        user_id = (TextInputLayout) findViewById(R.id.user_id);
        pwd = (TextInputLayout) findViewById(R.id.password);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    void attemptLogin() {
        String user_id = Login.this.user_id.getEditText().getText().toString();
        String pwd = Login.this.pwd.getEditText().getText().toString();
        DBConnect dbConnect = new DBConnect(this);
        dbConnect.delegate = Login.this;
        dbConnect.execute(user_id, pwd);
    }

    @Override
    public void response(boolean userExists) {
        if(userExists) {
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
