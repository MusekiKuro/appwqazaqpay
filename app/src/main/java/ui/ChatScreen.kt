package com.example.qazaqpaybank.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavHostController) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(
            listOf(
                ChatMessage(
                    "Здравствуйте! Я AI-помощник QazaqPay 🤖\n\nЗадайте любой вопрос о наших услугах!",
                    false,
                    "13:00"
                )
            )
        )
    }
    var isLoading by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    fun sendMessage() {
        if (messageText.isNotEmpty() && !isLoading) {
            val userMessage = messageText
            messages = messages + ChatMessage(
                userMessage,
                true,
                "13:${10 + messages.size}"
            )
            messageText = ""
            isLoading = true
            keyboardController?.hide()

            coroutineScope.launch {
                listState.animateScrollToItem(messages.size - 1)
                delay(1500) // Имитация набора текста

                val aiResponse = getSmartResponse(userMessage)

                messages = messages + ChatMessage(
                    aiResponse,
                    false,
                    "13:${10 + messages.size}"
                )

                isLoading = false
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .size(40.dp)
                                .background(Color(0xFF4A90E2), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.SmartToy,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("AI Помощник", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(
                                if (isLoading) "Печатает..." else "Онлайн",
                                fontSize = 12.sp,
                                color = if (isLoading) Color(0xFFFFBE0B) else Color(0xFF34C759)
                            )
                        }
                    }
                },
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
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(messages) { message ->
                    ChatMessageBubble(message)
                }

                if (isLoading) {
                    item {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                Modifier
                                    .size(32.dp)
                                    .background(Color(0xFF4A90E2), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.SmartToy,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                            Box(
                                Modifier
                                    .background(Color.White, RoundedCornerShape(16.dp))
                                    .padding(12.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color(0xFF4A90E2),
                                    strokeWidth = 2.dp
                                )
                            }
                        }
                    }
                }
            }

            if (messages.size <= 2) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        "Популярные вопросы:",
                        fontSize = 13.sp,
                        color = Color(0xFF8E8E93),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuickReplyButton("Как сделать перевод?") {
                            messageText = "Как сделать перевод?"
                            sendMessage()
                        }
                        QuickReplyButton("Условия кредита") {
                            messageText = "Какие условия кредита?"
                            sendMessage()
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuickReplyButton("Комиссии") {
                            messageText = "Какие комиссии?"
                            sendMessage()
                        }
                        QuickReplyButton("Безопасность") {
                            messageText = "Насколько это безопасно?"
                            sendMessage()
                        }
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Напишите сообщение...", color = Color(0xFFBDBDBD), fontSize = 15.sp) },
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF8F9FA),
                        unfocusedContainerColor = Color(0xFFF8F9FA),
                        focusedBorderColor = Color(0xFF4A90E2),
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 3,
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = { sendMessage() }
                    )
                )

                Spacer(Modifier.width(8.dp))

                IconButton(
                    onClick = { sendMessage() },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFF4A90E2), CircleShape),
                    enabled = messageText.isNotEmpty() && !isLoading
                ) {
                    Icon(
                        Icons.Filled.Send,
                        contentDescription = "Отправить",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

fun getSmartResponse(userMessage: String): String {
    val msg = userMessage.lowercase()

    return when {
        msg.contains("привет") || msg.contains("здравств") ->
            "Здравствуйте! 👋 Рад помочь вам с вопросами о QazaqPay. Чем могу быть полезен?"

        msg.contains("перевод") ->
            "💸 **Переводы в QazaqPay:**\n\n• Между своими счетами - бесплатно\n• На карты QazaqPay - 0₸\n• На карты других банков - 0.5%\n\nОткройте раздел 'Переводы' в меню для отправки денег!"

        msg.contains("оплат") || msg.contains("счет") || msg.contains("жкх") ->
            "📄 **Оплата счетов:**\n\nДоступны категории:\n• ЖКХ (свет, вода, газ)\n• Связь (мобильная)\n• Интернет\n• ТВ\n• Штрафы\n\nПерейдите в 'Платежи' → выберите категорию → введите лицевой счёт!"

        msg.contains("кредит") || msg.contains("займ") ->
            "💰 **Кредиты в QazaqPay:**\n\n• Сумма: до 5 000 000 ₸\n• Ставка: от 12% годовых\n• Срок: до 60 месяцев\n• Решение за 24 часа\n\nПодайте заявку в 'Сервисы' → 'Кредиты'!"

        msg.contains("карт") || msg.contains("блокир") ->
            "💳 **Управление картами:**\n\n• Блокировка/разблокировка\n• Установка лимитов расходов\n• Просмотр выписок\n\nНажмите на карточку на главном экране для управления!"

        msg.contains("qr") || msg.contains("кр") || msg.contains("оплата") && msg.contains("код") ->
            "📱 **QR-оплата:**\n\n• Для оплаты: отсканируйте QR-код продавца\n• Для получения: покажите свой QR\n\nОткройте 'QR-оплата' в меню!"

        msg.contains("комисс") || msg.contains("плат") && msg.contains("процент") ->
            "💵 **Наши комиссии:**\n\n✅ Переводы внутри QazaqPay: 0₸\n✅ Оплата счетов: 0₸\n✅ QR-платежи: 0₸\n⚠️ На другие банки: 0.5%"

        msg.contains("безопас") || msg.contains("защит") ->
            "🔐 **Безопасность:**\n\n✅ Двухфакторная аутентификация (2FA)\n✅ Шифрование данных 256-bit\n✅ Мониторинг транзакций 24/7\n✅ Биометрия (отпечаток/Face ID)\n\nВаши деньги надёжно защищены!"

        msg.contains("контакт") || msg.contains("поддерж") || msg.contains("связ") ->
            "📞 **Контакты поддержки:**\n\n• Телефон: +7 (777) 123-45-67\n• Email: support@qazaqpay.kz\n• Чат: 24/7 (вы здесь!)\n\nРаботаем круглосуточно для вас! 🕐"

        msg.contains("спасибо") || msg.contains("благодар") ->
            "Пожалуйста! 😊 Рад был помочь. Если появятся ещё вопросы - обращайтесь!"

        msg.contains("инвести") || msg.contains("вклад") ->
            "📈 **Инвестиции:**\n\n• Депозиты от 10% годовых\n• Инвестиционные продукты\n• Управление портфелем\n\nОткройте 'Инвестиции' для подробностей!"

        msg.contains("время") || msg.contains("график") ->
            "🕐 **Режим работы:**\n\nМобильное приложение: 24/7\nОтделения: Пн-Пт 9:00-18:00\nПоддержка: круглосуточно\n\nМы всегда на связи!"

        else ->
            "Спасибо за вопрос! 🤔\n\nВы можете:\n• Выбрать популярный вопрос выше\n• Позвонить: +7 (777) 123-45-67\n• Написать: support@qazaqpay.kz\n\nЯ постараюсь лучше понять ваш вопрос, если переформулируете!"
    }
}

@Composable
fun ChatMessageBubble(message: ChatMessage) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isUser) {
            Box(
                Modifier
                    .size(32.dp)
                    .background(Color(0xFF4A90E2), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.SmartToy,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier.widthIn(max = 280.dp),
            horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
        ) {
            Box(
                Modifier
                    .background(
                        if (message.isUser) Color(0xFF4A90E2) else Color.White,
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (message.isUser) 16.dp else 4.dp,
                            bottomEnd = if (message.isUser) 4.dp else 16.dp
                        )
                    )
                    .padding(12.dp)
            ) {
                Text(
                    message.text,
                    fontSize = 15.sp,
                    color = if (message.isUser) Color.White else Color(0xFF1A1A1A),
                    lineHeight = 20.sp
                )
            }

            Text(
                message.timestamp,
                fontSize = 11.sp,
                color = Color(0xFF8E8E93),
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
            )
        }

        if (message.isUser) {
            Spacer(Modifier.width(8.dp))
            Box(
                Modifier
                    .size(32.dp)
                    .background(Color(0xFF34C759), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "АМ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun QuickReplyButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF4A90E2)
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF4A90E2))
        ),
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text, fontSize = 13.sp)
    }
}
