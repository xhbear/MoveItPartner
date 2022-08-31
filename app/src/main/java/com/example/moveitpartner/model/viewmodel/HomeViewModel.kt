package com.example.moveitpartner.model.viewmodel

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moveitpartner.WorkoutDao
import com.example.moveitpartner.model.data.WorkoutLog
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class HomeViewModel(val dao: WorkoutDao) : ViewModel() {

    /** set formatted welcome txt to home fragment */
    fun setWelcomeTxt(view: TextView) {
        val welcomeTxt = "To move is to\nLIVE"
        val formattedWelcomeText = SpannableString(welcomeTxt)
        formattedWelcomeText.setSpan(RelativeSizeSpan(1.8f), 14, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        formattedWelcomeText.setSpan(StyleSpan(Typeface.BOLD), 14, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        formattedWelcomeText.setSpan(ForegroundColorSpan(Color.parseColor("#000000")), 14, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.text = formattedWelcomeText
    }

    private val _clockInTime = MutableLiveData<LocalDateTime>()
    val clockInTime get() = _clockInTime

    private val _clockOutTime = MutableLiveData<LocalDateTime>()
    val clockOutTime get() = _clockOutTime

    private val _duration = MutableLiveData<Long>()
    private val duration get() = _duration

    private val _clockCounter = MutableLiveData<Int>(0)
    val clockCounter get() = _clockCounter

    private val _workoutLog = MutableLiveData<List<WorkoutLog>>()
    val workoutLog get() = _workoutLog

    private fun setTime(): LocalDateTime {
        return LocalDateTime.now()
    }

    fun setClockInTime() {
        _clockInTime.value = setTime()
        _clockCounter.value = 1
    }

    fun setClockOutTime() {
        _clockOutTime.value = setTime()
        viewModelScope.launch {
            calculateWorkoutDuration()
            val workoutLog = WorkoutLog(startTime = clockInTime.value.toString(), endTime = clockOutTime.value.toString(), duration = duration.value!!)
            dao.insert(workoutLog)
        }
    }

    private fun calculateWorkoutDuration() {
        val start = clockInTime.value!!
        val end = clockOutTime.value!!
        _duration.value = start.until(end, ChronoUnit.SECONDS)
    }

    fun resetTimeValues() {
        _clockInTime.value = null
        _clockOutTime.value = null
        _duration.value = null
        _clockCounter.value = 0
    }

    fun displaySessionRunning(view: TextView, counter: Int) {
        if (counter == 1) {
            view.visibility = VISIBLE
        } else {
            view.visibility = INVISIBLE
        }
    }
}