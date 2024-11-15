package com.example.athlitecsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.athlitecsapp.dao.RoutineDao
import com.example.athlitecsapp.dao.StatusDao
import com.example.athlitecsapp.dao.UserDao
import com.example.athlitecsapp.model.Routine
import com.example.athlitecsapp.table.Status
import com.example.athlitecsapp.table.User

@Database(
    entities = [User::class, Status::class,Routine::class],
    version = 2
)
abstract class RunBoostDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getRoutine(): RoutineDao

    abstract fun getStatus(): StatusDao

    companion object {
        @Volatile
        private var instance: RunBoostDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RunBoostDatabase::class.java,
            "runBoost.db"
        ).fallbackToDestructiveMigration()
            .build()


    }
}