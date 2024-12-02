package com.example.athlitecsapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athlitecsapp.dao.RoutineDao
import com.example.athlitecsapp.dao.StatusDao
import com.example.athlitecsapp.dao.UserDao
import com.example.athlitecsapp.dao.WorkOutsDao
import com.example.athlitecsapp.database.RunBoostDatabase
import com.example.athlitecsapp.model.Routine
import com.example.athlitecsapp.table.Status
import com.example.athlitecsapp.table.User
import com.example.athlitecsapp.table.Workouts
import kotlinx.coroutines.launch

class SignUpModel(context: Context) : ViewModel() {


    private var routineDao: RoutineDao
    private var statusDao: StatusDao
    private var workOutsDao: WorkOutsDao
    val statusLiveData = MutableLiveData<Status>()
    init {
        val database = RunBoostDatabase(context)

        routineDao = database.getRoutine()
        statusDao = database.getStatus()
        workOutsDao = database.getWorkouts()

    }

    fun getAllStatus() {
        viewModelScope.launch {
            try {
                val status = statusDao.getAllStatus() // Replace with your actual DAO method
                statusLiveData.postValue(status)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateLevel1(id: Int, level1: Int, level1DayProgress: Int) {
        viewModelScope.launch {
            try {
                statusDao.updateLevel1AndProgress(id, level1, level1DayProgress)
                Log.d("UpdateStatus", "Successfully updated level1 and level1DayProgress for id: $id")
            } catch (e: Exception) {
                Log.e("UpdateStatus", "Failed to update level1 and level1DayProgress for id: $id", e)
            }
        }
    }

    fun updateLevel2(id: Int, level2: Int, level2DayProgress: Int) {
        viewModelScope.launch {
            try {
                statusDao.updateLevel2AndProgress(id, level2, level2DayProgress)
                Log.d("UpdateStatus", "Successfully updated level2 and level2DayProgress for id: $id")
            } catch (e: Exception) {
                Log.e("UpdateStatus", "Failed to update level2 and level2DayProgress for id: $id", e)
            }
        }
    }

    fun updateLevel3(id: Int, level3: Int, level3DayProgress: Int) {
        viewModelScope.launch {
            try {
                statusDao.updateLevel3AndProgress(id, level3, level3DayProgress)
                Log.d("UpdateStatus", "Successfully updated level3 and level3DayProgress for id: $id")
            } catch (e: Exception) {
                Log.e("UpdateStatus", "Failed to update level3 and level3DayProgress for id: $id", e)
            }
        }
    }

    fun updateLevel4(level4DayProgress: Int) {
        viewModelScope.launch {
            try {
                statusDao.updateLevel4DayProgressForAll(level4DayProgress)
                Log.d("UpdateStatus", "Successfully updated level4DayProgressForAll")
            } catch (e: Exception) {
                Log.e("UpdateStatus", "Failed to update level4DayProgressForAll", e)
            }
        }
    }

    fun updateLevel5(level5DayProgress: Int) {
        viewModelScope.launch {
            try {
                statusDao.updateLevel5DayProgressForAll(level5DayProgress)
                Log.d("UpdateStatus", "Successfully updated level4DayProgressForAll")
            } catch (e: Exception) {
                Log.e("UpdateStatus", "Failed to update level4DayProgressForAll", e)
            }
        }
    }
    fun updateLevel6(level6DayProgress: Int) {
        viewModelScope.launch {
            try {
                statusDao.updateLevel6DayProgressForAll(level6DayProgress)
                Log.d("UpdateStatus", "Successfully updated level4DayProgressForAll")
            } catch (e: Exception) {
                Log.e("UpdateStatus", "Failed to update level4DayProgressForAll", e)
            }
        }
    }
    fun updateLevel7(level7DayProgress: Int) {
        viewModelScope.launch {
            try {
                statusDao.updateLevel7DayProgressForAll(level7DayProgress)
                Log.d("UpdateStatus", "Successfully updated level4DayProgressForAll")
            } catch (e: Exception) {
                Log.e("UpdateStatus", "Failed to update level4DayProgressForAll", e)
            }
        }
    }


}