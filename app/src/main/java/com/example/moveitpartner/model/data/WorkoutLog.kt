package com.example.moveitpartner.model.data

import java.time.LocalDateTime

data class WorkoutLog(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val duration: Long)
