package com.example.moveitpartner

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moveitpartner.model.data.WorkoutLog

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insert(workoutLog: WorkoutLog)

    @Update
    suspend fun update(workoutLog: WorkoutLog)

    @Delete
    suspend fun delete(workoutLog: WorkoutLog)

    @Query("SELECT * FROM workout_log_table WHERE logId = :logId")
    fun get(logId: Long): LiveData<WorkoutLog>

    @Query("SELECT * FROM workout_log_table ORDER BY logId DESC")
    fun getAll(): LiveData<List<WorkoutLog>>
}