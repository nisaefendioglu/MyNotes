package com.task.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.task.noteapp.model.NoteModel

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: NoteModel)
    @Delete
    suspend fun deleteNote(note: NoteModel)
    @Update
    suspend fun updateNote(note: NoteModel)
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<NoteModel>>
    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query OR noteBody LIKE :query")
    fun searchNote(query: String?): LiveData<List<NoteModel>>
}