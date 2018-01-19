package com.handycoms;

import android.content.ClipData;
import android.content.Intent;
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

public class Languages extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private HandyLanguageAdapter mAdapter;
    SearchView searchView;

    FloatingActionButton btnOpenAddLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_languages);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        btnOpenAddLanguage = findViewById(R.id.btnOpenAddLanguage);
        btnOpenAddLanguage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Languages.this, Add_Language.class));
            }
        });

        HandyDBHelper dbHelper = new HandyDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recyclerLanguageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        mAdapter = new HandyLanguageAdapter(this, getAllItems(""));
        recyclerView.setAdapter(mAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                if (getCommandsCount(id) > 0) {
                    Toast.makeText(Languages.this, "Delete associated commands first to delete the language", Toast.LENGTH_SHORT).show();
                } else {

                    mDatabase.delete(
                            HandyContracts.Language.TABLE_NAME,
                            HandyContracts.Language._ID + "=" + id,
                            null
                    );
                 }
                mAdapter.swapCursor(getAllItems(""));

            }
        }).attachToRecyclerView(recyclerView);
    }


    private Integer getCommandsCount(long id) {
        Integer count = 0;

Cursor cursor=

        mDatabase.query(
                HandyContracts.Commands.TABLE_NAME,
                new String[] { HandyContracts.Commands.COLUMN_NAME ,
                        HandyContracts.Commands.COLUMN_DESCRIPTION ,
                        HandyContracts.Commands.COLUMN_LANGUAGEID ,
                        HandyContracts.Commands._ID,
                        HandyContracts.Commands.COLUMN_TIMESTAMP } ,
                HandyContracts.Commands.COLUMN_LANGUAGEID + " = "+ id,
                null,
                null,
                null,
                HandyContracts.Commands.COLUMN_TIMESTAMP + " DESC"
        );

        //Toast.makeText(this, cursor.getCount(), Toast.LENGTH_SHORT).show();
        return cursor.getCount();

    }




    private Cursor getAllItems(String searchtext) {
        return mDatabase.query(
                HandyContracts.Language.TABLE_NAME,
                new String[]{HandyContracts.Language.COLUMN_NAME,
                        HandyContracts.Language._ID,
                        HandyContracts.Language.COLUMN_TIMESTAMP},
                HandyContracts.Language.COLUMN_NAME + " LIKE '%" + searchtext + "%'",
                null,
                null,
                null,
                HandyContracts.Language.COLUMN_TIMESTAMP + " DESC"
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
