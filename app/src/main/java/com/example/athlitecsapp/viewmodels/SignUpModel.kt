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
    fun updateLevel1(level1DayProgress: Int) {
        viewModelScope.launch {
            try {
                statusDao.updateLevel1DayProgressForAll(level1DayProgress)
                Log.d("UpdateStatus", "Successfully updated level4DayProgressForAll")
            } catch (e: Exception) {
                Log.e("UpdateStatus", "Failed to update level4DayProgressForAll", e)
            }
        }
    }

    fun updateLevel2(level2DayProgress: Int) {
        viewModelScope.launch {
            try {
                statusDao.updateLevel2DayProgressForAll(level2DayProgress)
                Log.d("UpdateStatus", "Successfully updated level4DayProgressForAll")
            } catch (e: Exception) {
                Log.e("UpdateStatus", "Failed to update level4DayProgressForAll", e)
            }
        }
    }

    fun updateLevel3(level3DayProgress: Int) {
        viewModelScope.launch {
            try {
                statusDao.updateLevel3DayProgressForAll(level3DayProgress)
                Log.d("UpdateStatus", "Successfully updated level4DayProgressForAll")
            } catch (e: Exception) {
                Log.e("UpdateStatus", "Failed to update level4DayProgressForAll", e)
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