package github.app.noteapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import github.app.noteapp.Note
import github.app.noteapp.NoteViewModel
import github.app.noteapp.databinding.FragmentAddEditNoteBinding
import github.app.noteapp.ui.activity.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class AddEditFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var binding: FragmentAddEditNoteBinding
    private lateinit var bundle: Bundle
    private lateinit var noteType: String
    var noteID = -1;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        addEvent()
    }


    private fun initView() {
        if (arguments != null) {
            bundle = requireArguments()
            noteType = bundle.getString("noteType").toString()
        } else
            noteType = ""

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        if (noteType == "Edit") {
            val noteTitle = bundle.getString("noteTitle")
            val noteDescription = bundle.getString("noteDescription")
            noteID = bundle.getInt("noteId", -1)
            binding.idEdtNoteName.setText(noteTitle)
            binding.idEdtNoteDesc.setText(noteDescription)
            binding.idBtn.text = "Update Note"
        } else {
            binding.idBtn.text = "Save Note"
        }
    }

    private fun addEvent() {
        with(binding) {
            idBtn.setOnClickListener {
                val noteTitle = idEdtNoteName.text.toString()
                val noteDescription = idEdtNoteDesc.text.toString()
                if (noteType == "Edit") {
                    onEditNote(noteTitle, noteDescription)
                } else {
                    onAddNote(noteTitle, noteDescription)
                }
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }

    private fun onEditNote(noteTitle: String, noteDescription: String) {
        if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
            val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
            val currentDateAndTime: String = sdf.format(Date())
            val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
            updatedNote.id = noteID
            if (viewModel.updateNote(updatedNote)) {
                Toast.makeText(activity, "Note Updated..", Toast.LENGTH_LONG).show()
                activity?.finish()
            }
        }
    }

    private fun onAddNote(noteTitle: String, noteDescription: String) {
        if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
            val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
            val currentDateAndTime: String = sdf.format(Date())
            val note = Note(noteTitle, noteDescription, currentDateAndTime)
            if (viewModel.insertNote(note)
            ) {
                Toast.makeText(activity, "$noteTitle Added", Toast.LENGTH_SHORT).show()
                activity?.finish()
            }
        }
    }


}