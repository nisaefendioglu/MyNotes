package com.task.noteapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.noteapp.R
import com.task.noteapp.adapter.NoteAdapter
import com.task.noteapp.databinding.FragmentHomeBinding
import com.task.noteapp.model.NoteModel
import com.task.noteapp.view.MainActivity
import com.task.noteapp.viewModel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener,
    MenuProvider {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    internal var pStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        setRecyclerView()
        binding.btnAddNote.setOnClickListener { mView ->
            mView.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)
        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = true
        mMenuSearch.setOnQueryTextListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchNote(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNote(newText)
        }
        return true
    }

    private fun setRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner) { note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }

    private fun updateUI(note: List<NoteModel>) {
        if (note.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.notes.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.notes.visibility = View.VISIBLE
        }
    }

    private fun searchNote(query: String?) {
        val searchQuery = "%$query%"
        noteViewModel.searchNote(searchQuery).observe(this) { list ->
            noteAdapter.differ.submitList(list)
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

}