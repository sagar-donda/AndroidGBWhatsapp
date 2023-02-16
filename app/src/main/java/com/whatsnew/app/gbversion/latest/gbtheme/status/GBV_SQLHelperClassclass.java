package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class GBV_SQLHelperClassclass extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE = "notepad";
    public static final String TEXT = "text";
    public static final String TITLE = "title";

    public GBV_SQLHelperClassclass(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Log.d("EventsData", "onCreate: create table notepad( _id integer primary key autoincrement, title text not null, text text);");
        sQLiteDatabase.execSQL("create table notepad( _id integer primary key autoincrement, title text not null, text text);");
    }

    @Override 
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < i2) {
            String str = null;
            if (i == 1) {
                str = "alter table notepad add note text;";
            }
            if (i == 2) {
                str = "";
            }
            Log.d("EventsData", "onUpgrade : " + str);
            if (str != null) {
                sQLiteDatabase.execSQL(str);
            }
        }
    }
}
