package com.example.qazaqpaybank.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Transaction(
    val id: String,
    val name: String,
    val amount: Double,
    val date: String,
    val avatarLabel: String
)

class HistoryViewModel : ViewModel() {

    private val _transactions = MutableStateFlow(
        listOf(
            Transaction("1", "Groceries", -84.31, "Today", "GR"),
            Transaction("2", "Salary", 2000.00, "Yesterday", "SL"),
            Transaction("3", "Netflix", -15.00, "Jun 10", "NF"),
            Transaction("4", "Coffee Shop", -5.50, "Jun 09", "CF"),
            Transaction("5", "Gym Membership", -50.00, "Jun 08", "GM"),
        )
    )

    val transactions: StateFlow<List<Transaction>> = _transactions
}
