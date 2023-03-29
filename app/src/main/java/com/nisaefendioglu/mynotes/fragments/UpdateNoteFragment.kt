package com.nisaefendioglu.mynotes.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.nisaefendioglu.mynotes.view.MainActivity
import com.nisaefendioglu.mynotes.R
import com.nisaefendioglu.mynotes.databinding.FragmentUpdateNoteBinding
import com.nisaefendioglu.mynotes.model.NoteModel
import com.nisaefendioglu.mynotes.viewModel.NoteViewModel


class UpdateNoteFragment : Fragment(), MenuProvider {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: NoteModel
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!
        binding.titleUpdate.setText(currentNote.noteTitle)
        binding.descriptionUpdate.setText(currentNote.noteBody)
        binding.imgUpdate.setText(currentNote.noteImageUrl)

        binding.btnUpdate.setOnClickListener {
            val title = binding.titleUpdate.text.toString().trim()
            val body = binding.descriptionUpdate.text.toString().trim()
            val image = binding.imgUpdate.text.toString().trim()
            val date = currentNote.noteDate
            val editInfo = getString(R.string.edit_note)

            if (title.isNotEmpty() || title != currentNote.noteTitle || body != currentNote.noteBody || image != currentNote.noteImageUrl) {
                val note = NoteModel(currentNote.id, title, body, image, date, editInfo)
                noteViewModel.updateNote(note)
                Toast.makeText(context, getString(R.string.updated_note), Toast.LENGTH_SHORT).show()

                view.findNavController().navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            } else {
                Toast.makeText(context, getString(R.string.warning_title), Toast.LENGTH_SHORT).show()
            }
        }
        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.update_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.delete_menu -> {
                deleteNote()
            }
        }
        return super.onContextItemSelected(menuItem)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle(getString(R.string.delete_note))
            setMessage(getString(R.string.delete_message))
            setPositiveButton("DELETE") { _, _ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
                Toast.makeText(context, getString(R.string.deleted), Toast.LENGTH_SHORT).show()
            }
            setNegativeButton(getString(R.string.cancel), null)
        }.create().show()
    }

}