package github.app.noteapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.app.noteapp.data.NoteDao
import github.app.noteapp.data.NoteDatabase
import github.app.noteapp.data.NoteRepo

class NoteViewModel(application: Application) :ViewModel() {



    val allNotes : LiveData<List<Note>>
    private val repo : NoteRepo

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDAO()
        repo= NoteRepo(dao)
        allNotes = repo.allNotes
    }

    fun insertNote(note: Note) :Boolean {
        return try {
            repo.insert(note)
            true
        } catch (e: Exception) {
            Log.e("Lỗi thêm dữ liệu: ", e.message!!)
            false
        }
    }

    fun updateNote(note: Note)  :Boolean{
        return try {
            repo.update(note)
            true
        } catch (e: Exception) {
            Log.e("Lỗi thêm dữ liệu: ", e.message!!)
            false
        }
    }
    fun deleteNote(note: Note)  :Boolean{
        return try {
            repo.delete(note)
            true
        } catch (e: Exception) {
            Log.e("Lỗi thêm dữ liệu: ", e.message!!)
            false
        }
    }
}