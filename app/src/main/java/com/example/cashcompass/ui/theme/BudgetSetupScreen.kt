package com.example.cashcompass.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cashcompass.R
import com.example.cashcompass.data.ExpenditureViewModel

@Composable
fun BudgetSetupScreen(
    isEdit: Boolean,
    viewModel: ExpenditureViewModel,
    navController: NavController
) {

    val budget by viewModel.budget.collectAsState(initial = null)
    val ManropeBold = FontFamily(Font(R.font.manrope_bold))
    val ManropeExtraBold = FontFamily(Font(R.font.manrope_extrabold))
    val ManropeRegular = FontFamily(Font(R.font.manrope_regular))

    var amountText by remember {
        mutableStateOf("")
    }

    // ✅ Prefill if editing
    LaunchedEffect(budget) {
        if (isEdit && budget != null) {
            amountText = budget!!.limit.toString()
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                listOf(
                    Color(0xFF1A1B41),
                    Color(0xFF2A1B3D)
                )
            )
        )
        )

    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp),
            //verticalArrangement = Arrangement.Center,
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = if (isEdit) "Edit Budget" else "Set Your Budget",
                fontSize = 20.sp,
                color = Color(0xff94A3B8)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },

                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = ManropeBold,
                    color = Color.White
                ),

                colors = TextFieldDefaults.colors(

                    // TEXT
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,

                    // BACKGROUND
                    focusedContainerColor = Color(0xff1E1E1E),
                    unfocusedContainerColor = Color(0xff1E1E1E),


                ),

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally)
            {
                Button(
                    onClick = {
                        val amount = amountText.toDoubleOrNull()

                        if (amount != null && amount > 0) {

                            viewModel.setBudget(amount)

                            if (isEdit) {
                                // 🔥 go back
                                navController.popBackStack()
                            } else {
                                // 🔥 first time → go to main
                                navController.navigate("main") {
                                    popUpTo("setup") { inclusive = true }
                                }
                            }
                        }
                    }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2094F3))
                ) {
                    Text("Save")
                }
            }
        }
    }
}