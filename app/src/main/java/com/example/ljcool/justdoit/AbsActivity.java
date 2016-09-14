package com.example.ljcool.justdoit;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import java.util.ArrayList;

public abstract class AbsActivity extends AppCompatActivity {

    DbHelper mDb;
    NoteDisplay mNoteDisplay;
    ArrayList<ViewGroup> mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = new DbHelper(this);
    }

    public String addToDb( String headingText, String contentText, int taskComplete){
        long result = mDb.insertData(headingText, contentText, taskComplete);
        return Long.toString(result);
    }

    public ViewGroup createNote(String headingText, String contentText, int taskComplete, String id) {
        mNoteDisplay = new NoteDisplay(AbsActivity.this, headingText, contentText, taskComplete, id);
        return mNoteDisplay.buildNote();
    }

    public ArrayList<ViewGroup> getNotes() {
        Cursor cursor = mDb.getAllData();
        mNotes = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String headingText = cursor.getString(cursor.getColumnIndex("heading"));
                String contentText = cursor.getString(cursor.getColumnIndex("content"));
                int taskComplete = Integer.parseInt(cursor.getString(cursor.getColumnIndex("done")));
                ViewGroup note = createNote(headingText, contentText, taskComplete, id);
                mNotes.add(note);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return mNotes;
    }

    public ViewGroup getNoteBoard() {
        return (ViewGroup) findViewById(R.id.note_board);
    }

    public ViewGroup getNoteList() {
        return (ViewGroup) findViewById(R.id.note_list);
    }

    public void updateDb(String id, String headingText, String contentText, int taskComplete) {
        mDb.updateData(id, headingText, contentText, taskComplete);
    }

    public void removeNote(ViewGroup note) {
        ViewGroup noteList = getNoteList();
        noteList.removeView(note);
    }

    public void deleteFromDb(String id) {
        mDb.deleteData(id);
    }

}
