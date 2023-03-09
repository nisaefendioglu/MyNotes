package com.nisaefendioglu.noteapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.nisaefendioglu.noteapp.databinding.ActivityMainBinding
import com.nisaefendioglu.noteapp.db.NoteDb
import com.nisaefendioglu.noteapp.repository.NoteRepository
import com.nisaefendioglu.noteapp.viewModel.NoteViewModel
import com.nisaefendioglu.noteapp.viewModel.NoteViewModelProviderFactory

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