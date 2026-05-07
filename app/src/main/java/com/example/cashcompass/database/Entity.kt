package com.example.cashcompass.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
    val amount: Double,
    val title: String,
    val createdAt: Long = System.currentTimeMillis()

)

@Entity(tableName = "budget")
data class BudgetEntity(
    @PrimaryKey
    val month: String, // e.g. "2026-02"
    val limit: Double
)