package com.example.qazaqpaybank.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.qazaqpaybank.ui.theme.BG
import com.example.qazaqpaybank.ui.theme.CardWhite
import com.example.qazaqpaybank.ui.theme.Navy
import com.example.qazaqpaybank.ui.theme.TealPrimary
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScreen(navController: NavHostController) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showScanDialog by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QR Payment", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BG,
                    titleContentColor = Navy
                )
            )
        },
        bottomBar = { BottomNavBar(navController = navController, currentRoute = "qr") },
        containerColor = BG
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = CardWhite,
                contentColor = TealPrimary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Scan QR") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("My QR") }
                )
            }

            when (selectedTab) {
                0 -> ScanQRTab(
                    onScanClick = { showScanDialog = true }
                )
                1 -> MyQRTab(
                    amount = amount,
                    onAmountChange = { amount = it }
                )
            }
        }

        // Scan Dialog (mock)
        if (showScanDialog) {
            AlertDialog(
                onDismissRequest = { showScanDialog = false },
                icon = { Icon(Icons.Filled.QrCodeScanner, contentDescription = null, tint = TealPrimary) },
                title = { Text("QR Scanner") },
                text = { Text("QR scanning feature will be available in the next version. Camera permission is required.") },
                confirmButton = {
                    Button(
                        onClick = { showScanDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun ScanQRTab(onScanClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Filled.QrCodeScanner,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = TealPrimary
        )

        Spacer(Modifier.height(24.dp))

        Text(
            "Scan QR Code to Pay",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Navy
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "Point your camera at a QR code to make a payment",
            fontSize = 14.sp,
            color = Navy.copy(alpha = 0.7f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onScanClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Filled.CameraAlt, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Open Scanner", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MyQRTab(amount: String, onAmountChange: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "My Payment QR Code",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Navy
        )

        Spacer(Modifier.height(24.dp))

        // Amount input
        OutlinedTextField(
            value = amount,
            onValueChange = onAmountChange,
            label = { Text("Amount to receive") },
            placeholder = { Text("Enter amount") },
            leadingIcon = { Text("$", fontSize = 20.sp, color = TealPrimary) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CardWhite,
                unfocusedContainerColor = CardWhite,
                focusedBorderColor = TealPrimary
            )
        )

        Spacer(Modifier.height(32.dp))

        // QR Code
        val qrData = "QAZAQPAY:CARD:1234567890123456:AMOUNT:${amount.ifEmpty { "0" }}"
        val qrBitmap = remember(qrData) { generateQRCode(qrData) }

        if (qrBitmap != null) {
            Box(
                Modifier
                    .size(280.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Image(
                    bitmap = qrBitmap.asImageBitmap(),
                    contentDescription = "QR Code",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "Show this QR code to receive payment",
            fontSize = 14.sp,
            color = Navy.copy(alpha = 0.7f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        if (amount.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            Text(
                "Amount: $$amount",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TealPrimary
            )
        }
    }
}

fun generateQRCode(data: String, size: Int = 512): Bitmap? {
    return try {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)

        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        bitmap
    } catch (e: Exception) {
        null
    }
}
