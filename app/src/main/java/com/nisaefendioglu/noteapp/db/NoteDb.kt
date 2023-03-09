package com.nisaefendioglu.noteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nisaefendioglu.noteapp.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class NoteDb : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    companion object {

        private const val DATABASE_NAME = "notes_db"

        @Volatile
        private var instance: NoteDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDb::class.java,
            DATABASE_NAME
        ).build()
    }
}