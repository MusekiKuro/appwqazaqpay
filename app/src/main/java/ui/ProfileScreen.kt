package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

data class ProfileMenuItem(
    val title: String,
    val icon: ImageVector,
    val route: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val menuItems = listOf(
        ProfileMenuItem("Мои карты", Icons.Filled.CreditCard, "cardDetails"),
        ProfileMenuItem("Настройки", Icons.Filled.Settings, ""),
        ProfileMenuItem("Безопасность", Icons.Filled.Lock, ""),
        ProfileMenuItem("Уведомления", Icons.Filled.Notifications, ""),
        ProfileMenuItem("FAQ", Icons.Filled.Help, "faq"),
        ProfileMenuItem("О приложении", Icons.Filled.Info, "")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1A1A1A)
                )
            )
        },
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "profile") },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4A90E2)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "АМ",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    "Аманжол Мусеки",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "+7 (777) 123-45-67",
                    fontSize = 15.sp,
                    color = Color(0xFF8E8E93)
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatItem(
                        title = "Карты",
                        value = "3",
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        title = "Баланс",
                        value = "1.4M ₸",
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        title = "Операции",
                        value = "156",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp)
            ) {
                menuItems.forEach { item ->
                    ProfileMenuRow(
                        item = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (item.route.isNotEmpty()) {
                                    navController.navigate(item.route)
                                }
                            }
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                    )
                    if (item != menuItems.last()) {
                        Divider(
                            color = Color(0xFFF8F9FA),
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("login") }
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.ExitToApp,
                        contentDescription = null,
                        tint = Color(0xFFFF6B6B),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        "Выйти",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFFF6B6B)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            title,
            fontSize = 13.sp,
            color = Color(0xFF8E8E93)
        )
    }
}

@Composable
fun ProfileMenuRow(
    item: ProfileMenuItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            item.icon,
            contentDescription = null,
            tint = Color(0xFF4A90E2),
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            item.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF8E8E93),
            modifier = Modifier.size(24.dp)
        )
    }
}
