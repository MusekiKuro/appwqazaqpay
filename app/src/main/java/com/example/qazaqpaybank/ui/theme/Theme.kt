package com.example.qazaqpaybank.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = TealPrimary,
    onPrimary = Color.White,
    secondary = Navy,
    background = BG,
    surface = CardWhite,
    onSurface = Navy
)

@Composable
fun QazaqPayBankTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}
