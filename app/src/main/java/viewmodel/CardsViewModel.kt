package com.example.qazaqpaybank.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qazaqpaybank.data.ApiClient
import com.example.qazaqpaybank.data.AuthRepository
import com.example.qazaqpaybank.data.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardsViewModel : ViewModel() {

    private var repo: AuthRepository? = null

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun init(context: Context) {
        if (repo == null) {
            repo = AuthRepository(context)
        }
    }

    fun loadCards() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                val token = repo?.getToken()
                Log.d("CardsViewModel", "Token: $token")

                if (token != null && !token.startsWith("demo")) {
                    // Попробуем загрузить с backend
                    try {
                        val cardsList = ApiClient.apiService.myCards("Bearer $token")
                        Log.d("CardsViewModel", "Cards loaded from backend: ${cardsList.size}")
                        _cards.value = cardsList
                        _loading.value = false
                        return@launch
                    } catch (e: Exception) {
                        Log.w("CardsViewModel", "Failed to load from backend: ${e.message}")
                        // Продолжаем к mock данным
                    }
                }

                // ИСПОЛЬЗУЕМ MOCK ДАННЫЕ для демо
                Log.d("CardsViewModel", "Using mock cards")
                _cards.value = listOf(
                    Card(
                        id = 1,
                        cardNumber = ".....1234",
                        expiryDate = "12/27",
                        cardHolder = "AMANZHOL MUSEKI",
                        status = "ACTIVE",
                        limit = 50000.0,
                        accountNumber = "KZ123456789012345678"
                    ),
                    Card(
                        id = 2,
                        cardNumber = ".....5678",
                        expiryDate = "06/26",
                        cardHolder = "AMANZHOL MUSEKI",
                        status = "ACTIVE",
                        limit = 30000.0,
                        accountNumber = "KZ987654321098765432"
                    ),
                    Card(
                        id = 3,
                        cardNumber = ".....9012",
                        expiryDate = "03/28",
                        cardHolder = "AMANZHOL MUSEKI",
                        status = "BLOCKED",
                        limit = 100000.0,
                        accountNumber = "KZ111222333444555666"
                    )
                )
            } catch (e: Exception) {
                _error.value = "Failed to load cards: ${e.message}"
                Log.e("CardsViewModel", "Error loading cards", e)
            }

            _loading.value = false
        }
    }
}
