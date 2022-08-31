package com.example.moveitpartner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moveitpartner.model.viewmodel.HomeViewModel

class WorkoutViewModelFactory(private val dao: WorkoutDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}