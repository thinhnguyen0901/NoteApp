package github.app.noteapp.contract

import github.app.noteapp.Note

interface ClickNoteContract {
    fun onClickNote(note: Note)
}