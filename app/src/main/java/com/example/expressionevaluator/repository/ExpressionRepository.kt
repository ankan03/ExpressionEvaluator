package com.example.expressionevaluator.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expressionevaluator.api.ExpressionService
import com.example.expressionevaluator.db.ExpressionDatabase
import com.example.expressionevaluator.models.Expression
import com.example.expressionevaluator.models.ExpressionRequest
import com.example.expressionevaluator.models.ExpressionResult
import com.example.expressionevaluator.utils.checkNetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ExpressionRepository(
    private val expressionService: ExpressionService,
    private val expressionDatabase: ExpressionDatabase,
    private val applicationContext: Context
) {
    private val expressionLiveData = MutableLiveData<ExpressionResult>()
    private val expressionHistoryLiveData = MutableLiveData<Expression>()

    val expressionResult: LiveData<ExpressionResult>
        get() = expressionLiveData

    suspend fun getExpressionResult(expr: List<String>, precision: String) {
        if (checkNetworkState(applicationContext)) {
            try {
                val chunkSize = 50
                val numChunks = (expr.size + chunkSize - 1) / chunkSize
                var result: Response<ExpressionResult>? = null

                for (i in 0 until numChunks) {
                    val startIndex = i * chunkSize
                    val endIndex = minOf((i + 1) * chunkSize, expr.size)
                    val chunk = expr.subList(startIndex, endIndex)

                    result = withContext(Dispatchers.IO) {
                        expressionService.getExpressionResult(ExpressionRequest(chunk, precision.toInt()))
                    }
                    Log.d("DEBUG_1", result.toString())

                    if (result.isSuccessful && result.body() != null) {
                        for (j in chunk.indices) {
                            val dataToInsertIntoDb = Expression(null, chunk[j], result.body()!!.result[j], System.currentTimeMillis())
                            expressionDatabase.expressionDao().insertExpression(dataToInsertIntoDb)
                            expressionHistoryLiveData.postValue(dataToInsertIntoDb)
                        }
                    } else {
                        GlobalScope.launch(Dispatchers.Main) {
                            Toast.makeText(applicationContext, result.message(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                if (result != null && result.isSuccessful && result.body() != null) {
                    expressionLiveData.postValue(result.body())
                }
            } catch (e: Exception) {
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Device is OFFLINE", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

