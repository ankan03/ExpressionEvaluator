package com.example.expressionevaluator.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.expressionevaluator.db.ExpressionDao
import com.example.expressionevaluator.models.Expression

class HistoryViewModel(private val expressionDao: ExpressionDao):ViewModel() {
    val expressionsLiveData: LiveData<List<Expression>> = liveData {
        try {
            val expressions = expressionDao.getExpressions()
            emit(expressions)
        } catch (e: Exception) {
            emit(emptyList())
            Log.e("HistoryViewModel", "Error getting expressions: ${e.message}")
        }
    }
}