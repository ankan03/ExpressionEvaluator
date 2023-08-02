package com.example.expressionevaluator.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expressionevaluator.models.Expression
import com.example.expressionevaluator.models.ExpressionRequest
import com.example.expressionevaluator.models.ExpressionResult

@Dao
interface ExpressionDao {

    @Insert
    suspend fun insertExpression(expression: Expression)

    @Query("SELECT * from expression ORDER BY timestamp DESC")
    suspend fun getExpressions():List<Expression>
}