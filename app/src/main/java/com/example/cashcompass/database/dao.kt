package com.example.cashcompass.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.cashcompass.data.ExpenseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
    @Query("DELETE FROM expenses WHERE createdAt < :startOfMonth")
    suspend fun deleteOldExpenses(startOfMonth: Long)

    @Query(
        """
    SELECT * FROM expenses
    WHERE createdAt BETWEEN :start AND :end
    ORDER BY createdAt DESC
"""
    )
    //get complete list
    fun getExpensesBetween(start: Long, end: Long): Flow<List<ExpenseEntity>>

    @Query("""
    SELECT SUM(amount) FROM expenses
    WHERE createdAt BETWEEN :start AND :end
""")
    fun getMonthlyTotal(start: Long, end: Long): Flow<Double?>

    @Query(
        """
SELECT SUM(amount)
FROM expenses
WHERE category = :category
AND createdAt BETWEEN :start AND :end
"""
    )
    // category total
    fun getCategoryTotal(
        category: String,
        start: Long,
        end: Long
    ): Flow<Double?>

    @Query("""
SELECT title, amount, category, createdAt
FROM expenses
WHERE category = :category
AND createdAt BETWEEN :start AND :end
ORDER BY createdAt DESC
""")
    //list according to category
    fun getItem_by_Category(
        category: String,
        start: Long,
        end: Long
    ): Flow<List<ExpenseItem>>
}
@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateBudget(budget: BudgetEntity)

    @Query("""
        SELECT * FROM budget
        WHERE month = :month
        LIMIT 1
    """)
    fun getBudgetForMonth(month: String): Flow<BudgetEntity?>
    @Delete
    suspend fun deleteBudget(budget: BudgetEntity)
}
