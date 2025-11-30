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
import com.example.qazaqpaybank.ui.theme.BG
import com.example.qazaqpaybank.ui.theme.CardWhite
import com.example.qazaqpaybank.ui.theme.Navy
import com.example.qazaqpaybank.ui.theme.TealPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailsScreen(navController: NavHostController) {
    var isBlocked by remember { mutableStateOf(false) }
    var showBlockDialog by remember { mutableStateOf(false) }
    var showLimitDialog by remember { mutableStateOf(false) }
    var currentLimit by remember { mutableStateOf("50000") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Card Details", fontWeight = FontWeight.Bold) },
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
        containerColor = BG
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Card Display
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        if (isBlocked) Navy else TealPrimary,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Visa Card", color = Color.White, fontSize = 16.sp)
                        if (isBlocked) {
                            Surface(
                                color = Color.Red,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    "BLOCKED",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(40.dp))

                    Text(
                        "**** **** **** 1234",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(20.dp))

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("CARD HOLDER", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
                            Text("AMANZHOL MUSEKI", color = Color.White, fontSize = 14.sp)
                        }
                        Column {
                            Text("EXPIRES", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
                            Text("12/27", color = Color.White, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Card Info
            Text("Card Information", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Navy)
            Spacer(Modifier.height(16.dp))

            InfoCard("Balance", "$${currentLimit}")
            Spacer(Modifier.height(12.dp))
            InfoCard("Card Number", "**** **** **** 1234")
            Spacer(Modifier.height(12.dp))
            InfoCard("Account", "KZ123456789012345678")
            Spacer(Modifier.height(12.dp))
            InfoCard("Status", if (isBlocked) "Blocked" else "Active")

            Spacer(Modifier.height(32.dp))

            // Actions
            Text("Card Actions", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Navy)
            Spacer(Modifier.height(16.dp))

            // Block/Unblock Button
            Button(
                onClick = { showBlockDialog = true },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isBlocked) TealPrimary else Color.Red
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    if (isBlocked) Icons.Filled.LockOpen else Icons.Filled.Lock,
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    if (isBlocked) "Unblock Card" else "Block Card",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(12.dp))

            // Set Limit Button
            OutlinedButton(
                onClick = { showLimitDialog = true },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TealPrimary
                )
            ) {
                Icon(Icons.Filled.AccountBalanceWallet, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Set Spending Limit", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(12.dp))

            // View Statements
            OutlinedButton(
                onClick = { navController.navigate("history") },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TealPrimary
                )
            ) {
                Icon(Icons.Filled.Receipt, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("View Statements", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Block Dialog
        if (showBlockDialog) {
            AlertDialog(
                onDismissRequest = { showBlockDialog = false },
                icon = {
                    Icon(
                        if (isBlocked) Icons.Filled.LockOpen else Icons.Filled.Lock,
                        contentDescription = null,
                        tint = if (isBlocked) TealPrimary else Color.Red,
                        modifier = Modifier.size(48.dp)
                    )
                },
                title = {
                    Text(if (isBlocked) "Unblock Card?" else "Block Card?")
                },
                text = {
                    Text(
                        if (isBlocked)
                            "Your card will be unblocked and you can use it for transactions."
                        else
                            "Your card will be blocked temporarily. You won't be able to make any transactions."
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            isBlocked = !isBlocked
                            showBlockDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isBlocked) TealPrimary else Color.Red
                        )
                    ) {
                        Text(if (isBlocked) "Unblock" else "Block")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showBlockDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Limit Dialog
        if (showLimitDialog) {
            var newLimit by remember { mutableStateOf(currentLimit) }

            AlertDialog(
                onDismissRequest = { showLimitDialog = false },
                icon = {
                    Icon(
                        Icons.Filled.AccountBalanceWallet,
                        contentDescription = null,
                        tint = TealPrimary,
                        modifier = Modifier.size(48.dp)
                    )
                },
                title = { Text("Set Spending Limit") },
                text = {
                    Column {
                        Text("Enter new monthly spending limit:")
                        Spacer(Modifier.height(16.dp))
                        OutlinedTextField(
                            value = newLimit,
                            onValueChange = { newLimit = it },
                            label = { Text("Limit") },
                            leadingIcon = { Text("$", color = TealPrimary) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            currentLimit = newLimit
                            showLimitDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLimitDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun InfoCard(label: String, value: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(CardWhite, RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Navy.copy(alpha = 0.7f), fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.SemiBold, color = Navy, fontSize = 16.sp)
    }
}
