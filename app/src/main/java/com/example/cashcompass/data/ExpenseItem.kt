package com.example.cashcompass.data

data class ExpenseItem(
    var category: String,
    var amount: Double,
    var title: String,
    var createdAt: Long
)