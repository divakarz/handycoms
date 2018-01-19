package com.handycoms;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Command extends AppCompatActivity {

    SQLiteDatabase mDatabase;
    EditText txtCommandName ;
    EditText txtCommandDescription ;
    Button btnCommand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_add__command);


        Intent intent = getIntent();
        String LANGUAGENAME = intent.getStringExtra("name").toString();
        String LANGUAGEID = intent.getStringExtra("id").toString();
        this.setTitle("ADD COMMAND IN " + LANGUAGENAME.toUpperCase());

        HandyDBHelper dbHelper = new HandyDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        txtCommandName = findViewById(R.id.txtCommandName);
        txtCommandDescription = findViewById(R.id.txtCommandDescription);
        btnCommand = findViewById(R.id.btnCommand);
        btnCommand.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String COMMANDNAME = txtCommandName.getText().toString().trim();
                String COMMANDDESCRIPTION = txtCommandDescription.getText().toString().trim();

                if(COMMANDNAME.trim().length()<1 ||  COMMANDDESCRIPTION.trim().length() < 1){
                    if(COMMANDNAME.trim().length()<1){
                    Toast.makeText(getApplicationContext(), "Please enter command !",Toast.LENGTH_LONG).show();
                    }

                    if(COMMANDDESCRIPTION.trim().length()<1){
                        Toast.makeText(getApplicationContext(), "Please enter description !",Toast.LENGTH_LONG).show();
                    }

                }
                else {


                    Intent intent = getIntent();
                    String LANGUAGENAME = intent.getStringExtra("name").toString();
                    String LANGUAGEID = intent.getStringExtra("id").toString();

                    ContentValues cv = new ContentValues();
                    cv.put(HandyContracts.Commands.COLUMN_NAME, COMMANDNAME);
                    cv.put(HandyContracts.Commands.COLUMN_DESCRIPTION, COMMANDDESCRIPTION);
                    cv.put(HandyContracts.Commands.COLUMN_LANGUAGEID, LANGUAGEID);



                    mDatabase.insert(HandyContracts.Commands.TABLE_NAME, null, cv);
                    Toast.makeText(Add_Command.this, "Command Added", Toast.LENGTH_SHORT).show();


                    Intent redirectIntent = new Intent(Add_Command.this, LanguageCommands.class);
                    redirectIntent.putExtra("name" , LANGUAGENAME);
                    redirectIntent.putExtra("id",LANGUAGEID);
                    startActivity(redirectIntent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent intent = getIntent();
        String LANGUAGENAME = intent.getStringExtra("name").toString();
        String LANGUAGEID = intent.getStringExtra("id").toString();


        // TODO Auto-generated method stub
        Intent redirectIntent = new Intent(Add_Command.this, LanguageCommands.class);
        redirectIntent.putExtra("name" , LANGUAGENAME);
        redirectIntent.putExtra("id",LANGUAGEID);
        setResult(2, redirectIntent);


        super.onBackPressed();
    }
}
