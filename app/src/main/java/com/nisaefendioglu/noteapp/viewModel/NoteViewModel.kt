package com.nisaefendioglu.noteapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nisaefendioglu.noteapp.model.NoteModel
import com.nisaefendioglu.noteapp.repository.NoteRepository
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