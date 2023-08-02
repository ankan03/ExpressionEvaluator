package com.example.expressionevaluator.viewmodels

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expressionevaluator.models.ExpressionResult
import com.example.expressionevaluator.repository.ExpressionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: ExpressionRepository) : ViewModel() {
    var expression: String? = null
    private val _progressBarVisible = MutableLiveData<Boolean>().apply { value = false }
    val progressBarVisible: LiveData<Boolean>
        get() = _progressBarVisible

    fun showProgressBar(show: Boolean) {
        _progressBarVisible.value = show
    }

    fun onCalculateButtonClick(view: View) {
        Log.d("DEBUG_3", "Expression: '$expression'")
        if (expression != null && expression != "") {
            val lines = expression?.split("\n")
            val listExpression: List<String> = lines!!.toList()
            showProgressBar(true)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getExpressionResult(listExpression, "14")
                    withContext(Dispatchers.Main) {
                        showProgressBar(false)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        showProgressBar(false)
                        Log.e("DEBUG_ERROR", "Error: ${e.message}")
                        Toast.makeText(view.context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Log.d("DEBUG_2", "Expression is empty")
            Toast.makeText(view.context, "Expression is empty", Toast.LENGTH_SHORT).show()
        }
    }

    val expressions: LiveData<ExpressionResult>
        get() = repository.expressionResult
}