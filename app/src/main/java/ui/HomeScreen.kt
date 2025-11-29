package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.qazaqpaybank.ui.theme.BG
import com.example.qazaqpaybank.ui.theme.CardWhite
import com.example.qazaqpaybank.ui.theme.Navy
import com.example.qazaqpaybank.ui.theme.TealPrimary

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Outlined.Home)
    object Transfer : BottomNavItem("transfer", "Transfer", Icons.Outlined.Send)
    object History : BottomNavItem("history", "History", Icons.Outlined.DateRange)
    object Investments : BottomNavItem("investments", "Invest", Icons.Outlined.ShowChart)
    object Cards : BottomNavItem("cards", "Cards", Icons.Outlined.Payment)

}


@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = CardWhite) {
                val items = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Transfer,
                    BottomNavItem.History,
                    BottomNavItem.Investments,
                    BottomNavItem.Cards
                )
                val currentRoute = navController.currentDestination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label, tint = TealPrimary) },
                        label = { Text(item.label, fontSize = 12.sp) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) { launchSingleTop = true }
                        }
                    )
                }
            }
        },
        containerColor = BG
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                "Your Cards",
                style = MaterialTheme.typography.titleMedium,
                color = Navy,
                modifier = Modifier.padding(16.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(3) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Navy),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.size(width = 280.dp, height = 160.dp)
                    ) {
                        Column(
                            Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("БЦК Card", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("**** 2398", color = Color.White, style = MaterialTheme.typography.titleLarge)
                            Text("$12,080.50", color = Color.White, style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                }
            }
        }
    }
}
