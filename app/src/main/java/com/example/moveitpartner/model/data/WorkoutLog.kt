package com.example.moveitpartner.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_log_table")
data class WorkoutLog(
    @PrimaryKey(autoGenerate = true)
    var logId: Long = 0L,
    @ColumnInfo(name = "start_time")
    val startTime: String = "",
    @ColumnInfo(name = "end_time")
    val endTime: String = "",
    @ColumnInfo(name = "workout_duration")
    val duration: Long = 0L)
