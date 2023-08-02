package com.example.expressionevaluator.models

data class ExpressionRequest (
    val expr:List<String>,
    val precision:Int
)