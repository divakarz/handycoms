package com.handycoms;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import static com.handycoms.HandyContracts.LANGUAGE_NAME;

public class LanguageCommands extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private HandyLanguageCommandAdapter mAdapter;
    SearchView searchView;


    FloatingActionButton btnOpenAddCommand;


    public String GetSelectedLanguageName()
    {
        SharedPreferences sp = getSharedPreferences(HandyContracts.LANGUAGE_PREFERENCES , Context.MODE_PRIVATE);
        String name  = sp.getString(LANGUAGE_NAME,"");
        return name;
    }

    public String GetSelectedLanguageId()
    {
        SharedPreferences sp = getSharedPreferences(HandyContracts.LANGUAGE_PREFERENCES , Context.MODE_PRIVATE);
        String id  = sp.getString(HandyContracts.LANGUAGE_ID,"");
        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_language_commands);



        this.setTitle(GetSelectedLanguageName());


        btnOpenAddCommand = findViewById(R.id.btnOpenAddCommand);
        btnOpenAddCommand.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent redirectIntent = new Intent(LanguageCommands.this, Add_Command.class);
                redirectIntent.putExtra("name" , GetSelectedLanguageName());
                redirectIntent.putExtra("id",GetSelectedLanguageId());
                startActivity(redirectIntent);





            }
        });


        HandyDBHelper dbHelper = new HandyDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recyclerLanguageCommandList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        mAdapter = new HandyLanguageCommandAdapter(this, getAllItems(""));
        recyclerView.setAdapter(mAdapter);



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                long id = (long) viewHolder.itemView.getTag();
                mDatabase.delete(
                        HandyContracts.Commands.TABLE_NAME,
                        HandyContracts.Commands._ID + "=" + id,
                        null
                );
                mAdapter.swapCursor(getAllItems(""));
            }
        }).attachToRecyclerView(recyclerView);
    }


    private Cursor getAllItems(String searchtext) {





        return mDatabase.query(
                HandyContracts.Commands.TABLE_NAME,
                new String[] { HandyContracts.Commands.COLUMN_NAME ,
                        HandyContracts.Commands.COLUMN_DESCRIPTION ,
                        HandyContracts.Commands.COLUMN_LANGUAGEID ,
                        HandyContracts.Commands._ID,
                        HandyContracts.Commands.COLUMN_TIMESTAMP } ,
                HandyContracts.Commands.COLUMN_LANGUAGEID + " = "+ GetSelectedLanguageId() + " AND " +
                "(" + HandyContracts.Commands.COLUMN_NAME + " LIKE '%"+ searchtext +"%'"+ " OR " +
                         HandyContracts.Commands.COLUMN_DESCRIPTION + " LIKE '%"+ searchtext +"%'"+  ")",
                null,
                null,
                null,
                HandyContracts.Commands.COLUMN_TIMESTAMP + " DESC"
        );



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.colorButtonFont));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.swapCursor(getAllItems(newText));
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
