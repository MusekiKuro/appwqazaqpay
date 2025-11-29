package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qazaqpaybank.ui.theme.BG
import com.example.qazaqpaybank.ui.theme.Navy
import com.example.qazaqpaybank.ui.theme.TealPrimary
import com.example.qazaqpaybank.viewmodel.LoginViewModel

@Composable
fun MfaScreen(
    email: String,
    onMfaSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.init(context)
    }

    var code by remember { mutableStateOf("") }
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val mfaSuccess by viewModel.mfaSuccess.collectAsState()

    // Observe MFA success
    LaunchedEffect(mfaSuccess) {
        if (mfaSuccess) {
            onMfaSuccess()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(BG)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Enter MFA Code",
            style = MaterialTheme.typography.headlineMedium,
            color = Navy,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Code sent to $email",
            style = MaterialTheme.typography.bodyMedium,
            color = Navy.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("6-digit code") },
            placeholder = { Text("123456") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.verifyMfa(email, code)
            },
            enabled = !loading && code.length == 6,
            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Verify", color = Color.White)
            }
        }

        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error!!, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
