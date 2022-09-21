package github.app.noteapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import github.app.noteapp.Note

@Dao
interface NoteDao {

    @Query("select * from note order by id asc")
    fun getListNote():  LiveData<List<Note>>

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}