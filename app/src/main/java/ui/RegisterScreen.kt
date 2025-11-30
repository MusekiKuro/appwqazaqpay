@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var iin by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState()

    LaunchedEffect(success) {
        if (success) {
            onRegisterSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Регистрация", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onLoginClick) {
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
                .background(Color.White)
                .padding(24.dp)
        ) {
            Text(
                "Создайте аккаунт",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )

            Text(
                "Управляйте финансами в одном приложении",
                fontSize = 15.sp,
                color = Color(0xFF8E8E93),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(Modifier.height(32.dp))

            // Имя
            Text("Имя", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF8E8E93))
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = { Text("Введите имя", color = Color(0xFFBDBDBD)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F9FA),
                    unfocusedContainerColor = Color(0xFFF8F9FA),
                    focusedBorderColor = Color(0xFF4A90E2),
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Фамилия
            Text("Фамилия", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF8E8E93))
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = { Text("Введите фамилию", color = Color(0xFFBDBDBD)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F9FA),
                    unfocusedContainerColor = Color(0xFFF8F9FA),
                    focusedBorderColor = Color(0xFF4A90E2),
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Телефон
            Text("Номер телефона", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF8E8E93))
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = { Text("+7 ___ ___-__-__", color = Color(0xFFBDBDBD)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F9FA),
                    unfocusedContainerColor = Color(0xFFF8F9FA),
                    focusedBorderColor = Color(0xFF4A90E2),
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // ИИН
            Text("ИИН", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF8E8E93))
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = iin,
                onValueChange = { if (it.length <= 12) iin = it },
                placeholder = { Text("12 цифр", color = Color(0xFFBDBDBD)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F9FA),
                    unfocusedContainerColor = Color(0xFFF8F9FA),
                    focusedBorderColor = Color(0xFF4A90E2),
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Email
            Text("Email", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF8E8E93))
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("example@mail.com", color = Color(0xFFBDBDBD)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F9FA),
                    unfocusedContainerColor = Color(0xFFF8F9FA),
                    focusedBorderColor = Color(0xFF4A90E2),
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Пароль
            Text("Пароль", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF8E8E93))
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Минимум 6 символов", color = Color(0xFFBDBDBD)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F9FA),
                    unfocusedContainerColor = Color(0xFFF8F9FA),
                    focusedBorderColor = Color(0xFF4A90E2),
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = Color(0xFF8E8E93)
                        )
                    }
                },
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Подтверждение пароля
            Text("Подтвердите пароль", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF8E8E93))
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Повторите пароль", color = Color(0xFFBDBDBD)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F9FA),
                    unfocusedContainerColor = Color(0xFFF8F9FA),
                    focusedBorderColor = Color(0xFF4A90E2),
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                        Icon(
                            if (isConfirmPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = Color(0xFF8E8E93)
                        )
                    }
                },
                singleLine = true
            )

            if (error != null) {
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFF6B6B).copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Error, contentDescription = null, tint = Color(0xFFFF6B6B))
                    Spacer(Modifier.width(8.dp))
                    Text(error ?: "", color = Color(0xFFFF6B6B), fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    when {
                        firstName.isEmpty() -> viewModel.setError("Введите имя")
                        lastName.isEmpty() -> viewModel.setError("Введите фамилию")
                        phone.isEmpty() -> viewModel.setError("Введите телефон")
                        iin.isEmpty() -> viewModel.setError("Введите ИИН")
                        iin.length != 12 -> viewModel.setError("ИИН должен быть 12 цифр")
                        email.isEmpty() -> viewModel.setError("Введите email")
                        password.isEmpty() -> viewModel.setError("Введите пароль")
                        password.length < 6 -> viewModel.setError("Пароль минимум 6 символов")
                        password != confirmPassword -> viewModel.setError("Пароли не совпадают")
                        else -> viewModel.register(firstName, lastName, phone, email, password, iin)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
                shape = RoundedCornerShape(16.dp),
                enabled = !loading
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Зарегистрироваться", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Уже есть аккаунт? ", color = Color(0xFF8E8E93), fontSize = 14.sp)
                TextButton(onClick = onLoginClick, contentPadding = PaddingValues(0.dp)) {
                    Text("Войти", color = Color(0xFF4A90E2), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
