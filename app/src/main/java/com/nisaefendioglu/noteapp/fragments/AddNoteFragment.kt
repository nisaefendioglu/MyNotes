package com.nisaefendioglu.noteapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.nisaefendioglu.noteapp.view.MainActivity
import com.nisaefendioglu.noteapp.R
import com.nisaefendioglu.noteapp.model.NoteModel
import com.nisaefendioglu.noteapp.viewModel.NoteViewModel
import com.nisaefendioglu.noteapp.databinding.FragmentAddNoteBinding
import com.nisaefendioglu.noteapp.util.getCurrentDateFormatted
import java.util.*


class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        mView = view
        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.save_menu -> {
                saveNote(mView)
            }
        }
        return super.onContextItemSelected(menuItem)
    }

    private fun saveNote(view: View) {
        val noteTitle = binding.noteTitle.text.toString().trim()
        val noteBody = binding.noteDesc.text.toString().trim()
        val noteImage = binding.noteImg.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val currentDate = Date().getCurrentDateFormatted()
            val note = NoteModel(0, noteTitle, noteBody, noteImage, currentDate, "")
            noteViewModel.addNote(note)
            Toast.makeText(context, getString(R.string.saved_note), Toast.LENGTH_SHORT).show()

            view.findNavController().navigate(
                R.id.action_newNoteFragment_to_homeFragment
            )
        } else {
            Toast.makeText(context, getString(R.string.enter_note_title), Toast.LENGTH_SHORT).show()
        }
    }
}