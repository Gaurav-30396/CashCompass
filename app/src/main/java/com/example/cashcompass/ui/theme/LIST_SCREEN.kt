package com.example.cashcompass.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashcompass.R
//import androidx.lifecycle.ViewModel
import com.example.cashcompass.data.ExpenditureViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cashcompass.data.ExpenseItem

@Composable
fun listScreen(category: String?,  viewModel: ExpenditureViewModel, navController: NavController) {

    val ManropeFont = FontFamily(
        Font(R.font.manrope_bold, FontWeight.Bold)
    )
    val ManropeExtrabold = FontFamily(Font(R.font.manrope_extrabold, FontWeight.ExtraBold))
    val category = category ?: ""

    LaunchedEffect(category) {
        viewModel.setCategory(category)
    }
    val listbyCategory by viewModel.listbyCategory.collectAsState(initial = null)


    val total by viewModel.totalByCategory.collectAsState(initial = 0.0)
    val budget by viewModel.budget.collectAsState()
    val budgetAmount = budget?.limit ?: 0.0
    val percentage = (total?.div(budgetAmount) ?: 0.0) * 100


    val ManropeRegular = FontFamily(Font(R.font.manrope_regular, FontWeight.Normal))


    //timestamp


    Scaffold(
        containerColor = Color.Transparent,
        // bottomBar = { BottomBar(onClick = { isAlert = true }) }
    ) { innerpadding ->
        Column(
            Modifier
                .padding(innerpadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1B41),
                            Color(0xFF2A1B3D)
                        )
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "",
                        modifier = Modifier.clickable { navController.navigate("main") },
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$category",
                        fontFamily = ManropeRegular,
                        color = Color(0XFF60A5FA),
                        fontSize = 14.sp
                    )


                }
            }
            //main box 2
            Box(
                modifier = Modifier
                    .width(390.dp)
                    .height(840.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(342.dp)
                        .height(102.dp)
                        .padding(top = 32.dp, bottom = 48.dp, start = 24.dp, end = 24.dp)
                )

                {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "SPENDING BY CATEGORY",
                            fontFamily = ManropeFont,
                            fontSize = 10.sp,
                            color = Color(0Xff94A3B8)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "₹",
                                fontFamily = ManropeExtrabold,
                                fontSize = 48.sp,
                                color = Color(0XFF2094F3),
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color(0X662094F3),
                                        offset = Offset(0f, 0f),
                                        blurRadius = 20f
                                    )
                                )
                            )
                            Text(
                                text = "${total}",
                                fontFamily = ManropeExtrabold,
                                fontSize = 48.sp,
                                color = Color.White,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color(0X662094F3),
                                        offset = Offset(0f, 0f),
                                        blurRadius = 20f
                                    )
                                )
                            )


                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${percentage} used",
                            fontFamily = ManropeFont,
                            fontSize = 10.sp,
                            color = Color(0XFF2094F3)
                        )


                    }


                }
                Spacer(modifier = Modifier.height(48.dp))
                Card(
                    modifier = Modifier
                        .height(530.dp)
                        .width(390.dp),
                    shape = RoundedCornerShape(48.dp),
                    colors = CardDefaults.cardColors(Color(0XFF121212))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 24.dp, end = 24.dp, top = 40.dp)
                    ) {
                        Text(
                            text = "Transactions",
                            fontFamily = ManropeFont,
                            color = Color(0XFF94A3B8),
                            fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.padding(24.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (listbyCategory.isNullOrEmpty()) {
                                Text(text = "No Expense have been added yet", fontFamily = ManropeFont, fontSize = 16.sp, color = Color.White)

                            } else {
                                LazyColumn {
                                    items(listbyCategory ?: emptyList()) { expense ->
                                        Card(
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color(0xff1E1E1E)
                                            )
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(16.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = "${expense.title}",
                                                        fontSize = 14.sp,
                                                        color = Color(0XFFF1F5F9),
                                                        fontFamily = ManropeFont
                                                    )
                                                    Text(
                                                        text = "${expense.amount}",
                                                        fontSize = 14.sp,
                                                        color = Color(0XFFF44336),
                                                        fontFamily = ManropeFont
                                                    )


                                                }

                                                Text(
                                                    text = "${viewModel.formatTimestamp(expense.createdAt)}",
                                                    fontSize = 10.sp,
                                                    color = Color(0XFF64748B),
                                                    fontFamily = ManropeFont
                                                )


                                            }

                                        }
                                        Spacer(modifier = Modifier.height(12.dp))


                                    }
                                }
                            }

                        }


                    }


                }


            }


        }
    }
}
