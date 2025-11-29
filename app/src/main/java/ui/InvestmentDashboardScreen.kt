package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.qazaqpaybank.ui.theme.BG
import com.example.qazaqpaybank.ui.theme.CardWhite
import com.example.qazaqpaybank.ui.theme.Navy
import com.example.qazaqpaybank.ui.theme.TealPrimary

@Composable
fun InvestmentDashboardScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "investments") },
        containerColor = BG
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text("Portfolio Value", style = MaterialTheme.typography.titleMedium, color = Navy)

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(TealPrimary.copy(alpha = 0.07f), shape = MaterialTheme.shapes.medium),
            ) {
                Text(
                    "$12,000",
                    Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                    color = Navy,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(Modifier.height(24.dp))

            var selectedTab by remember { mutableIntStateOf(0) }
            val periods = listOf("Day", "Week", "Month")

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = CardWhite
            ) {
                periods.forEachIndexed { idx, label ->
                    Tab(
                        text = { Text(label) },
                        selected = selectedTab == idx,
                        onClick = { selectedTab = idx }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text("Investment features coming soon...", color = Navy, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
