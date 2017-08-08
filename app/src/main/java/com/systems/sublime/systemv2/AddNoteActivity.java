package com.systems.sublime.systemv2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.systems.sublime.systemv2.data.NoteContract;
import com.systems.sublime.systemv2.data.NoteDbHelper;

public class AddNoteActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // FIXME: Remove this and change primaryDarkColor before uploading on Play
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add a new note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NoteDbHelper noteDbHelper = new NoteDbHelper(this);
        mDb = noteDbHelper.getWritableDatabase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void cancelNote(View view) {

        // TODO: Save the note to draft if not empty
        finish();
    }

    void saveNote(View view) {

        // Check if the edit texts are empty
        EditText etTitle = (EditText) findViewById(R.id.et_title);
        EditText etContent = (EditText) findViewById(R.id.et_content);
        if (etTitle.getText().length() == 0 || etContent.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Note is empty.", Toast.LENGTH_SHORT).show();
        } else {
            saveNoteToDatabase(etTitle.getText().toString(), etContent.getText().toString());
        }
        finish();
    }

    private long saveNoteToDatabase(String title, String content) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteContract.NoteEntry.COLUMN_NOTE_TITLE, title);
        contentValues.put(NoteContract.NoteEntry.COLUMN_NOTE_CONTENT, content);
        return mDb.insert(NoteContract.NoteEntry.TABLE_NAME, null, contentValues);
    }
}
