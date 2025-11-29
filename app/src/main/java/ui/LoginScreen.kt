package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qazaqpaybank.ui.theme.TealPrimary
import com.example.qazaqpaybank.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.init(context)
    }

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val mfaRequired by viewModel.mfaRequired.collectAsState()
    val success by viewModel.success.collectAsState()

    // Observe login success or MFA required
    LaunchedEffect(mfaRequired, success) {
        if (mfaRequired) {
            onLoginSuccess(email) // Navigate to MFA with email
        } else if (success) {
            onLoginSuccess("") // Navigate directly to home
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(TealPrimary, TealPrimary.copy(alpha = 0.8f))
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome Back",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.email.value = it },
                label = { Text("Email") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.password.value = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Icon(
                Icons.Filled.Fingerprint,
                contentDescription = "Fingerprint",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .background(TealPrimary, CircleShape)
                    .padding(8.dp)
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { viewModel.login() },
                enabled = !loading,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = TealPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Login", color = TealPrimary)
                }
            }

            if (error != null) {
                Spacer(Modifier.height(8.dp))
                Text(error!!, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
