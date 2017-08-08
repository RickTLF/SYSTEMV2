package com.systems.sublime.systemv2.data;

import android.provider.BaseColumns;

/**
 * Created by Rick on 8/1/2017.
 */

public class NoteContract {

    public static final class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NOTE_TITLE = "noteTitle";
        public static final String COLUMN_NOTE_CONTENT = "noteContent";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
