package com.systems.sublime.systemv2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.systems.sublime.systemv2.adapters.NoteAdapter;
import com.systems.sublime.systemv2.data.NoteContract;
import com.systems.sublime.systemv2.data.NoteDbHelper;

import java.util.ArrayList;

public class DisplayNoteActivity extends AppCompatActivity { // implements DummyFragment.onChangeListener {

    private static SQLiteDatabase mDb;
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);
        int message;
        Cursor cursor;

        // TODO: Change the notification bar color to white and color symbols black
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NoteDbHelper dbHelper = new NoteDbHelper(this);
        mDb = dbHelper.getReadableDatabase();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        message = intent.getIntExtra(NoteAdapter.ViewHolder.EXTRA_MESSAGE, 0);
        cursor = getAllNotes();
        cursor.moveToPosition(message);

        // Create an array list to pass strings of the current resource
        final ArrayList<String> noteData = new ArrayList<>();
        noteData.add(cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_NOTE_TITLE)));
        noteData.add(cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_NOTE_CONTENT)));
        noteData.add(cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_TIMESTAMP)));

        try {
            getSupportActionBar().setTitle(noteData.get(0));
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();
        }

        noteData.add("" + message);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_notes);

        FragmentDataAdapter adapter = new FragmentDataAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter();
        mViewPager.addOnPageChangeListener(mViewPagerAdapter);
        mViewPager.setCurrentItem(message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private class ViewPagerAdapter implements ViewPager.OnPageChangeListener {

        /**
         * This method will be invoked when the current page is scrolled, either as part
         * of a programmatically initiated smooth scroll or a user initiated touch scroll.
         *
         * @param position             Position index of the first page currently being displayed.
         *                             Page position+1 will be visible if positionOffset is nonzero.
         * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
         * @param positionOffsetPixels Value in pixels indicating the offset from position.
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param position Position index of the new selected page.
         */
        @Override
        public void onPageSelected(int position) {

            // TODO: Implement a more efficient method
            Cursor onPageCursor = getAllNotes();
            onPageCursor.moveToPosition(position);
            toolbar.setTitle(onPageCursor.getString(onPageCursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_NOTE_TITLE)));
        }

        /**
         * Called when the scroll state changes. Useful for discovering when the user
         * begins dragging, when the pager is automatically settling to the current page,
         * or when it is fully stopped/idle.
         *
         * @param state The new scroll state.
         * @see ViewPager#SCROLL_STATE_IDLE
         * @see ViewPager#SCROLL_STATE_DRAGGING
         * @see ViewPager#SCROLL_STATE_SETTLING
         */
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    private static class FragmentDataAdapter extends FragmentStatePagerAdapter {
        Cursor noteCursor;

        FragmentDataAdapter(FragmentManager fm) {
            super(fm);
            noteCursor = getAllDummyNotes();
        }
        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position of the current fragment scrolled.
         */
        @Override
        public Fragment getItem(int position) {
            noteCursor.moveToPosition(position);

            // Create an array list to pass strings of the current resource
            ArrayList<String> noteData = new ArrayList<>();
            noteData.add(noteCursor.getString(noteCursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_NOTE_TITLE)));
            noteData.add(noteCursor.getString(noteCursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_NOTE_CONTENT)));
            noteData.add(noteCursor.getString(noteCursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_TIMESTAMP)));
            noteData.add("" + position);
            return NoteFragment.newInstance(noteData);
        }

        private Cursor getAllDummyNotes () {
            return mDb.query(
                    NoteContract.NoteEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    NoteContract.NoteEntry.COLUMN_TIMESTAMP
            );
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return noteCursor.getCount();
        }
    }

    private Cursor getAllNotes() {
        return mDb.query(
                NoteContract.NoteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NoteContract.NoteEntry.COLUMN_TIMESTAMP
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}