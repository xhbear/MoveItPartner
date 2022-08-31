package com.example.moveitpartner

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moveitpartner.model.data.WorkoutLog

@Database(entities = [WorkoutLog::class], version = 1, exportSchema = false)
abstract class WorkoutLogDatabase : RoomDatabase(){
    abstract val workoutDao: WorkoutDao

    companion object {
        @Volatile
        private var INSTANCE: WorkoutLogDatabase? = null

        fun getInstance(context: Context): WorkoutLogDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WorkoutLogDatabase::class.java,
                        "workout_log_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}