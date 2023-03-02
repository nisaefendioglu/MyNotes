package com.task.noteapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.task.noteapp.databinding.ActivityMainBinding
import com.task.noteapp.db.NoteDb
import com.task.noteapp.repository.NoteRepository
import com.task.noteapp.viewModel.NoteViewModel
import com.task.noteapp.viewModel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setViewModel()
    }

    private fun setViewModel() {
        val noteRepository = NoteRepository(
            NoteDb(this)
        )
        val viewModelProviderFactory = NoteViewModelProviderFactory(
            application, noteRepository
        )
        noteViewModel = ViewModelProvider(
            this, viewModelProviderFactory
        )[NoteViewModel::class.java]
    }
}