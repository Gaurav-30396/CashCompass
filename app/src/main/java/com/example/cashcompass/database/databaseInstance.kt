package com.example.cashcompass.database

import com.example.cashcompass.database.AppDatabase
import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "cash_compass_db"
            ).build()

            INSTANCE = instance
            instance
        }
    }
}