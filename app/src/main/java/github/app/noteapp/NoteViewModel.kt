package github.app.noteapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import github.app.noteapp.data.NoteDatabase
import github.app.noteapp.data.NoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val listNote: LiveData<List<Note>>
    private val repo: NoteRepo

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repo = NoteRepo(dao)
        listNote = repo.listNote
    }

    fun insertNote(note: Note): Boolean {
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                repo.insert(note)
            }
            true
        } catch (e: Exception) {
            Log.e("Lỗi thêm dữ liệu: ", e.message!!)
            false
        }
    }

    fun updateNote(note: Note): Boolean {
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                repo.update(note)
            }
            true
        } catch (e: Exception) {
            Log.e("Lỗi sửa dữ liệu: ", e.message!!)
            false
        }
    }

    fun deleteNote(note: Note): Boolean {
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                repo.delete(note)
            }
            true
        } catch (e: Exception) {
            Log.e("Lỗi xóa dữ liệu: ", e.message!!)
            false
        }
    }
}