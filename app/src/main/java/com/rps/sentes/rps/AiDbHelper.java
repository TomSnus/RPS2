//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.rps.sentes.rps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class AiDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "aidb.db";
    private final String TYPE_INTEGER = "INTEGER";
    private final String TYPE_TEXT = "TEXT";
    private final String NOT_NULL = "NOT NULL";
    private final String PRIMARY_KEY = "PRIMARY KEY";
    private final String AUTOINCREMENT = "AUTOINCREMENT";
    private final String SEPERATOR = ",";
    private final String DEFAULT = "DEFAULT";

    public AiDbHelper(Context context) {
        super(context, "aidb.db", (CursorFactory)null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE aidb (_id INTEGER PRIMARY KEY AUTOINCREMENT, PLAYER_PICK TEXT NOT NULL, AI_PICK TEXT NOT NULL, RESULT TEXT);";
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newVersion) {
    }
}
