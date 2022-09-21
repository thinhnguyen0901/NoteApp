package github.app.noteapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import github.app.noteapp.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {


    abstract fun getNoteDAO(): NoteDao

    companion object {
        private const val nameDatabase = "note_database"
        fun getDatabase(context: Context): NoteDatabase {
            return Room.databaseBuilder(
                context.applicationContext, NoteDatabase::class.java, nameDatabase
            ).build()
        }
    }
}
