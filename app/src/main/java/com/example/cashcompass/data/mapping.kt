package com.example.cashcompass.data

import com.example.cashcompass.database.ExpenseEntity

fun ExpenseItem.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        category = category,
        amount = amount,
        title = title,
        createdAt = System.currentTimeMillis()

    )
}