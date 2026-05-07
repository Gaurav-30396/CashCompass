package com.example.cashcompass.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cashcompass.R
import com.example.cashcompass.data.ExpenditureViewModel
import com.example.cashcompass.data.ExpenseItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: ExpenditureViewModel,
    navController: NavController
) {

    val budget by viewModel.budget.collectAsState()
    val budgetAmount = budget?.limit ?: 0.0
    val totalSpent by viewModel.totalSpent.collectAsState()

    val usedPercent = viewModel.usedPercent()
    val remaining = viewModel.remainingBudget()
    val progress = (usedPercent / 100f)

    // var show by remember { mutableStateOf(false) }

    val ManropeBold = FontFamily(Font(R.font.manrope_bold))
    val ManropeExtraBold = FontFamily(Font(R.font.manrope_extrabold))
    val ManropeRegular = FontFamily(Font(R.font.manrope_regular))

    val categories = listOf("Food", "Shopping", "Travel", "Other")

    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var amount = amountText.toDoubleOrNull() ?: 0.0
    var tag by remember { mutableStateOf("") }
    var show by remember { mutableStateOf(false) }
    var expenseitem by remember {
        mutableStateOf(ExpenseItem( category = "",
            amount = 0.0,
            title = "",
            createdAt = System.currentTimeMillis()))
    }
    val foodTotal by viewModel.getTotalByCategory("Food")
        .collectAsState(initial = 0.0)
    val travelTotal by viewModel.getTotalByCategory("Travel")
        .collectAsState(initial = 0.0)
    val shoppingTotal by viewModel.getTotalByCategory("Shopping")
        .collectAsState(initial = 0.0)
    val otherTotal by viewModel.getTotalByCategory("Other")
        .collectAsState(initial = 0.0)

    val datausing = listOf(
        Triple("Food", foodTotal, Color.Red),
        Triple("Shopping", shoppingTotal, Color.Blue),
        Triple("Travel", travelTotal, Color.Green),
        Triple("Other", otherTotal, Color.Yellow)
    )
    //val total = data.sumOf { it.second.toDouble() }.toFloat()

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = { BottomBar{show = true} }
    ) { padding ->

        // ✅ FULL SCREEN BACKGROUND
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF1A1B41),
                            Color(0xFF2A1B3D)
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Cash Compass",
                    fontSize = 20.sp,
                    fontFamily = ManropeBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ✅ BUDGET CARD
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E1E1E)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Budget Remaining",
                                color = Color.White,
                                fontFamily = ManropeBold
                            )

                            Text(
                                "$usedPercent% used",
                                color = Color(0xFF94A3B8),
                                fontFamily = ManropeBold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "₹$remaining",
                            color = Color(0xFF4CAF50),
                            fontSize = 32.sp,
                            fontFamily = ManropeExtraBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        GradientProgressBar(progress.toFloat())

                        Spacer(modifier = Modifier.height(8.dp))
Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth())
{
    Text(
        text = "LIMIT: ₹$budgetAmount",
        color = Color(0xFF64748B),
        fontSize = 12.sp
    )
    Icon(
        painter = painterResource(id = R.drawable.baseline_mode_edit_24),
        contentDescription = "Edit",
        tint = Color.White,
        modifier = Modifier
            .size(20.dp)
            .clickable {
                // handle click
                navController.navigate("setup?isEdit=true")
            }
    )
}
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ✅ CATEGORY CARD
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color(0x66121212)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .height(350.dp)
                    ) {

                        Text(
                            "SPENDING BY CATEGORY",
                            color = Color(0xFF94A3B8),
                            fontFamily = ManropeBold
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // ✅ CLEAN DONUT CHART
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Canvas(modifier = Modifier.size(150.dp)) {

                                val data = datausing
                                val total = datausing.sumOf { (it.second ?: 0.0) }.toFloat()

                                var startAngle = -90f

                                data.forEach { (_, value, color) ->
                                    val safeValue = (value ?: 0.0).toFloat()

                                    val sweepAngle = (safeValue / total).toFloat() * 360f

                                    drawArc(
                                        color = color,
                                        startAngle = startAngle,
                                        sweepAngle = sweepAngle,
                                        useCenter = false,
                                        style = Stroke(width = 20f, cap = StrokeCap.Round)
                                    )

                                    startAngle += sweepAngle
                                }
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Total Spent", color = Color.Gray)
                                Text("₹$totalSpent", color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // ✅ CATEGORY BUTTONS
                        Row(
                            modifier = Modifier
                                .padding(start = 4.dp, end = 4.dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CategoryRow(
                                category = "Food",
                                navController = navController,
                                colour = 0XFFFF0000
                            )
                            CategoryRow(
                                category = "Shopping",
                                navController = navController,
                                colour = 0XFF002362
                            )

                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, end = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CategoryRow(
                                category = "Travel",
                                navController = navController,
                                colour = 0xff32CD32
                            )
                            CategoryRow(
                                category = "Other",
                                navController = navController,
                                colour = 0XFFFFFF00
                            )

                        }


                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ✅ VIEW ALL CARD
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { navController.navigate("expenseList") },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color(0xFF1E1E1E))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "VIEW ALL TRANSACTIONS",
                                color = Color.White
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "VIEW ALL TRANSACTIONS",
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

    if (show) {
        AlertDialog(onDismissRequest = { show = false },
            containerColor = Color(0xff1E1E1E),
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {

                            show = false


                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x0DFFFFFF),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            if (name.isNotBlank() && amountText.isNotBlank()) {
                              expenseitem.title = name
                                expenseitem.category = selectedCategory
                                expenseitem.amount = amountText.toDoubleOrNull()?:0.0
                                expenseitem.createdAt = System.currentTimeMillis()
                                viewModel.insertItem(item = expenseitem)
                                show = false

                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff2094F3),
                            contentColor = Color.White
                        )

                    ) {
                        Text(text = "Add")

                    }

                }


            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add New Expense",
                        fontSize = 24.sp,
                        color = Color(0xffF1F5F9)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = "Close",
                        tint = Color.White, // 👈 THIS was missing
                        modifier = Modifier
                            .clickable { show = false }
                            .size(24.dp)
                    )
                }
            },
            text = {
                Column {
                    //  Text(text = "Expense")
                    OutlinedTextField(
                        value = name, onValueChange = { name = it },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.Transparent,   // 👈 THIS FIXES IT
                            unfocusedContainerColor = Color.Transparent  // 👈 THIS TOO
                        ),
                        label = { Text(text = "Expense", fontWeight = FontWeight.SemiBold) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    OutlinedTextField(
                        value = amountText, onValueChange = { amountText = it },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),

                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,

                            focusedContainerColor = Color.Transparent,   // 👈 THIS FIXES IT
                            unfocusedContainerColor = Color.Transparent  // 👈 THIS TOO
                        ),
                        label = { Text(text = "Amount", fontWeight = FontWeight.SemiBold) },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            shape = RoundedCornerShape(16.dp),

                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Category", fontWeight = FontWeight.SemiBold) },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,

                                focusedContainerColor = Color.Transparent,   // 👈 THIS FIXES IT
                                unfocusedContainerColor = Color.Transparent  // 👈 THIS TOO
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .padding(8.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(Color(0xFF1E1E1E)) // dark b


                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category, color = Color.White) },
                                    onClick = {
                                        selectedCategory = category
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }


                }
            }
        )

    }
}



    @Composable
    fun CategoryRow(category: String, navController: NavController, colour: Long) {
        Card(
            modifier = Modifier
                .size(width = 150.dp, height = 60.dp)
                .padding(vertical = 6.dp)
                .clickable { navController.navigate("detail/$category") },
            colors = CardDefaults.cardColors(Color(0xFF1E1E1E)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_square_24),
                        contentDescription = "",
                        tint = Color(colour)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${category}", color = Color.White,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24), contentDescription = "")


                }
            }


        }

    }

    @Composable
    fun GradientProgressBar(progress: Float) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF3A3A3A)) // track
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .clip(RoundedCornerShape(50))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF4CAF50), // green
                                Color(0xFFFF9800), // orange
                                Color(0xFFF44336)  // red
                            )
                        )
                    )
            )
        }
    }

    @Composable
    fun BottomBar(onClick: () -> Unit) {

        Surface(
            shadowElevation = 8.dp,
            color = Color(0xFF1E1E1E)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier.wrapContentSize(),
                        colors = CardDefaults.cardColors(Color(0XFF2094F3))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_add_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(48.dp)
                                .clickable {
                                    onClick()


                                }


                        )


                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Add Expense",
                        color = Color.White
                    )

                }
            }
        }
    }



