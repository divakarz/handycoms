package com.handycoms;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Language extends AppCompatActivity {

    SQLiteDatabase mDatabase;
    EditText txtLanguageName ;
    Button btnLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_add__language);
        HandyDBHelper dbHelper = new HandyDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        btnLanguage = findViewById(R.id.btnLanguage);
        txtLanguageName = findViewById(R.id.txtLanguageName);
        txtLanguageName.setTextIsSelectable(true);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);




        btnLanguage.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String NAME = txtLanguageName.getText().toString().trim();

                if(NAME .length()<1){
                    Toast.makeText(getApplicationContext(), "Please enter name !",Toast.LENGTH_LONG).show();

                }
                else {
                    ContentValues cv = new ContentValues();
                    cv.put(HandyContracts.Language.COLUMN_NAME, NAME);
                    mDatabase.insert(HandyContracts.Language.TABLE_NAME, null, cv);
                    Toast.makeText(Add_Language.this, "Language Added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Add_Language.this, Languages.class));
                }
            }
        });

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//              NavUtils.navigateUpFromSameTask(this);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//
//    }
}
