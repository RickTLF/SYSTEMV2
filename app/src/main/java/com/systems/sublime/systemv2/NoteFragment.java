package com.systems.sublime.systemv2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rick on 8/6/2017.
 */

public class NoteFragment extends Fragment {
    public static String TITLE = "";
    public static String ARGS_KEY = "note";

    /**
     * Create a new instance of DummyFragment, creating dummy
     * as an argument.
     * @return DummyFragment
     */
    static NoteFragment newInstance(ArrayList noteTitle) {
        NoteFragment dummyFragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARGS_KEY, noteTitle);
        dummyFragment.setArguments(args);
        return dummyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        Bundle args = getArguments();

        if (args != null) {
            ArrayList<String> message;
            message = args.getStringArrayList(ARGS_KEY);

            assert message != null;
            if (!message.isEmpty()) {

                int i = 1;

                if (message.size() > 1) {
                    while (i < message.size()) {
                        if (message.get(i) == null) message.set(1, "Empty");
                        i++;
                    }
                } else {
                    while (i < 3) {
                        message.add("empty");
                        i++;
                    }
                }

                TITLE = message.get(0);
                updateContent(view, false, message.get(1));
                updateTimestamp(view, message.get(2));

                updateDebugInfo(view, "Position clicked at:\t" + message.get(3));
            } else {
                updateDebugInfo(view, "ERROR:\tArguments sent to fragment is empty.");
            }
        } else {
            Log.e("NoteFragment args", "No arguments passed to NoteFragment.");
        }
        return view;
    }

    public void updateContent(View rootView, boolean isError, String text) {
        TextView tvNote = (TextView) rootView.findViewById(R.id.tv_content);
        tvNote.setText(text);
        if (isError) tvNote.setTextColor(Color.RED);
    }

    public void updateTimestamp(View rootView, String text) {
        TextView tvTimeStamp = (TextView) rootView.findViewById(R.id.tv_timestamp);
        tvTimeStamp.setText(text);
    }

    public void updateDebugInfo(View rootView, String text) {
        TextView tvTimeStamp = (TextView) rootView.findViewById(R.id.tv_debug);
        tvTimeStamp.setText(text);
    }
}
