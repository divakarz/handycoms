package com.handycoms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import static com.handycoms.HandyContracts.LANGUAGE_NAME;

public class View_Command extends AppCompatActivity {

    TextView txtCommandNameView, txtCommandDesciptionView;

    public String GetSelectedLanguageName() {
        SharedPreferences sp = getSharedPreferences(HandyContracts.LANGUAGE_PREFERENCES, Context.MODE_PRIVATE);
        String name = sp.getString(LANGUAGE_NAME, "");
        return name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_view_command);
        this.setTitle(GetSelectedLanguageName());

        Intent intent = getIntent();

        String plid = intent.getStringExtra("plid");
        String name = intent.getStringExtra("commandname");
        String command = intent.getStringExtra("commanddescription");
//        String description_full = intent.getStringExtra("description_full");


        txtCommandNameView = findViewById(R.id.txtCommandNameView);
        txtCommandNameView.setText(name);


        txtCommandDesciptionView = findViewById(R.id.txtCommandDesciptionView);
        txtCommandDesciptionView.setText(command);
    }
}
