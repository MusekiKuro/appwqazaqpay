package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.qazaqpaybank.ui.theme.BG
import com.example.qazaqpaybank.ui.theme.Navy
import com.example.qazaqpaybank.ui.theme.TealPrimary

@Composable
fun TransferScreen(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(BG)
            .padding(24.dp)
    ) {
        Text("Send to", style = MaterialTheme.typography.titleMedium, color = Navy)

        LazyRow(
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(TealPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("AB", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Amount", style = MaterialTheme.typography.labelLarge, color = Navy)
        OutlinedTextField(
            value = "150.00",
            onValueChange = {},
            leadingIcon = { Text("$", color = Navy, fontWeight = FontWeight.Bold) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continue", color = Color.White)
        }
    }
}
