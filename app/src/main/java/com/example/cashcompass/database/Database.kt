package com.example.cashcompass.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cashcompass.database.BudgetDao
import com.example.cashcompass.database.BudgetEntity
import com.example.cashcompass.database.ExpenseDao
import com.example.cashcompass.database.ExpenseEntity

@Database(
    entities = [ExpenseEntity::class, BudgetEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    abstract fun budgetDao(): BudgetDao

}