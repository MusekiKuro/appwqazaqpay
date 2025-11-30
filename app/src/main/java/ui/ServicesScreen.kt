package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class Service(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(navController: NavHostController) {
    val services = listOf(
        Service("Переводы", Icons.Filled.Send, "transfer", Color(0xFF4A90E2)),
        Service("Платежи", Icons.Filled.Receipt, "bills", Color(0xFF34C759)),
        Service("QR-оплата", Icons.Filled.QrCode2, "qr", Color(0xFF9B59B6)),
        Service("Карты", Icons.Filled.CreditCard, "cardDetails", Color(0xFFFF6B6B)),
        Service("История", Icons.Filled.History, "history", Color(0xFFFFBE0B)),
        Service("Инвестиции", Icons.Filled.TrendingUp, "investments", Color(0xFF4ECDC4)),
        Service("Кредиты", Icons.Filled.AccountBalance, "loan", Color(0xFF34C759)),
        Service("AI Чат", Icons.Filled.SmartToy, "chat", Color(0xFF9B59B6))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Сервисы", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1A1A1A)
                )
            )
        },
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "services") },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(24.dp)
        ) {
            Text(
                "Все сервисы",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )

            Spacer(Modifier.height(24.dp))

            services.chunked(2).forEach { rowServices ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rowServices.forEach { service ->
                        ServiceCard(
                            service = service,
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate(service.route) }
                        )
                    }
                    if (rowServices.size == 1) {
                        Spacer(Modifier.weight(1f))
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ServiceCard(
    service: Service,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier
            .aspectRatio(1f)
            .background(Color(0xFFF8F9FA), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .size(64.dp)
                .background(service.color.copy(alpha = 0.15f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                service.icon,
                contentDescription = null,
                tint = service.color,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            service.title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = Color(0xFF1A1A1A),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
