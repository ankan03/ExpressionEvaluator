package com.example.expressionevaluator.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expressionevaluator.db.ExpressionDao

class HistoryViewModelFactory(private val expressionDao: ExpressionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryViewModel(expressionDao) as T
    }
}