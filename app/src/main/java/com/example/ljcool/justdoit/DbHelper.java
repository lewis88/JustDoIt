package com.example.ljcool.justdoit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todo.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notes( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "heading TEXT, content TEXT, done INTEGER)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    public long insertData(String heading, String content, int done) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("heading", heading);
        contentValues.put("content", content);
        contentValues.put("done", done);
        return db.insert("notes", null, contentValues);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM notes", null);
    }

    public boolean updateData(String id, String heading, String content, int done) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("heading", heading);
        contentValues.put("content", content);
        contentValues.put("done", done);
        db.update("notes", contentValues, "id = ?", new String[]{id});
        return true;
    }

    public int deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("notes", "id = ?", new String[] {id});
    }

}
