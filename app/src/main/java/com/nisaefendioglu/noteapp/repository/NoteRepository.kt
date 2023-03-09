package com.nisaefendioglu.noteapp.repository

import com.nisaefendioglu.noteapp.db.NoteDb
import com.nisaefendioglu.noteapp.model.NoteModel

class NoteRepository(private val db: NoteDb) {
    suspend fun addNote(note: NoteModel) = db.getNoteDao().addNote(note)
    suspend fun deleteNote(note: NoteModel) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: NoteModel) = db.getNoteDao().updateNote(note)
    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)
}