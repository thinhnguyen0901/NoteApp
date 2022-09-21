package github.app.noteapp.contract

import github.app.noteapp.Note

interface DeleteNoteContract {
    fun onDeleteNote(note: Note)
}