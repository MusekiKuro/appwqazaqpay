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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

data class BillCategory(
    val name: String,
    val icon: ImageVector,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillsScreen(navController: NavHostController) {
    var selectedCategory by remember { mutableStateOf<BillCategory?>(null) }
    var accountNumber by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    val categories = listOf(
        BillCategory("ЖКХ", Icons.Filled.Home, Color(0xFFFF6B6B)),
        BillCategory("Связь", Icons.Filled.PhoneAndroid, Color(0xFF4ECDC4)),
        BillCategory("Интернет", Icons.Filled.Wifi, Color(0xFF4A90E2)),
        BillCategory("ТВ", Icons.Filled.Tv, Color(0xFF9B59B6)),
        BillCategory("Штрафы", Icons.Filled.LocalParking, Color(0xFFE67E22)),
        BillCategory("Налоги", Icons.Filled.AccountBalance, Color(0xFF34C759)),
        BillCategory("Образование", Icons.Filled.School, Color(0xFFFFBE0B)),
        BillCategory("Другое", Icons.Filled.MoreHoriz, Color(0xFF8E8E93))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Платежи", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (selectedCategory != null) {
                            selectedCategory = null
                        } else {
                            navController.navigateUp()
                        }
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад", tint = Color(0xFF1A1A1A))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1A1A1A)
                )
            )
        },
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "bills") },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        if (selectedCategory == null) {
            // Сетка категорий
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Text(
                    "Выберите категорию",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )

                Spacer(Modifier.height(24.dp))

                categories.chunked(2).forEach { rowCategories ->
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowCategories.forEach { category ->
                            BillCategoryCard(
                                category = category,
                                modifier = Modifier.weight(1f),
                                onClick = { selectedCategory = category }
                            )
                        }
                        if (rowCategories.size == 1) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        } else {
            // Форма оплаты
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                // Заголовок категории
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF8F9FA), RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(56.dp)
                            .background(
                                selectedCategory!!.color.copy(alpha = 0.15f),
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            selectedCategory!!.icon,
                            contentDescription = null,
                            tint = selectedCategory!!.color,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Text(
                        selectedCategory!!.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )
                }

                Spacer(Modifier.height(32.dp))

                Text(
                    "Лицевой счёт",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8E8E93)
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = accountNumber,
                    onValueChange = { accountNumber = it },
                    placeholder = { Text("Введите номер счёта", color = Color(0xFFBDBDBD), fontSize = 15.sp) },
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

                Spacer(Modifier.height(16.dp))

                Text(
                    "Быстрая сумма",
                    fontSize = 13.sp,
                    color = Color(0xFF8E8E93)
                )
                Spacer(Modifier.height(8.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("500", "1000", "2000", "5000").forEach { quickAmount ->
                        OutlinedButton(
                            onClick = { amount = quickAmount },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF1A1A1A)
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE5E5EA))
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(quickAmount, fontSize = 13.sp)
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    "Комиссия: 0 ₸",
                    fontSize = 13.sp,
                    color = Color(0xFF8E8E93)
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (amount.isNotEmpty() && accountNumber.isNotEmpty()) {
                            showSuccess = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
                    shape = RoundedCornerShape(16.dp),
                    enabled = amount.isNotEmpty() && accountNumber.isNotEmpty()
                ) {
                    Text(
                        "Оплатить",
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
                title = { Text("Платёж выполнен!", color = Color(0xFF1A1A1A), fontWeight = FontWeight.Bold) },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Категория: ${selectedCategory?.name}", color = Color(0xFF8E8E93))
                        Text("Счёт: $accountNumber", color = Color(0xFF8E8E93))
                        Text("Сумма: $amount ₸", color = Color(0xFF8E8E93))
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccess = false
                            selectedCategory = null
                            amount = ""
                            accountNumber = ""
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

@Composable
fun BillCategoryCard(
    category: BillCategory,
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
        Icon(
            category.icon,
            contentDescription = null,
            tint = category.color,
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            category.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = Color(0xFF1A1A1A),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
