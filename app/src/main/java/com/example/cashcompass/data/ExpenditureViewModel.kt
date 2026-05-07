package com.example.cashcompass.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashcompass.database.BudgetEntity
import com.example.cashcompass.database.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ExpenditureViewModel(
    private val expenseRepo: ExpenseRepository, private val budgetRepo:BudgetRepository
): ViewModel() {

    private val selectedCategory = MutableStateFlow<String?>(null)
     val listbyCategory = selectedCategory
         .filterNotNull()
         .flatMapLatest { category->
             expenseRepo.getlistByCategory(category)

         }


    val totalByCategory = selectedCategory
        .filterNotNull()
        .flatMapLatest { category ->
            expenseRepo.getTotalByCategory(category)
        }
    //list of item
    private val _itemList = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val itemList:StateFlow<List<ExpenseEntity>> = _itemList

    // ye ho gya budget
    private val _budget = MutableStateFlow<BudgetEntity?>(null)
    val budget: StateFlow<BudgetEntity?> = _budget


    // list according to category

   // private val _listByCategory = MutableStateFlow<List<ExpenseItem>>(emptyList())
    //val listByCategory: StateFlow<List<ExpenseItem>> = _listByCategory

    //total according to the category



    // ye hai tmhara total spent currently
    private val _totalSpent = MutableStateFlow<Double?>(0.0)
    val totalSpent : StateFlow<Double?> = _totalSpent

    init{
        observeBudget()
        showList()
        getMonthlyTotalSpent()

    }


    // ab ayenge saare methods

    // budget
    // yhaa hua budget ka dekhnq
    fun observeBudget(){
        viewModelScope.launch {
            budgetRepo.getCurrentMonthBudget().collect {
                _budget.value = it
            }
        }
    }
    // budget ka set krnaaaa

    fun setBudget(limit:Double){
        viewModelScope.launch{
            budgetRepo.insert(limit)
        }

    }

    //expense

    //updating the list

    fun showList(){
        viewModelScope.launch{
            expenseRepo.getCurrentMonthExpensesList().collect{
                _itemList.value = it
            }
        }

    }
    // updatelist
    fun insertItem(item:ExpenseItem){
        viewModelScope.launch {
            val entity = item.toEntity()
            expenseRepo.insert(entity)
        }
    }
    //delete list
    fun deleteItems(item: ExpenseEntity){
        viewModelScope.launch {
            expenseRepo.deleteExpense(item)
        }
    }
    // total spent
    fun getMonthlyTotalSpent(){
        viewModelScope.launch{
            expenseRepo.getMonthlyTotal().collectLatest{
                _totalSpent.value = it?:0.0
            }
        }
    }

    //spent by category
    fun getTotalByCategory(category:String): Flow<Double?> {
        val spent = expenseRepo.getTotalByCategory(category)
        return spent

    }


    //list by category

    //fun getListByCategory(category: String){
       // viewModelScope.launch {
          // expenseRepo.getlistByCategory(category).collect{
           //    _listByCategory.value = it
          // }
      //  }
   // }

    // kitna budget bacha hai
    fun remainingBudget():Double{
        val budgetAmount = _budget.value?.limit ?: 0.0
        val spent = _totalSpent.value ?: 0.0
        return budgetAmount - spent

    }

    // calculating percentage

    fun usedPercent(): Double{
        val budgetAmount = _budget.value?.limit ?: 0.0
        val spent = _totalSpent.value ?: 0.0
        if(budgetAmount==0.0)return 0.0
        else {
            return (spent / budgetAmount) * 100
        }
    }
    fun setCategory(category: String) {
        selectedCategory.value = category
    }
    fun clearOldData() {
        viewModelScope.launch {
            val start = getStartOfMonth()   // from utils
            expenseRepo.deleteOldExpenses(start)
        }
    }

    fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }



}