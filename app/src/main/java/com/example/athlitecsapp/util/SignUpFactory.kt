package com.example.athlitecsapp.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.athlitecsapp.viewmodels.HomeViewModel
import com.example.athlitecsapp.viewmodels.SignUpModel

class SignUpFactory (private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpModel::class.java)) {
            return SignUpModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
