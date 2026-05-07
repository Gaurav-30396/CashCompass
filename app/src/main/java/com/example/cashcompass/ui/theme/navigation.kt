package com.example.cashcompass.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Database
import com.example.cashcompass.data.BudgetRepository
import com.example.cashcompass.data.ExpenditureViewModel
import com.example.cashcompass.data.ExpenditureViewModelFactory
import com.example.cashcompass.data.ExpenseRepository
import com.example.cashcompass.database.DatabaseProvider

@Composable
fun myApp(){
    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)

    val expenseRepo = ExpenseRepository(db.expenseDao())
    val budgetRepo = BudgetRepository(db.budgetDao())
    val navController = rememberNavController()
    val factory = ExpenditureViewModelFactory(expenseRepo = expenseRepo, budgetRepo)
    val viewModel: ExpenditureViewModel = viewModel(factory = factory)
    val budget by viewModel.budget.collectAsState()
    val startDestination = if (budget == null) {
        "setup"
    } else {
        "main"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {


        //composable("setup") { BudgetSetupScreen(viewModel, navController) }
        composable(
            route = "setup?isEdit={isEdit}",
            arguments = listOf(navArgument("isEdit") {
                defaultValue = false
            })
        ) { backStackEntry ->

            val isEdit = backStackEntry.arguments?.getBoolean("isEdit") ?: false

            BudgetSetupScreen(
                isEdit = isEdit,
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("main") { MainScreen(viewModel, navController) }

        composable("detail/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")

            listScreen(


                category = category,
                viewModel,
                navController = navController
            )
        }
        composable("expenseList") {
            expenseScreen(viewModel, navController = navController)
        }
    }
    }