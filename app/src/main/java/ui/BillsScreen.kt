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
import com.example.qazaqpaybank.ui.theme.BG
import com.example.qazaqpaybank.ui.theme.CardWhite
import com.example.qazaqpaybank.ui.theme.Navy
import com.example.qazaqpaybank.ui.theme.TealPrimary

data class BillCategory(
    val name: String,
    val icon: ImageVector,
    val examples: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillsScreen(navController: NavHostController) {
    var selectedCategory by remember { mutableStateOf<BillCategory?>(null) }
    var accountNumber by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    val categories = listOf(
        BillCategory("Utilities", Icons.Filled.ElectricBolt, listOf("Electricity", "Water", "Gas")),
        BillCategory("Mobile", Icons.Filled.PhoneAndroid, listOf("Beeline", "Kcell", "Tele2")),
        BillCategory("Internet", Icons.Filled.Wifi, listOf("Kazakhtelecom", "Beeline", "Altel")),
        BillCategory("TV", Icons.Filled.Tv, listOf("Alma TV", "IPTV", "Satellite"))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pay Bills", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (selectedCategory != null) {
                            selectedCategory = null
                        } else {
                            navController.navigateUp()
                        }
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BG,
                    titleContentColor = Navy
                )
            )
        },
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "bills") },
        containerColor = BG
    ) { padding ->
        if (selectedCategory == null) {
            // Categories Grid
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                Text(
                    "Select Category",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )

                Spacer(Modifier.height(16.dp))

                categories.chunked(2).forEach { rowCategories ->
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowCategories.forEach { category ->
                            CategoryCard(
                                category = category,
                                modifier = Modifier.weight(1f),
                                onClick = { selectedCategory = category }
                            )
                        }
                        if (rowCategories.size == 1) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        } else {
            // Payment Form
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Category Header
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(CardWhite, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        selectedCategory!!.icon,
                        contentDescription = null,
                        tint = TealPrimary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        selectedCategory!!.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Account/Phone Number
                Text("Account Number", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Navy)
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = accountNumber,
                    onValueChange = { accountNumber = it },
                    placeholder = { Text("Enter account or phone number") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CardWhite,
                        unfocusedContainerColor = CardWhite,
                        focusedBorderColor = TealPrimary
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.AccountBox, contentDescription = null, tint = TealPrimary)
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

                Spacer(Modifier.height(16.dp))

                // Quick amounts
                Text("Quick Amount", fontSize = 14.sp, color = Navy.copy(alpha = 0.7f))
                Spacer(Modifier.height(8.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("500", "1000", "2000", "5000").forEach { quickAmount ->
                        Button(
                            onClick = { amount = quickAmount },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CardWhite,
                                contentColor = Navy
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("$quickAmount", fontSize = 12.sp)
                        }
                    }
                }

                Spacer(Modifier.height(32.dp))

                // Pay Button
                Button(
                    onClick = {
                        if (amount.isNotEmpty() && accountNumber.isNotEmpty()) {
                            showSuccess = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                    shape = RoundedCornerShape(12.dp),
                    enabled = amount.isNotEmpty() && accountNumber.isNotEmpty()
                ) {
                    Text("Pay $${amount.ifEmpty { "0" }}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
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
                title = { Text("Payment Successful!", textAlign = androidx.compose.ui.text.style.TextAlign.Center) },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Category: ${selectedCategory?.name}")
                        Text("Account: $accountNumber")
                        Text("Amount: $$amount")
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
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                    ) {
                        Text("Done")
                    }
                }
            )
        }
    }
}

@Composable
fun CategoryCard(
    category: BillCategory,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier
            .aspectRatio(1f)
            .background(CardWhite, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            category.icon,
            contentDescription = null,
            tint = TealPrimary,
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            category.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Navy
        )
    }
}
