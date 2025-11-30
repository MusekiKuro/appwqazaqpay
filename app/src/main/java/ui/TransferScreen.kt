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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(navController: NavHostController) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedCard by remember { mutableStateOf("Основная карта •••• 1234, 150 000 ₸") }
    var showCardSelector by remember { mutableStateOf(false) }
    var recipientCard by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    val cards = listOf(
        "Основная карта •••• 1234, 150 000 ₸",
        "Kaspi Gold •••• 5678, 1 240 890 ₸",
        "Депозит •••• 9012, 5 000 $"
    )

    val tabs = listOf("Свой счёт", "На карту другого банка", "По номеру телефона", "Международный")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Перевод", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
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
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "transfer") },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color(0xFF4A90E2),
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFF4A90E2),
                        height = 3.dp
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                title,
                                fontSize = 14.sp,
                                fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (selectedTab == index) Color(0xFF4A90E2) else Color(0xFF8E8E93)
                            )
                        },
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Text(
                    "Откуда",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8E8E93)
                )
                Spacer(Modifier.height(8.dp))

                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp))
                        .clickable { showCardSelector = !showCardSelector }
                        .padding(16.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            selectedCard,
                            fontSize = 15.sp,
                            color = Color(0xFF1A1A1A),
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            if (showCardSelector) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color(0xFF8E8E93)
                        )
                    }
                }

                if (showCardSelector) {
                    Spacer(Modifier.height(8.dp))
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp))
                            .padding(4.dp)
                    ) {
                        cards.forEach { card ->
                            Text(
                                card,
                                fontSize = 15.sp,
                                color = Color(0xFF1A1A1A),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCard = card
                                        showCardSelector = false
                                    }
                                    .padding(12.dp)
                            )
                            if (card != cards.last()) {
                                Divider(color = Color(0xFFE5E5EA))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    "Куда",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8E8E93)
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = recipientCard,
                    onValueChange = { recipientCard = it },
                    placeholder = { Text("Введите номер карты", color = Color(0xFFBDBDBD), fontSize = 15.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF8F9FA),
                        unfocusedContainerColor = Color(0xFFF8F9FA),
                        focusedBorderColor = Color(0xFF4A90E2),
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    "Сумма",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8E8E93)
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    placeholder = { Text("0", color = Color(0xFFBDBDBD), fontSize = 15.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF8F9FA),
                        unfocusedContainerColor = Color(0xFFF8F9FA),
                        focusedBorderColor = Color(0xFF4A90E2),
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = { Text("₸", color = Color(0xFF8E8E93), fontSize = 16.sp, modifier = Modifier.padding(end = 8.dp)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Комиссия: 0 ₸",
                    fontSize = 13.sp,
                    color = Color(0xFF8E8E93)
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    "Комментарий",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8E8E93)
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    placeholder = { Text("Необязательно", color = Color(0xFFBDBDBD), fontSize = 15.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF8F9FA),
                        unfocusedContainerColor = Color(0xFFF8F9FA),
                        focusedBorderColor = Color(0xFF4A90E2),
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 3,
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (amount.isNotEmpty() && recipientCard.isNotEmpty()) {
                            showSuccess = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
                    shape = RoundedCornerShape(16.dp),
                    enabled = amount.isNotEmpty() && recipientCard.isNotEmpty()
                ) {
                    Text(
                        "Перевести",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        if (showSuccess) {
            AlertDialog(
                onDismissRequest = { showSuccess = false },
                icon = {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF34C759),
                        modifier = Modifier.size(64.dp)
                    )
                },
                title = { Text("Перевод выполнен!", color = Color(0xFF1A1A1A), fontWeight = FontWeight.Bold) },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Сумма: $amount ₸", color = Color(0xFF8E8E93))
                        Text("Получатель: $recipientCard", color = Color(0xFF8E8E93))
                        if (comment.isNotEmpty()) {
                            Text("Комментарий: $comment", color = Color(0xFF8E8E93))
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccess = false
                            amount = ""
                            recipientCard = ""
                            comment = ""
                            navController.navigate("home")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2))
                    ) {
                        Text("Готово")
                    }
                }
            )
        }
    }
}
