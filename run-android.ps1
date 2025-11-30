# Скрипт для запуска Android приложения на эмуляторе
# Использование: .\run-android.ps1

Write-Host "🚀 Запуск Android приложения..." -ForegroundColor Green

# Проверка наличия эмулятора
Write-Host "📱 Проверка подключенных устройств..." -ForegroundColor Yellow
$devices = adb devices
if ($devices -match "device$") {
    Write-Host "✅ Устройство найдено!" -ForegroundColor Green
} else {
    Write-Host "❌ Эмулятор не запущен или устройство не подключено!" -ForegroundColor Red
    Write-Host "💡 Запустите эмулятор в Android Studio или через командную строку:" -ForegroundColor Yellow
    Write-Host "   emulator -avd <имя_эмулятора>" -ForegroundColor Cyan
    exit 1
}

# Сборка приложения
Write-Host "🔨 Сборка приложения..." -ForegroundColor Yellow
& .\gradlew.bat assembleDebug
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Ошибка сборки!" -ForegroundColor Red
    exit 1
}

# Установка APK
Write-Host "📦 Установка APK на устройство..." -ForegroundColor Yellow
& .\gradlew.bat installDebug
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Ошибка установки!" -ForegroundColor Red
    exit 1
}

# Запуск приложения
Write-Host "▶️  Запуск приложения..." -ForegroundColor Yellow
adb shell am start -n com.example.qazaqpaybank/.MainActivity

Write-Host "✅ Приложение запущено!" -ForegroundColor Green
Write-Host "📊 Просмотр логов: adb logcat" -ForegroundColor Cyan

