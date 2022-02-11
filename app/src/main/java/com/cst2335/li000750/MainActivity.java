package com.cst2335.li000750;

import static com.google.android.material.snackbar.Snackbar.LENGTH_LONG;
import static com.google.android.material.snackbar.Snackbar.make;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private TextView emailAddress,password;
    private EditText typeEmail,typePWD;
    private Button login;

    public static final String storeEmail="EMAIL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //load email address
        EditText typeEmail = findViewById(R.id.typeEmail);
        SharedPreferences sharedPreferences = getSharedPreferences(storeEmail, Context.MODE_PRIVATE);
        String preferencesString = sharedPreferences.getString(storeEmail,"");

        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("EMAIL",emailTyped);
        //editor.apply();
        typeEmail.setText(preferencesString);

        //login button
        Button login = findViewById(R.id.login);
        login.setOnClickListener(click ->{
            String emailTyped = typeEmail.getText().toString();
            Intent gotoProfile = new Intent(MainActivity.this,ProfileActivity.class);
            gotoProfile.putExtra(storeEmail,emailTyped);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(storeEmail,emailTyped);
            editor.apply();
            startActivity(gotoProfile);
        });
    }

/*
    @Override
    protected void onPause() {
        super.onPause();
        EditText typeEmail = findViewById(R.id.typeEmail);
        Button login = findViewById(R.id.login);
        String emailTyped = typeEmail.getText().toString();
        login.setOnClickListener(click ->{
            SharedPreferences sharedPreferences = getSharedPreferences("storeEmail", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("storeEmail",emailTyped);
            editor.apply();
        });

    }

 */
}