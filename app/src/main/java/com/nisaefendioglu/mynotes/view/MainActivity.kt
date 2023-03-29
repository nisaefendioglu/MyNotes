package com.nisaefendioglu.mynotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.nisaefendioglu.mynotes.databinding.ActivityMainBinding
import com.nisaefendioglu.mynotes.db.NoteDb
import com.nisaefendioglu.mynotes.repository.NoteRepository
import com.nisaefendioglu.mynotes.viewModel.NoteViewModel
import com.nisaefendioglu.mynotes.viewModel.NoteViewModelProviderFactory

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