package com.example.ljcool.justdoit;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AbsActivity {

    private TextView mContent;
    private String mContentText;
    private String mHeadingText;
    private String mId;
    private int mTaskComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mId = extras.getString("id");
        mHeadingText = extras.getString("headingText");
        mContentText = extras.getString("contentText");
        mTaskComplete = extras.getInt("taskComplete");

        setTitle(mHeadingText);

        mContent = (TextView) findViewById(R.id.note_content);
        setContent();
        setListeners();

    }

    public void setContent() {
        mContent.setText(mContentText);
    }

    public void setListeners() {
        final EditText editTextContent = createEditText(mContentText);

        mContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ViewGroup noteBoard = getNoteBoard();
                noteBoard.removeView(noteBoard.getChildAt(0));
                noteBoard.addView(editTextContent, 0);
                return true;
            }
        });

        editTextContent.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ( (event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) ) {
                    mContentText = editTextContent.getText().toString();
                    setContent();

                    ViewGroup noteBoard = getNoteBoard();
                    noteBoard.removeView(editTextContent);
                    noteBoard.addView(mContent, 0);
                    updateDb(mId, mHeadingText, mContentText, mTaskComplete);
                }
                return false;
            }
        });
    }

    public EditText createEditText(String text) {
        EditText editText = new EditText(this);
        editText.setBackgroundResource(R.drawable.text_edit);
        editText.setText(text);
        return editText;
    }

}
