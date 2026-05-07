package com.example.cashcompass.data

import android.icu.util.Calendar
import androidx.room.Delete
import com.example.cashcompass.database.ExpenseDao
import com.example.cashcompass.database.ExpenseEntity
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao,

    ){
    private fun getCurrentMonthRange(): Pair<Long, Long> {

        val calendar = Calendar.getInstance()

        // Start of month
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        //get month and year


        val start = calendar.timeInMillis

        // Move to next month
        calendar.add(Calendar.MONTH, 1)

        val end = calendar.timeInMillis - 1

        return Pair(start, end)
    }
    fun getCurrentMonthExpensesList(): Flow<List<ExpenseEntity>> {
        val (start, end) = getCurrentMonthRange()
        return expenseDao.getExpensesBetween(start, end)
    }

   // val allExpense: Flow<List<ExpenseEntity>> = expenseDao.getExpensesByMonth("","")
    suspend fun insert(expenseEntity: ExpenseEntity){
        expenseDao.insertExpense(expenseEntity)
    }
    suspend fun deleteExpense(expenseEntity: ExpenseEntity){
        expenseDao.deleteExpense(expense = expenseEntity)
    }

     fun getTotalByCategory(category:String):Flow<Double?>{
        val(start, end) = getCurrentMonthRange()
         return expenseDao.getCategoryTotal(category, start, end)

    }
    fun getlistByCategory(category: String): Flow<List<ExpenseItem>>{
        val(start, end) = getCurrentMonthRange()
        return expenseDao.getItem_by_Category(category, start, end)

    }
    fun getMonthlyTotal():Flow<Double?>{
        val (start, end) = getCurrentMonthRange()
        return expenseDao.getMonthlyTotal(start, end)

    }
    suspend fun deleteOldExpenses(startOfMonth: Long) {
        expenseDao.deleteOldExpenses(startOfMonth)
    }


}