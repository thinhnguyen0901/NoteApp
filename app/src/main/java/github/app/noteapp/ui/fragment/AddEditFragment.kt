package github.app.noteapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
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


    private fun addEvent() {
        with(binding) {
            idBtn.setOnClickListener {
                val noteTitle = idEdtNoteName.text.toString()
                val noteDescription = idEdtNoteDesc.text.toString()
                if (noteType.equals("Edit")) {
                    if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                        val currentDateAndTime: String = sdf.format(Date())
                        val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                        updatedNote.id = noteID
                        viewModel.updateNote(updatedNote)
                        Toast.makeText(activity, "Note Updated..", Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                        val currentDateAndTime: String = sdf.format(Date())
                        // if the string is not empty we are calling a
                        // add note method to add data to our room database.
                        viewModel.insertNote(Note(noteTitle, noteDescription, currentDateAndTime))
                        Toast.makeText(activity, "$noteTitle Added", Toast.LENGTH_LONG).show()
                    }
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.getFragmentManager()?.popBackStack();
                }
            }
        }
    }

    private fun initView() {
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        val bundle = this.arguments
        if (bundle != null) {
            noteType = bundle.getString("noteType")!!
            if (noteType.equals("Edit")) {
                val noteTitle = bundle.getString("noteTitle")
                val noteDescription = bundle.getString("noteDescription")
                binding.idEdtNoteName.setText(noteTitle)
                binding.idEdtNoteDesc.setText(noteDescription)
                binding.idBtn.text = "Update Note"
            } else {
                binding.idBtn.text = "Save Note"
            }
        }
    }


}