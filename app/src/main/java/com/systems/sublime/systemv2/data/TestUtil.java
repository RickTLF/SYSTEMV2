package com.systems.sublime.systemv2.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Test utility meant for testing database functionality concerning
 * notes. After functionality such as adding and deleting notes
 * have been implemented and tested successfully, this class is no
 * longer used.
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
 * @since   01-08-2017
 */
public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db) {
        if (db == null) {return;}

        // Create a few fake notes
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(NoteContract.NoteEntry.COLUMN_NOTE_TITLE, "Hello world!");
        cv.put(NoteContract.NoteEntry.COLUMN_NOTE_CONTENT, "Title 1 has content.");
        list.add(cv);

        cv = new ContentValues();
        cv.put(NoteContract.NoteEntry.COLUMN_NOTE_TITLE, "Some second title");
        cv.put(NoteContract.NoteEntry.COLUMN_NOTE_CONTENT, "Title 2 has content.");
        list.add(cv);

        cv = new ContentValues();
        cv.put(NoteContract.NoteEntry.COLUMN_NOTE_TITLE, "Third title");

        // Entry for content of note with 'title 3' has been deliberately left out
        list.add(cv);

        //insert all notes in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (NoteContract.NoteEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(NoteContract.NoteEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }
    }
}
