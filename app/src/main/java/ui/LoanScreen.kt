package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanScreen(navController: NavHostController) {
    var loanAmount by remember { mutableStateOf("") }
    var loanTerm by remember { mutableStateOf("12") }
    var purpose by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    val loanTerms = listOf("6", "12", "24", "36", "48", "60")
    val purposes = listOf(
        "Потребительский кредит",
        "Автокредит",
        "Ипотека",
        "Образование",
        "Ремонт",
        "Другое"
    )

    var expandedTerm by remember { mutableStateOf(false) }
    var expandedPurpose by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Заявка на кредит", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
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
            // Информация
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4A90E2).copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = null,
                    tint = Color(0xFF4A90E2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    "Заявка будет рассмотрена в течение 24 часов",
                    fontSize = 13.sp,
                    color = Color(0xFF4A90E2)
                )
            }

            Spacer(Modifier.height(24.dp))

            // Сумма кредита
            Text(
                "Сумма кредита",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF8E8E93)
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = loanAmount,
                onValueChange = { loanAmount = it },
                placeholder = { Text("Введите сумму", color = Color(0xFFBDBDBD), fontSize = 15.sp) },
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

            // Быстрые суммы
            Text(
                "Популярные суммы",
                fontSize = 13.sp,
                color = Color(0xFF8E8E93)
            )
            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("500 000", "1 000 000", "2 000 000").forEach { quickAmount ->
                    OutlinedButton(
                        onClick = { loanAmount = quickAmount.replace(" ", "") },
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
                        Text(quickAmount, fontSize = 12.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Срок кредита
            Text(
                "Срок кредита",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF8E8E93)
            )
            Spacer(Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expandedTerm,
                onExpandedChange = { expandedTerm = !expandedTerm }
            ) {
                OutlinedTextField(
                    value = "$loanTerm месяцев",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF8F9FA),
                        unfocusedContainerColor = Color(0xFFF8F9FA),
                        focusedBorderColor = Color(0xFF4A90E2),
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        Icon(
                            if (expandedTerm) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color(0xFF8E8E93)
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                )

                ExposedDropdownMenu(
                    expanded = expandedTerm,
                    onDismissRequest = { expandedTerm = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    loanTerms.forEach { term ->
                        DropdownMenuItem(
                            text = { Text("$term месяцев", fontSize = 15.sp) },
                            onClick = {
                                loanTerm = term
                                expandedTerm = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Цель кредита
            Text(
                "Цель кредита",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF8E8E93)
            )
            Spacer(Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expandedPurpose,
                onExpandedChange = { expandedPurpose = !expandedPurpose }
            ) {
                OutlinedTextField(
                    value = purpose.ifEmpty { "Выберите цель" },
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF8F9FA),
                        unfocusedContainerColor = Color(0xFFF8F9FA),
                        focusedBorderColor = Color(0xFF4A90E2),
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        Icon(
                            if (expandedPurpose) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color(0xFF8E8E93)
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                )

                ExposedDropdownMenu(
                    expanded = expandedPurpose,
                    onDismissRequest = { expandedPurpose = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    purposes.forEach { p ->
                        DropdownMenuItem(
                            text = { Text(p, fontSize = 15.sp) },
                            onClick = {
                                purpose = p
                                expandedPurpose = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // Расчёт
            if (loanAmount.isNotEmpty()) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Ежемесячный платёж:", fontSize = 14.sp, color = Color(0xFF8E8E93))
                        Text(
                            "~${(loanAmount.toIntOrNull() ?: 0) / loanTerm.toInt() + 5000} ₸",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A1A)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Процентная ставка:", fontSize = 14.sp, color = Color(0xFF8E8E93))
                        Text("12% годовых", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
                    }
                }

                Spacer(Modifier.height(24.dp))
            }

            // Кнопка
            Button(
                onClick = {
                    if (loanAmount.isNotEmpty() && purpose.isNotEmpty()) {
                        showSuccess = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
                shape = RoundedCornerShape(16.dp),
                enabled = loanAmount.isNotEmpty() && purpose.isNotEmpty()
            ) {
                Text(
                    "Подать заявку",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
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
                title = { Text("Заявка отправлена!", color = Color(0xFF1A1A1A), fontWeight = FontWeight.Bold) },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Сумма: $loanAmount ₸", color = Color(0xFF8E8E93))
                        Text("Срок: $loanTerm месяцев", color = Color(0xFF8E8E93))
                        Text("Цель: $purpose", color = Color(0xFF8E8E93))
                        Spacer(Modifier.height(8.dp))
                        Text("Мы рассмотрим вашу заявку в течение 24 часов", fontSize = 13.sp, color = Color(0xFF8E8E93))
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccess = false
                            navController.navigate("home")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2))
                    ) {
                        Text("Отлично")
                    }
                }
            )
        }
    }
}
