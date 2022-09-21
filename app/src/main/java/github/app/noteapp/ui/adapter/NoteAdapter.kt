package github.app.noteapp.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import github.app.noteapp.Note
import github.app.noteapp.contract.DeleteNoteContract
import github.app.noteapp.contract.ClickNoteContract
import github.app.noteapp.databinding.ItemNoteBinding

class NoteAdapter(
    val context: Context,
    private val deleteNoteContract: DeleteNoteContract,
    private val clickNoteContract: ClickNoteContract,
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    // on below line we are creating a
    // variable for our all notes list.
    private val allNotes = ArrayList<Note>()

    // on below line we are creating a view holder class.
    inner class ViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file for each item of recycler view.
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // on below line we are setting data to item of recycler view.
        with(holder) {
            with(allNotes[position]) {
                binding.idTVNote.text = this.noteTitle
                binding.idTVDate.text = this.timeStamp
                binding.idIVDelete.setOnClickListener {
                    deleteNoteContract.onDeleteNote(this)
                }
                binding.root.setOnClickListener {
                    clickNoteContract.onClickNote(this)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    // below method is use to update our list of notes.
    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}