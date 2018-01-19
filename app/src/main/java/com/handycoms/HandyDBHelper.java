package com.handycoms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.handycoms.HandyContracts.*;
/**
 * DB Helper class
 */

public class HandyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "handycommands.db";
    public static final int DATABASE_VERSION = 1;



    public HandyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_LANGUAGE_TABLE = "CREATE TABLE "+
                Language.TABLE_NAME + " (" +
                Language._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Language.COLUMN_NAME + " TEXT NOT NULL, " +
                Language.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "+
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_LANGUAGE_TABLE);


        final String SQL_CREATE_COMMANDS_TABLE = "CREATE TABLE "+
                Commands.TABLE_NAME + " (" +
                Commands._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Commands.COLUMN_LANGUAGEID + " INTEGER NOT NULL, " +
                Commands.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                Commands.COLUMN_NAME + " TEXT NOT NULL, " +
                Commands.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "+
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_COMMANDS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Language.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Commands.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
