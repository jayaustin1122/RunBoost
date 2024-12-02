package com.example.athlitecsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _lastSelectedIndex = MutableLiveData<Int>()
    val lastSelectedIndex: LiveData<Int> get() = _lastSelectedIndex

    fun setLastSelectedIndex(index: Int) {
        _lastSelectedIndex.value = index
    }
}
