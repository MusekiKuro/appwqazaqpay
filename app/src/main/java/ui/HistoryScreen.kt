package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.qazaqpaybank.ui.theme.BG
import com.example.qazaqpaybank.ui.theme.CardWhite
import com.example.qazaqpaybank.ui.theme.Navy
import com.example.qazaqpaybank.ui.theme.TealPrimary
import com.example.qazaqpaybank.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(
    navController: NavHostController,
    viewModel: HistoryViewModel = viewModel()
) {
    val transactions by viewModel.transactions.collectAsState()

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Transactions",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search", tint = TealPrimary)
                }
            }
        },
        containerColor = BG
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp, bottom = 64.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(transactions) { txn ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .background(CardWhite, shape = MaterialTheme.shapes.medium)
                        .shadow(2.dp, MaterialTheme.shapes.medium)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(40.dp)
                            .background(TealPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(txn.avatarLabel, color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(Modifier.width(12.dp))

                    Column(Modifier.weight(1f)) {
                        Text(txn.name, fontWeight = FontWeight.SemiBold)
                        Text(txn.date, color = Navy, style = MaterialTheme.typography.bodySmall)
                    }

                    Text(
                        (if (txn.amount > 0) "+" else "") + "$%.2f".format(txn.amount),
                        color = if (txn.amount > 0) TealPrimary else Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
