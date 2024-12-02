package com.example.athlitecsapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athlitecsapp.dao.NoteDao
import com.example.athlitecsapp.dao.RoutineDao
import com.example.athlitecsapp.dao.StatusDao
import com.example.athlitecsapp.dao.UserDao
import com.example.athlitecsapp.dao.WorkOutsDao
import com.example.athlitecsapp.database.RunBoostDatabase
import com.example.athlitecsapp.model.Routine
import com.example.athlitecsapp.table.Note
import com.example.athlitecsapp.table.Status
import com.example.athlitecsapp.table.TimeTrial
import com.example.athlitecsapp.table.User
import com.example.athlitecsapp.table.Workouts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(context: Context) : ViewModel() {

    private var userDao: UserDao
    private var routineDao: RoutineDao
    private var workOutsDao: WorkOutsDao
    private var statusDao: StatusDao
    private var notesDao: NoteDao

    init {
        val database = RunBoostDatabase(context)
        userDao = database.getUserDao()
        routineDao = database.getRoutine()
        workOutsDao = database.getWorkouts()
        statusDao = database.getStatus()
        notesDao = database.getNotesDao()
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            try {
                // Perform database operation on IO dispatcher (background thread)
                withContext(Dispatchers.IO) {
                    notesDao.insertNote(note)
                }
                Log.d("InsertNote", "Successfully inserted note")
            } catch (e: Exception) {
                Log.e("InsertNote", "Failed to insert note", e)
            }
        }
    }
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                // Perform the delete operation on the IO dispatcher
                withContext(Dispatchers.IO) {
                    notesDao.deleteNoteById(note.id) // Assuming you're deleting by note ID
                }
                Log.d("DeleteNote", "Successfully deleted note with ID: ${note.id}")
            } catch (e: Exception) {
                Log.e("DeleteNote", "Failed to delete note with ID: ${note.id}", e)
            }
        }
    }
    fun updateNoteByid(noteId: Long, timeTrials: List<TimeTrial>){
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO){
                    notesDao.updateTimeTrials(noteId, timeTrials)
                }
                Log.d("UpdateNote", "Successfully updated note with ID: $noteId")
                } catch (e: Exception) {
                Log.e("UpdateNote", "Failed to update note with ID: $noteId", e)
            }
        }
    }
    fun getNoteById(id: Long, callback: (Note?) -> Unit) {
        viewModelScope.launch {
            try {
                val note = withContext(Dispatchers.IO) {
                    notesDao.getNoteById(id)
                }
                callback(note)
                Log.d("GetNoteById", "Successfully fetched note with ID: $id")
            } catch (e: Exception) {
                callback(null)
                Log.e("GetNoteById", "Failed to fetch note with ID: $id", e)
            }
        }
    }

    fun getAllNotes(onResult: (List<Note>) -> Unit) {
        viewModelScope.launch {
            val notes = withContext(Dispatchers.IO) {
                notesDao.getAllNotes()
            }
            onResult(notes)
        }
    }

    fun insertStatus(status: Status) {
        viewModelScope.launch {
            try {
                statusDao.insertStatus(status)
                Log.d("InsertStatus", "Successfully inserted status")
            } catch (e: Exception) {
                Log.e("InsertStatus", "Failed to insert status", e)
            }
        }
    }

    fun insertWorkouts(workouts: List<Workouts>) {
        viewModelScope.launch {
            try {
                workOutsDao.insertWorkouts(workouts)
                Log.d("InsertWorkouts", "Successfully inserted workouts")
            } catch (e: Exception) {
                Log.e("InsertWorkouts", "Failed to insert workouts", e)
            }

        }

    }

    fun getUserById(id: Int, callback: (User?) -> Unit) {
        viewModelScope.launch {
            try {
                val user = userDao.getUserById(id)
                callback(user)
            } catch (e: Exception) {
                Log.e("ViewModelSignUp", "Error fetching user by ID: ${e.message}")
            }
        }
    }

    fun getRoutineByCategory(category: String, callback: (List<Routine>) -> Unit) {
        viewModelScope.launch {
            try {
                val tiniklingList = routineDao.getRoutineByCategory(category)
                callback(tiniklingList!!)
            } catch (e: Exception) {
                Log.e("ViewModelSignUp", "Error fetching data: ${e.message}")
            }
        }
    }

    // ViewModel function to get workout data by ID
    fun getWorkoutsById(id: Int, callback: (Workouts?) -> Unit) {
        viewModelScope.launch {
            try {
                val workout = workOutsDao.getWorkoutsById(id)
                callback(workout)  // Pass the result to the callback
            } catch (e: Exception) {
                callback(null)  // Pass null if there's an error fetching the data
            }
        }
    }

    fun fetchWorkout(scope: String, id: Int, callback: (Workouts?) -> Unit) {
        viewModelScope.launch {
            try {
                val workout = workOutsDao.getWorkoutsByScopeAndId(scope, id)
                callback(workout)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

    fun getRoutineById(id: Int, callback: (Routine?) -> Unit) {
        viewModelScope.launch {
            try {
                val tinikling = routineDao.getRoutineById(id)
                callback(tinikling)
            } catch (e: Exception) {
            }
        }
    }

    fun getRoutineWithVideo(callback: (List<Routine>) -> Unit) {
        viewModelScope.launch {
            try {
                val tiniklingList = routineDao.getRoutineWithVideo()
                callback(tiniklingList)
            } catch (e: Exception) {
                Log.e(
                    "ViewModelSignUp",
                    "Error fetching tinikling with videos: ${e.message}"
                )
            }
        }
    }

    fun insertRoutine(routine: List<Routine>) {
        viewModelScope.launch {
            try {
                routineDao.insertRoutine(routine)
            } catch (e: Exception) {
                Log.e("ViewModelSignUp", "Error inserting routine: ${e.message}")
            }
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            try {
                userDao.insertUser(user)
            } catch (e: Exception) {
                Log.e("ViewModelSignUp", "Error inserting user: ${e.message}")
            }
        }
    }
}


