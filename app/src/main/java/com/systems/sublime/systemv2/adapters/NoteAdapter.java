package com.systems.sublime.systemv2.adapters;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.systems.sublime.systemv2.DisplayNoteActivity;
import com.systems.sublime.systemv2.R;
import com.systems.sublime.systemv2.data.NoteContract;

/**
 * Used to display notes. Deletion is implemented in
 * 'MainActivity'. Implementation concerning the Notes database
 * table is also provided: displaying all notes in list and
 * updating the list (RecyclerView).
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author  Rick Kock
 * @version V1.0
 * @since   08-08-2017
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private static Cursor mCursor;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public static final String EXTRA_MESSAGE = "MESSAGE";


        ViewHolder(TextView v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView);

            mTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!mCursor.moveToPosition(getAdapterPosition())) {
                        return;
                    }
                    Intent intent = new Intent(v.getContext(), DisplayNoteActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, getAdapterPosition());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    public NoteAdapter(Cursor myCursor) {
        mCursor = myCursor;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_text_view, parent, false);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        long id = mCursor.getLong(mCursor.getColumnIndex(NoteContract.NoteEntry._ID));
        String title = mCursor.getString(mCursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_NOTE_TITLE));
        holder.mTextView.setText(title);
        holder.itemView.setTag(id);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}