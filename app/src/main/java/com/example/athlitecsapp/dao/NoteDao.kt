package com.example.athlitecsapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athlitecsapp.table.Note
import com.example.athlitecsapp.table.TimeTrial

@Dao
interface NoteDao {

    // Insert a note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    // Query a note by its ID
    @Query("SELECT * FROM Note WHERE id = :noteId LIMIT 1")
    suspend fun getNoteById(noteId: Long): Note?

    // Update a note with new time trials
    @Query("UPDATE Note SET timeTrials = :timeTrials WHERE id = :noteId")
    suspend fun updateTimeTrials(noteId: Long, timeTrials: List<TimeTrial>)

    // Get all notes
    @Query("SELECT * FROM Note")
    suspend fun getAllNotes(): List<Note>

    // Delete a note by its ID
    @Query("DELETE FROM Note WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long)
}
