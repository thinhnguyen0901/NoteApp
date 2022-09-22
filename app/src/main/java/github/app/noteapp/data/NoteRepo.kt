package github.app.noteapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import github.app.noteapp.Note

class NoteRepo(private val noteDao: NoteDao) {

    val listNote: LiveData<List<Note>> = noteDao.getListNote()

    fun insert(note: Note) {
        noteDao.insertNote(note)
    }

    fun update(note: Note) {
        noteDao.updateNote(note)
    }

    fun delete(note: Note) {
        noteDao.deleteNote(note)
    }
}