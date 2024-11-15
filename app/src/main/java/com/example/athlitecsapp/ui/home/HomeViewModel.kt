package com.example.athlitecsapp.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athlitecsapp.dao.RoutineDao
import com.example.athlitecsapp.dao.UserDao
import com.example.athlitecsapp.database.RunBoostDatabase
import com.example.athlitecsapp.model.Routine
import com.example.athlitecsapp.table.User
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {

    private lateinit var userDao: UserDao
    private lateinit var routineDao: RoutineDao

    init {
        val database = RunBoostDatabase(context)
        userDao = database.getUserDao()
        routineDao = database.getRoutine()

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
    fun getTiniklingByCategory(category: String, callback: (List<Routine>) -> Unit) {
        viewModelScope.launch {
            try {
                val tiniklingList = routineDao.getTiniklingByCategory(category)
                callback(tiniklingList!!)
            } catch (e: Exception) {
                Log.e("ViewModelSignUp", "Error fetching data: ${e.message}")
            }
        }
    }


}