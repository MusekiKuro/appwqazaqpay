package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.qazaqpaybank.data.Card
import com.example.qazaqpaybank.ui.theme.BG
import com.example.qazaqpaybank.ui.theme.CardWhite
import com.example.qazaqpaybank.ui.theme.Navy
import com.example.qazaqpaybank.ui.theme.TealPrimary
import com.example.qazaqpaybank.viewmodel.CardsViewModel

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Outlined.Home)
    object Transfer : BottomNavItem("transfer", "Transfer", Icons.Outlined.Send)
    object History : BottomNavItem("history", "History", Icons.Outlined.DateRange)
    object Investments : BottomNavItem("investments", "Invest", Icons.Outlined.ShowChart)
}

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: CardsViewModel = viewModel()
) {
    val context = LocalContext.current
    val cards by viewModel.cards.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.init(context)
        viewModel.loadCards()
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "home") },
        containerColor = BG
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header
            Text(
                "My Cards",
                Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            // Loading/Error states
            if (loading) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = TealPrimary)
                }
            } else if (error != null) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        error!!,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else if (cards.isEmpty()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No cards found", color = Navy)
                }
            } else {
                // Cards carousel
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cards) { card ->
                        CardItem(card = card) {
                            navController.navigate("cardDetails")
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Quick Actions",
                Modifier.padding(horizontal = 20.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(12.dp))

            // First Row - Transfer & Bills
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionButton("Transfer", Icons.Outlined.Send, Modifier.weight(1f)) {
                    navController.navigate("transfer")
                }
                QuickActionButton("Bills", Icons.Outlined.Receipt, Modifier.weight(1f)) {
                    navController.navigate("bills")
                }
            }

            Spacer(Modifier.height(12.dp))

            // Second Row - QR Pay & History
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionButton("QR Pay", Icons.Outlined.QrCodeScanner, Modifier.weight(1f)) {
                    navController.navigate("qr")
                }
                QuickActionButton("History", Icons.Outlined.DateRange, Modifier.weight(1f)) {
                    navController.navigate("history")
                }
            }
        }
    }
}

@Composable
fun CardItem(card: Card, onClick: () -> Unit = {}) {
    Box(
        Modifier
            .width(300.dp)
            .height(180.dp)
            .background(
                if (card.status == "ACTIVE") TealPrimary else Navy,
                RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column {
            Text("${card.cardHolder}", color = Color.White, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                card.cardNumber.replace(".....", "**** "),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Text("Expires: ${card.expiryDate}", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            Text("Limit: $${card.limit}", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun QuickActionButton(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(80.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CardWhite),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = label, tint = TealPrimary, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(8.dp))
            Text(label, fontSize = 14.sp, color = Navy, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController, currentRoute: String) {
    NavigationBar(
        containerColor = CardWhite
    ) {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Transfer,
            BottomNavItem.History,
            BottomNavItem.Investments
        )

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = TealPrimary,
                    selectedTextColor = TealPrimary,
                    indicatorColor = TealPrimary.copy(alpha = 0.1f)
                )
            )
        }
    }
}
