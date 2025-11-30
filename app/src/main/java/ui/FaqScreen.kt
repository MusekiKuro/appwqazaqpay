package com.example.qazaqpaybank.ui

import androidx.compose.animation.AnimatedVisibility
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

data class FaqItem(
    val question: String,
    val answer: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(navController: NavHostController) {
    val faqItems = listOf(
        FaqItem(
            "Как перевести деньги?",
            "Перейдите в раздел 'Переводы', выберите карту отправителя, введите номер карты получателя и сумму. Комиссия 0 ₸."
        ),
        FaqItem(
            "Как оплатить счета?",
            "В разделе 'Платежи' выберите категорию (ЖКХ, связь и т.д.), введите лицевой счёт и сумму платежа."
        ),
        FaqItem(
            "Как пользоваться QR-оплатой?",
            "Откройте раздел QR-оплата. Для оплаты отсканируйте QR-код продавца. Для получения денег покажите свой QR-код."
        ),
        FaqItem(
            "Как заблокировать карту?",
            "Перейдите в детали карты через главный экран, нажмите 'Заблокировать карту'. Разблокировать можно там же."
        ),
        FaqItem(
            "Как установить лимит расходов?",
            "В деталях карты нажмите 'Установить лимит' и укажите желаемую сумму ежемесячных расходов."
        ),
        FaqItem(
            "Безопасны ли переводы?",
            "Да, все операции защищены двухфакторной аутентификацией (2FA) и шифрованием данных."
        ),
        FaqItem(
            "Есть ли комиссия за переводы?",
            "Переводы между картами нашего банка бесплатны (0 ₸). На карты других банков комиссия 0.5%."
        ),
        FaqItem(
            "Как связаться с поддержкой?",
            "Телефон: +7 (777) 123-45-67\nEmail: support@qazaqpay.kz\nЧат: доступен с 9:00 до 21:00"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Помощь и FAQ", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад", tint = Color(0xFF1A1A1A))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1A1A1A)
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Text(
                    "Свяжитесь с нами",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SupportButton(
                        icon = Icons.Filled.Phone,
                        label = "Позвонить",
                        color = Color(0xFF34C759),
                        modifier = Modifier.weight(1f),
                        onClick = { }
                    )
                    SupportButton(
                        icon = Icons.Filled.Email,
                        label = "Email",
                        color = Color(0xFF4A90E2),
                        modifier = Modifier.weight(1f),
                        onClick = { }
                    )
                    SupportButton(
                        icon = Icons.Filled.Chat,
                        label = "AI Чат",
                        color = Color(0xFF9B59B6),
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("chat") }
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Text(
                    "Часто задаваемые вопросы",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )

                Spacer(Modifier.height(16.dp))

                faqItems.forEach { item ->
                    FaqItemCard(item)
                    Spacer(Modifier.height(12.dp))
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun SupportButton(
    icon: ImageVector,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier
            .background(color.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = color
        )
    }
}

@Composable
fun FaqItemCard(item: FaqItem) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                item.question,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.weight(1f)
            )
            Icon(
                if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = null,
                tint = Color(0xFF8E8E93)
            )
        }

        AnimatedVisibility(visible = expanded) {
            Column {
                Spacer(Modifier.height(12.dp))
                Text(
                    item.answer,
                    fontSize = 14.sp,
                    color = Color(0xFF8E8E93),
                    lineHeight = 20.sp
                )
            }
        }
    }
}
