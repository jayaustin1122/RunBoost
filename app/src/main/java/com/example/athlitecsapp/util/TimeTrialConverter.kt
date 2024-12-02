package com.example.athlitecsapp.util

import androidx.room.TypeConverter
import com.example.athlitecsapp.table.TimeTrial
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TimeTrialConverter {

    @TypeConverter
    fun fromTimeTrialList(timeTrials: List<TimeTrial>): String {
        return Gson().toJson(timeTrials)
    }

    @TypeConverter
    fun toTimeTrialList(timeTrialsString: String): List<TimeTrial> {
        val listType = object : TypeToken<List<TimeTrial>>() {}.type
        return Gson().fromJson(timeTrialsString, listType)
    }
}