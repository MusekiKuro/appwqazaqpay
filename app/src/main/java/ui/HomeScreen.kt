package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.qazaqpaybank.viewmodel.CardsViewModel

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Дом", Icons.Outlined.Home)
    object Bills : BottomNavItem("bills", "Платежи", Icons.Outlined.Receipt)
    object History : BottomNavItem("history", "Финансы", Icons.Outlined.ShowChart)
    object Services : BottomNavItem("services", "Сервисы", Icons.Outlined.Apps)
    object Profile : BottomNavItem("profile", "Профиль", Icons.Outlined.Person)

}

data class Transaction(
    val title: String,
    val subtitle: String,
    val amount: String,
    val isPositive: Boolean,
    val icon: ImageVector,
    val iconColor: Color
)

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: CardsViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.init(context)
        viewModel.loadCards()
    }

    val transactions = listOf(
        Transaction(
            "Magnum",
            "Покупка продуктов",
            "-15 230 ₸",
            false,
            Icons.Filled.ShoppingBag,
            Color(0xFFFF6B6B)
        ),
        Transaction(
            "Маме",
            "Перевод",
            "-50 000 ₸",
            false,
            Icons.Filled.Person,
            Color(0xFF4ECDC4)
        ),
        Transaction(
            "Beeline",
            "Оплата мобильной связи",
            "-2 500 ₸",
            false,
            Icons.Filled.Phone,
            Color(0xFFFFBE0B)
        )
    )

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "home") },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Text(
                        "Финансовое здоровье",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A),
                        letterSpacing = (-0.5).sp
                    )
                }
            }

            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(bottom = 24.dp)
                ) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            AccountCard(
                                title = "Kaspi Gold",
                                amount = "1 240 890.50 ₸",
                                gradient = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFFF6B6B),
                                        Color(0xFFFF8E53)
                                    )
                                ),
                                onClick = { navController.navigate("cardDetails") }
                            )
                        }

                        item {
                            AccountCard(
                                title = "Депозит",
                                amount = "5 000.00 $",
                                gradient = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF4A90E2),
                                        Color(0xFF50E3C2)
                                    )
                                ),
                                onClick = { navController.navigate("investments") }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .background(Color(0xFFF8F9FA))
                )
            }

            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 24.dp)
                        .padding(top = 20.dp, bottom = 12.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Последние операции",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A1A)
                        )

                        TextButton(
                            onClick = { navController.navigate("history") },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "Все",
                                fontSize = 14.sp,
                                color = Color(0xFF4A90E2)
                            )
                        }
                    }
                }
            }

            items(transactions) { transaction ->
                TransactionRow(
                    transaction = transaction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }

            item {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(Color.White)
                )
            }
        }
    }
}

@Composable
fun AccountCard(
    title: String,
    amount: String,
    gradient: Brush,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .width(320.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(gradient)
            .clickable { onClick() }
            .padding(24.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )

            Text(
                amount,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-1).sp
            )
        }
    }
}

@Composable
fun TransactionRow(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(transaction.iconColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                transaction.icon,
                contentDescription = null,
                tint = transaction.iconColor,
                modifier = Modifier.size(28.dp)
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
            Text(
                transaction.subtitle,
                fontSize = 14.sp,
                color = Color(0xFF8E8E93)
            )
        }

        Text(
            transaction.amount,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (transaction.isPositive) Color(0xFF34C759) else Color(0xFF1A1A1A)
        )
    }
}

@Composable
fun BottomNavBar(navController: NavHostController, currentRoute: String) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Bills,
            BottomNavItem.History,
            BottomNavItem.Services,
            BottomNavItem.Profile
        )

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        item.label,
                        fontSize = 11.sp,
                        fontWeight = if (currentRoute == item.route) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF4A90E2),
                    selectedTextColor = Color(0xFF4A90E2),
                    unselectedIconColor = Color(0xFF8E8E93),
                    unselectedTextColor = Color(0xFF8E8E93),
                    indicatorColor = Color(0xFF4A90E2).copy(alpha = 0.12f)
                )
            )
        }
    }
}
