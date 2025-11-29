package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.qazaqpaybank.ui.theme.BG
import com.example.qazaqpaybank.ui.theme.CardWhite
import com.example.qazaqpaybank.ui.theme.Navy
import com.example.qazaqpaybank.ui.theme.TealPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(navController: NavHostController) {
    var amount by remember { mutableStateOf("") }
    var recipientCard by remember { mutableStateOf("") }
    var selectedCard by remember { mutableStateOf("**** **** **** 1234") }
    var showCardSelector by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    val cards = listOf(
        "**** **** **** 1234" to "Balance: $50,000",
        "**** **** **** 5678" to "Balance: $30,000",
        "**** **** **** 9012" to "Balance: $100,000 (Blocked)"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transfer Money", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BG,
                    titleContentColor = Navy
                )
            )
        },
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "transfer") },
        containerColor = BG
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // From Card
            Text("From Card", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Navy)
            Spacer(Modifier.height(8.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .background(CardWhite, RoundedCornerShape(12.dp))
                    .clickable { showCardSelector = !showCardSelector }
                    .padding(16.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(selectedCard, fontWeight = FontWeight.Bold, color = Navy)
                        Text(
                            cards.find { it.first == selectedCard }?.second ?: "",
                            fontSize = 12.sp,
                            color = Navy.copy(alpha = 0.6f)
                        )
                    }
                    Icon(
                        if (showCardSelector) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        tint = TealPrimary
                    )
                }
            }

            // Card Selector
            if (showCardSelector) {
                Spacer(Modifier.height(8.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(CardWhite, RoundedCornerShape(12.dp))
                        .padding(8.dp)
                ) {
                    cards.forEach { (cardNumber, balance) ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedCard = cardNumber
                                    showCardSelector = false
                                }
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(cardNumber, fontWeight = FontWeight.SemiBold)
                                Text(balance, fontSize = 12.sp, color = Navy.copy(alpha = 0.6f))
                            }
                        }
                        if (cardNumber != cards.last().first) {
                            Divider()
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Recipient Card
            Text("To Card Number", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Navy)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = recipientCard,
                onValueChange = { recipientCard = it },
                placeholder = { Text("Enter 16-digit card number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardWhite,
                    unfocusedContainerColor = CardWhite,
                    focusedBorderColor = TealPrimary
                ),
                leadingIcon = {
                    Icon(Icons.Filled.CreditCard, contentDescription = null, tint = TealPrimary)
                }
            )

            Spacer(Modifier.height(24.dp))

            // Amount
            Text("Amount", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Navy)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                placeholder = { Text("Enter amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardWhite,
                    unfocusedContainerColor = CardWhite,
                    focusedBorderColor = TealPrimary
                ),
                leadingIcon = {
                    Text("$", fontSize = 24.sp, color = TealPrimary, modifier = Modifier.padding(start = 12.dp))
                }
            )

            Spacer(Modifier.height(32.dp))

            // Quick amounts
            Text("Quick Amount", fontSize = 14.sp, color = Navy.copy(alpha = 0.7f))
            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("1000", "5000", "10000").forEach { quickAmount ->
                    Button(
                        onClick = { amount = quickAmount },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CardWhite,
                            contentColor = Navy
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("$quickAmount")
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // Transfer Button
            Button(
                onClick = {
                    if (amount.isNotEmpty() && recipientCard.length == 16) {
                        showSuccess = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                shape = RoundedCornerShape(12.dp),
                enabled = amount.isNotEmpty() && recipientCard.length == 16
            ) {
                Text("Transfer $${amount.ifEmpty { "0" }}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Success Dialog
        if (showSuccess) {
            AlertDialog(
                onDismissRequest = { showSuccess = false },
                icon = {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = TealPrimary,
                        modifier = Modifier.size(64.dp)
                    )
                },
                title = { Text("Transfer Successful!", textAlign = androidx.compose.ui.text.style.TextAlign.Center) },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Amount: $$amount")
                        Text("To: $recipientCard")
                        Text("From: $selectedCard")
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccess = false
                            amount = ""
                            recipientCard = ""
                            navController.navigate("home")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                    ) {
                        Text("Done")
                    }
                }
            )
        }
    }
}
