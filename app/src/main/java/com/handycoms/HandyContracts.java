package com.handycoms;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.BaseColumns;

/**
 * Can not create objects for this class
 */

public class HandyContracts {

    public static final String LANGUAGE_PREFERENCES ="com.handycoms.LanguageCommands";
    public static final String LANGUAGE_NAME= "Name";
    public static final String LANGUAGE_ID= "Id";




    public static final class Language implements BaseColumns {
        public static final String TABLE_NAME = "language";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }


    public static final class Commands implements BaseColumns {
        public static final String TABLE_NAME = "commands";
        public static final String COLUMN_LANGUAGEID = "lid";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }


}
