package com.example.expressionevaluator.api

import com.example.expressionevaluator.models.ExpressionRequest
import com.example.expressionevaluator.models.ExpressionResult
import retrofit2.Response
import retrofit2.http.*

interface ExpressionService {
    @POST("/v4")
    suspend fun getExpressionResult(@Body request: ExpressionRequest):Response<ExpressionResult>
}