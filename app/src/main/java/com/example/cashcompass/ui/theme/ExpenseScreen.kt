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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.cashcompass.data.ExpenditureViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cashcompass.R


@Composable
fun expenseScreen(
    viewModel: ExpenditureViewModel,
    navController: NavController
) {
    val usedPercent = viewModel.usedPercent()
    val budget by viewModel.budget.collectAsState()
    val itemList by viewModel.itemList.collectAsState()
    val totalSpent by viewModel.totalSpent.collectAsState()

    val ManropeFont = FontFamily(Font(R.font.manrope_bold, FontWeight.Bold))
    val ManropeRegular = FontFamily(Font(R.font.manrope_regular, FontWeight.Normal))
    val ManropeExtrabold = FontFamily(Font(R.font.manrope_extrabold, FontWeight.ExtraBold))

    Scaffold(
        containerColor = Color.Transparent
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1B41),
                            Color(0xFF2A1B3D)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            // 🔹 Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        navController.navigate("main")
                    }, tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))

            }

            // 🔹 Spending Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


               // Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "₹",
                        fontFamily = ManropeExtrabold,
                        fontSize = 48.sp,
                        color = Color(0XFF2094F3),
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color(0X662094F3),
                                blurRadius = 20f
                            )
                        )
                    )
                    Text(
                        text = "$totalSpent",
                        fontFamily = ManropeExtrabold,
                        fontSize = 48.sp,
                        color = Color.White,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color(0X662094F3),
                                blurRadius = 20f
                            )
                        )
                    )
                }

                //Spacer(modifier = Modifier.height(1.dp))

                Text(
                    text = "$usedPercent% used",
                    fontFamily = ManropeFont,
                    fontSize = 18.sp,
                    color = Color(0XFF2094F3)
                )
            }

            // 🔥 Transactions Card (takes remaining space)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(48.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0XFF121212))
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {

                    Text(
                        text = "Transactions",
                        fontFamily = ManropeFont,
                        color = Color(0XFF94A3B8),
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(itemList ?: emptyList()) { item ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xff1e1e1e)
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = item.title,
                                            fontSize = 14.sp,
                                            color = Color(0XFFF1F5F9),
                                            fontFamily = ManropeFont
                                        )

                                        Text(
                                            text = "${item.amount}",
                                            fontSize = 14.sp,
                                            color = Color(0XFFF44336),
                                            fontFamily = ManropeFont
                                        )
                                    }

                                    Text(
                                        text = viewModel.formatTimestamp(item.createdAt),
                                        fontSize = 10.sp,
                                        color = Color(0XFF64748B),
                                        fontFamily = ManropeFont
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

