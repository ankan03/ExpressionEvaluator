package com.example.expressionevaluator

import android.app.Application
import com.example.expressionevaluator.api.ExpressionService
import com.example.expressionevaluator.api.RetrofitHelper
import com.example.expressionevaluator.db.ExpressionDatabase
import com.example.expressionevaluator.repository.ExpressionRepository

class ExpressionApplication :Application(){
    lateinit var expressionRepository: ExpressionRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val expressionService = RetrofitHelper.getRetrofitInstance().create(ExpressionService::class.java)
        val database = ExpressionDatabase.getDatabase(applicationContext)
        expressionRepository = ExpressionRepository(expressionService,database,applicationContext)
    }
}