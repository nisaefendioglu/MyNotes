package com.nisaefendioglu.noteapp.db

import androidx.room.Room
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nisaefendioglu.noteapp.getOrAwaitValue
import com.nisaefendioglu.noteapp.model.NoteModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDbTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteDb: NoteDb
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        noteDb = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            NoteDb::class.java
        ).build()
        noteDao = noteDb.getNoteDao()
    }

    @After
    fun tearDown() {
        noteDb.close()
    }

    @Test
    fun getAllNoteItem() = runBlocking {
        val note = NoteModel(1, "title", "body", "", "2021-01-01 00:00:00", "2021-01-01 00:00:00")
        noteDao.addNote(note)
        val allNotes = noteDao.getAllNotes().getOrAwaitValue()
        assertThat(allNotes).contains(note)
    }

    @Test
    fun deleteNoteItem() = runBlocking {
        val note = NoteModel(1, "title", "body", "", "2021-01-01 00:00:00", "2021-01-01 00:00:00")
        noteDao.addNote(note)
        noteDao.deleteNote(note)
        val allNotes = noteDao.getAllNotes().getOrAwaitValue()
        assertThat(allNotes).doesNotContain(note)
    }

    @Test
    fun updateNoteItem() = runBlocking {
        val note = NoteModel(1, "title", "body", "", "2021-01-01 00:00:00", "2021-01-01 00:00:00")
        noteDao.addNote(note)
        val updatedNote =
            NoteModel(1, "title", "body", "", "2021-01-01 00:00:00", "2021-01-01 00:00:00")
        noteDao.updateNote(updatedNote)
        val allNotes = noteDao.getAllNotes().getOrAwaitValue()
        assertThat(allNotes).contains(updatedNote)
    }

    @Test
    fun searchNoteItem() = runBlocking {
        val note = NoteModel(1, "title", "body", "", "2021-01-01 00:00:00", "2021-01-01 00:00:00")
        noteDao.addNote(note)
        val allNotes = noteDao.searchNote("title").getOrAwaitValue()
        assertThat(allNotes).contains(note)
    }

}