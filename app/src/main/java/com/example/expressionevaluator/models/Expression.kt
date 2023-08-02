package com.example.expressionevaluator.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expression")
data class Expression (
    @PrimaryKey(autoGenerate = true)
    val expressionId:Int?,
    val inputExpression:String,
    val outputResult:String,
    val timestamp: Long
)