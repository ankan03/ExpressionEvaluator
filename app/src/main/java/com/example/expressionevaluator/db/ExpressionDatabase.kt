package com.example.expressionevaluator.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expressionevaluator.models.Expression

@Database(entities = [Expression::class], version = 1)
abstract class ExpressionDatabase : RoomDatabase() {
    abstract fun expressionDao() : ExpressionDao
    companion object{
        @Volatile
        private var INSTANCE: ExpressionDatabase? = null

        fun getDatabase(context: Context): ExpressionDatabase {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        ExpressionDatabase::class.java,
                        "expressionDB")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}