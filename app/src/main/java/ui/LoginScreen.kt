package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qazaqpaybank.viewmodel.LoginViewModel
import com.example.qazaqpaybank.ui.theme.BackgroundLight
import com.example.qazaqpaybank.ui.theme.CardWhite
import com.example.qazaqpaybank.ui.theme.PrimaryGreen
import com.example.qazaqpaybank.ui.theme.TextHint
import com.example.qazaqpaybank.ui.theme.TextPrimary
import com.example.qazaqpaybank.ui.theme.TextSecondary
import androidx.navigation.NavHostController


@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val mfaRequired by viewModel.mfaRequired.collectAsState()
    val success by viewModel.success.collectAsState()

    // Навигация дальше (MFA / Home)
    LaunchedEffect(mfaRequired, success) {
        if (mfaRequired) {
            onLoginSuccess(email)
        } else if (success) {
            onLoginSuccess("")
        }
    }

    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.Start
        ) {
            // Заголовок как в HTML
            Text(
                text = "Управляйте финансами,\nоплачивайте услуги и инвестируйте\nв одном приложении.",
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Телефон
            Text(
                text = "Телефон",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.email.value = it },
                placeholder = { Text("+7 ___ ___‑__‑__", color = TextHint) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardWhite,
                    unfocusedContainerColor = CardWhite,
                    disabledContainerColor = CardWhite,
                    focusedBorderColor = PrimaryGreen,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = PrimaryGreen
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Пароль
            Text(
                text = "Пароль",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.password.value = it },
                placeholder = { Text("Введите пароль", color = TextHint) },
                singleLine = true,
                visualTransformation = if (isPasswordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = TextSecondary
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardWhite,
                    unfocusedContainerColor = CardWhite,
                    disabledContainerColor = CardWhite,
                    focusedBorderColor = PrimaryGreen,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = PrimaryGreen
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Все операции надежно защищены",
                color = TextSecondary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Кнопка "Войти" в стиле зелёного прямоугольника
            Button(
                onClick = { viewModel.login() },
                enabled = !loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen,
                    contentColor = Color.White,
                    disabledContainerColor = PrimaryGreen.copy(alpha = 0.5f),
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Войти",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Нет аккаунта? ", color = Color(0xFF8E8E93), fontSize = 14.sp)
                TextButton(
                    onClick = { navController.navigate("register") },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Зарегистрироваться", color = Color(0xFF4A90E2), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }

            if (error != null) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = error ?: "",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }
        }
    }
}
