package com.nisaefendioglu.mynotes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nisaefendioglu.mynotes.model.NoteModel
import com.nisaefendioglu.mynotes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {
    fun addNote(note: NoteModel) = viewModelScope.launch {
        noteRepository.addNote(note)
    }
    fun deleteNote(note: NoteModel) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }
    fun updateNote(note: NoteModel) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }
    fun getAllNotes() = noteRepository.getAllNotes()
    fun searchNote(query: String?) = noteRepository.searchNote(query)
}