package com.example.expressionevaluator.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expressionevaluator.repository.ExpressionRepository

class MainViewModelFactory(private val repository: ExpressionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}