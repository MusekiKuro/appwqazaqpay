package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class HistoryTransaction(
    val title: String,
    val subtitle: String,
    val date: String,
    val amount: String,
    val isPositive: Boolean,
    val icon: ImageVector,
    val iconColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavHostController) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf("Все", "Доходы", "Расходы")

    val allTransactions = listOf(
        HistoryTransaction(
            "Зарплата",
            "Поступление",
            "29 ноября",
            "+250 000 ₸",
            true,
            Icons.Filled.AccountBalance,
            Color(0xFF34C759)
        ),
        HistoryTransaction(
            "Magnum",
            "Покупка продуктов",
            "29 ноября",
            "-15 230 ₸",
            false,
            Icons.Filled.ShoppingBag,
            Color(0xFFFF6B6B)
        ),
        HistoryTransaction(
            "Маме",
            "Перевод",
            "28 ноября",
            "-50 000 ₸",
            false,
            Icons.Filled.Person,
            Color(0xFF4ECDC4)
        ),
        HistoryTransaction(
            "Beeline",
            "Оплата мобильной связи",
            "28 ноября",
            "-2 500 ₸",
            false,
            Icons.Filled.Phone,
            Color(0xFFFFBE0B)
        ),
        HistoryTransaction(
            "АЗС Helios",
            "Заправка",
            "27 ноября",
            "-8 000 ₸",
            false,
            Icons.Filled.LocalGasStation,
            Color(0xFF9B59B6)
        ),
        HistoryTransaction(
            "Депозит",
            "Проценты",
            "27 ноября",
            "+1 250 ₸",
            true,
            Icons.Filled.TrendingUp,
            Color(0xFF34C759)
        )
    )

    val filteredTransactions = when (selectedTab) {
        1 -> allTransactions.filter { it.isPositive }
        2 -> allTransactions.filter { !it.isPositive }
        else -> allTransactions
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Финансы", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад", tint = Color(0xFF1A1A1A))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1A1A1A)
                )
            )
        },
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "history") },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Табы
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color(0xFF4A90E2)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                title,
                                fontSize = 15.sp,
                                fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (selectedTab == index) Color(0xFF4A90E2) else Color(0xFF8E8E93)
                            )
                        },
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }

            // Статистика
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Доходы",
                    amount = "+251 250 ₸",
                    color = Color(0xFF34C759),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Расходы",
                    amount = "-75 730 ₸",
                    color = Color(0xFFFF6B6B),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color(0xFFF8F9FA))
            )

            // Список транзакций
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                items(filteredTransactions) { transaction ->
                    HistoryTransactionRow(
                        transaction = transaction,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                    )
                    Divider(
                        color = Color(0xFFF8F9FA),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }

                item {
                    Spacer(Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    amount: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            title,
            fontSize = 13.sp,
            color = Color(0xFF8E8E93)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            amount,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun HistoryTransactionRow(
    transaction: HistoryTransaction,
    modifier: Modifier = Modifier
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(transaction.iconColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                transaction.icon,
                contentDescription = null,
                tint = transaction.iconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.width(16.dp))

        Column(Modifier.weight(1f)) {
            Text(
                transaction.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A)
            )
            Spacer(Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    transaction.subtitle,
                    fontSize = 14.sp,
                    color = Color(0xFF8E8E93)
                )
                Text(
                    "•",
                    fontSize = 14.sp,
                    color = Color(0xFF8E8E93)
                )
                Text(
                    transaction.date,
                    fontSize = 14.sp,
                    color = Color(0xFF8E8E93)
                )
            }
        }

        Text(
            transaction.amount,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (transaction.isPositive) Color(0xFF34C759) else Color(0xFF1A1A1A)
        )
    }
}
