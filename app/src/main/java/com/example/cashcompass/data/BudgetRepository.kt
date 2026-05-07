package com.example.cashcompass.data

import com.example.cashcompass.database.BudgetDao
import com.example.cashcompass.database.BudgetEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BudgetRepository(
    private val budgetDao: BudgetDao
) {

    private fun getCurrentMonthKey(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun getCurrentMonthBudget(): Flow<BudgetEntity?> {
        val monthKey = getCurrentMonthKey()
        return budgetDao.getBudgetForMonth(monthKey)
    }

    suspend fun insert(limit: Double) {
        val monthKey = getCurrentMonthKey()
        val budget = BudgetEntity(
            month = monthKey,
            limit = limit
        )
        budgetDao.insertOrUpdateBudget(budget)
    }

    suspend fun delete(budget: BudgetEntity) {
        budgetDao.deleteBudget(budget)
    }

}