package github.app.noteapp.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import github.app.noteapp.Note
import github.app.noteapp.NoteViewModel
import github.app.noteapp.contract.DeleteNoteContract
import github.app.noteapp.contract.ClickNoteContract
import github.app.noteapp.databinding.ActivityMainBinding
import github.app.noteapp.ui.adapter.NoteAdapter
import github.app.noteapp.ui.fragment.AddEditFragment


class MainActivity : AppCompatActivity(), DeleteNoteContract, ClickNoteContract {

    private lateinit var viewModel: NoteViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        addEvent()
    }

    private fun addEvent() {
        binding.idFAB.setOnClickListener {
            val myFragment = AddEditFragment()
            supportFragmentManager.beginTransaction()
                .add(
                    binding.fragmentContainer.id,
                    myFragment,
                    AddEditFragment::class.java.simpleName
                )
                .commit()
            binding.idFAB.visibility = View.GONE
        }
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.notesRV.layoutManager = linearLayoutManager
        val noteAdapter = NoteAdapter(this, this, this)
        binding.notesRV.adapter = noteAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        viewModel.listNote.observe(this, Observer { list ->
            list?.let {
                noteAdapter.updateList(it)
            }
        })
    }

    override fun onClickNote(note: Note) {

        val fragment = AddEditFragment()
        val bundle = Bundle()
        bundle.putString("noteType", "Edit")
        bundle.putString("noteTitle", note.noteTitle)
        bundle.putString("noteDescription", note.noteDescription)
        bundle.putInt("noteId", note.id)
        fragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, fragment)
        transaction.commit()
        binding.idFAB.visibility = View.GONE
    }

    override fun onDeleteNote(note: Note) {
        if (viewModel.deleteNote(note))
            Toast.makeText(
                this,
                "Xóa thành công ${note.noteTitle}",
                Toast.LENGTH_SHORT
            ).show()
        else
            Toast.makeText(
                this,
                "Xóa không thành công",
                Toast.LENGTH_SHORT
            ).show()
    }
}