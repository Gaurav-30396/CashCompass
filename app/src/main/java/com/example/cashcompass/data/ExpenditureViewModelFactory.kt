package com.example.cashcompass.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ExpenditureViewModelFactory(
    private val expenseRepo: ExpenseRepository,
    private val budgetRepo: BudgetRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExpenditureViewModel(expenseRepo, budgetRepo) as T
    }
}