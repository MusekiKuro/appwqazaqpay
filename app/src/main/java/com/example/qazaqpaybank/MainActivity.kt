package com.example.qazaqpaybank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.qazaqpaybank.ui.*
import com.example.qazaqpaybank.ui.theme.QazaqPayBankTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QazaqPayBankTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = { email ->
                                if (email.isNotEmpty()) {
                                    navController.navigate("mfa/$email")
                                } else {
                                    navController.navigate("home")
                                }
                            }
                        )
                    }
                    composable(
                        "mfa/{email}",
                        arguments = listOf(navArgument("email") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val email = backStackEntry.arguments?.getString("email") ?: ""
                        MfaScreen(
                            email = email,
                            onMfaSuccess = { navController.navigate("home") }
                        )
                    }
                    composable("home") { HomeScreen(navController) }
                    composable("transfer") { TransferScreen(navController) }
                    composable("history") { HistoryScreen(navController) }
                    composable("investments") { InvestmentDashboardScreen(navController) }
                }
            }
        }
    }
}
