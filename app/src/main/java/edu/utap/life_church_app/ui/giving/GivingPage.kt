package edu.utap.life_church_app.ui.giving

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import edu.utap.life_church_app.BuildConfig
import edu.utap.life_church_app.ui.giving.payment.GooglePayJsonFactory
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GivingPage(viewModel: GivingViewModel = viewModel()) {
    var amount by remember { mutableStateOf("0") }
    var recurringEnabled by remember { mutableStateOf(false) }
    var frequency by remember { mutableStateOf("Monthly") }
    var location by remember { mutableStateOf("Austin") }
    var category by remember { mutableStateOf("Tithe") }
    var showKeyboard by remember { mutableStateOf(true) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showPaymentMethod by remember { mutableStateOf(false) }
    var showFundPicker by remember { mutableStateOf(false) }
    var showLocationPicker by remember { mutableStateOf(false) }
    var showFrequencyPicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().time) }
    var processDateLabel by remember { mutableStateOf("Today") }
    var isGooglePayReady by remember { mutableStateOf(false) }
    var gmsCheckAttempt by remember { mutableStateOf(0) }
    val context = LocalContext.current
    var hasLocationPermission by remember { mutableStateOf(hasAnyLocationPermission(context)) }
    var userCoordinates by remember { mutableStateOf(UT_AUSTIN_SPEEDWAY_COORDS) }
    val activity = context as? Activity
    val submitUiState by viewModel.submitUiState.collectAsState()
    val paymentsClient = remember(context) {
        Wallet.getPaymentsClient(
            context,
            Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                .build()
        )
    }
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF1A1A1A) else MaterialTheme.colorScheme.background
    val surfaceColor = if (isDarkTheme) Color(0xFF2A2A2A) else MaterialTheme.colorScheme.surface
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor = if (isDarkTheme) Color(0xFF9CA3AF) else MaterialTheme.colorScheme.onSurfaceVariant
    val hasValidAmount = (amount.toFloatOrNull() ?: 0f) >= 1f
    val hiddenKeyboardButtonContainer = if (hasValidAmount) Color.White else Color.Black
    val hiddenKeyboardButtonContent = if (hasValidAmount) Color.Black else Color.White
    val visibleKeyboardGiveButtonContainer = Color(0xFFA3A3A3)
    val visibleKeyboardGiveButtonContent = Color.Black
    val lightGrayControlContainer = Color(0xFFE5E7EB)
    val lightGrayControlContent = Color.Black

    val googlePayLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val paymentToken = if (data != null) {
                extractPaymentToken(PaymentData.getFromIntent(data))
            } else {
                null
            }
            if (paymentToken.isNullOrBlank()) {
                viewModel.reportError("Google Pay token was empty. Please try again.")
            } else {
                viewModel.scheduleGift(
                    amount = amount,
                    location = location,
                    frequency = if (recurringEnabled) frequency else null,
                    processDateLabel = processDateLabel,
                    paymentToken = paymentToken
                )
            }
        } else {
            viewModel.reportError("Google Pay was cancelled.")
        }
    }

    LaunchedEffect(paymentsClient, gmsCheckAttempt) {
        try {
            val request = IsReadyToPayRequest.fromJson(GooglePayJsonFactory.isReadyToPayRequest().toString())
            paymentsClient.isReadyToPay(request).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isGooglePayReady = task.result == true
                } else {
                    isGooglePayReady = false
                    val error = task.exception
                    if (error is android.os.DeadObjectException || error?.cause is android.os.DeadObjectException) {
                        viewModel.reportError("Google Play Services encountered a system error. Please try again or check device storage.")
                    }
                }
            }
        } catch (e: Exception) {
            isGooglePayReady = false
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions.values.any { it } || hasAnyLocationPermission(context)
    }

    val funds = remember { givingFunds() }
    val frequencies = remember { listOf("Weekly", "Every Two Weeks", "Twice Monthly (1st & 15th)", "Monthly") }
    val locationCatalog = remember { givingLocationsCatalog() }
    val onlineLocation = remember { givingOnlineLocation() }
    val physicalLocations = remember(locationCatalog, userCoordinates) {
        locationCatalog
            .map { base ->
                val miles = distanceMiles(
                    userLat = userCoordinates.latitude,
                    userLon = userCoordinates.longitude,
                    targetLat = base.latitude,
                    targetLon = base.longitude
                )
                base.toUiLocation(formatMiles(miles))
            }
            .sortedBy { it.distanceMiles }
    }
    val suggestedLocation = physicalLocations.firstOrNull()

    LaunchedEffect(hasLocationPermission, showLocationPicker) {
        if (showLocationPicker) {
            if (!hasLocationPermission) {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            } else {
                resolveCurrentLocation(context)?.let { userCoordinates = it }
            }
        }
    }

    LaunchedEffect(suggestedLocation?.name) {
        if (location.isBlank() && suggestedLocation != null) {
            location = suggestedLocation.name
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.clickable { showLocationPicker = true },
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(location, color = onBackgroundColor, fontWeight = FontWeight.SemiBold)
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = onBackgroundColor)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Dashboard, contentDescription = null, tint = onBackgroundColor)
                    }
                }
            }
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFB4532A))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                "One Day",
                                color = Color.White,
                                style = MaterialTheme.typography.displaySmall,
                                fontStyle = FontStyle.Italic
                            )
                            Text(
                                "Give one day's income to Local & Global Missions on April 26.",
                                color = Color.White
                            )
                            Text(
                                "Learn More ->",
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.wrapContentWidth(Alignment.End),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                            shape = RoundedCornerShape(999.dp)
                        ) {
                            Text("Calculate")
                        }
                    }
                }
            }
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showKeyboard = true }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Text(
                                "$",
                                color = if (showKeyboard) Color(0xFF40D9EA) else onSurfaceColor,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                amount,
                                color = if (showKeyboard) Color(0xFF40D9EA) else secondaryTextColor,
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier.clickable { showFundPicker = true },
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(category, color = onSurfaceColor, fontWeight = FontWeight.SemiBold)
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = onSurfaceColor)
                        }
                    }
                }
            }
            item {
                SettingCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Color(0xFF40D9EA))
                            Text("Make Gift Recurring", color = onSurfaceColor, fontWeight = FontWeight.SemiBold)
                        }
                        Switch(checked = recurringEnabled, onCheckedChange = { recurringEnabled = it })
                    }
                }
            }
            if (recurringEnabled) {
                item {
                    SelectRow(title = "Frequency", value = frequency, onClick = { showFrequencyPicker = true })
                }
            }
            item {
                SelectRow(title = "Process Date", value = processDateLabel, onClick = { showDatePicker = true })
            }
            item {
                SelectRow(
                    title = "",
                    value = "",
                    onClick = { showPaymentMethod = true },
                    leading = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "G",
                                color = onSurfaceColor,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text("Google Pay", color = onSurfaceColor, fontWeight = FontWeight.SemiBold)
                        }
                    }
                )
            }
            item { Spacer(Modifier.height(220.dp)) }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(backgroundColor)
                .navigationBarsPadding()
                .imePadding()
        ) {
            if (!showKeyboard) {
                Button(
                    onClick = { showKeyboard = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = hiddenKeyboardButtonContainer,
                        contentColor = hiddenKeyboardButtonContent
                    ),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(scheduleButtonText(amount), fontWeight = FontWeight.SemiBold)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(surfaceColor)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                viewModel.clearMessages()
                                val parsedAmount = amount.toFloatOrNull() ?: 0f
                                if (parsedAmount < 1f) {
                                    viewModel.reportError("Enter at least $1.00 to schedule a gift.")
                                    return@Button
                                }
                                if (!isGooglePayReady) {
                                    if (BuildConfig.ALLOW_MOCK_GOOGLE_PAY) {
                                        // Debug fallback for non-Play emulators.
                                        viewModel.scheduleGift(
                                            amount = amount,
                                            location = location,
                                            frequency = if (recurringEnabled) frequency else null,
                                            processDateLabel = processDateLabel,
                                            paymentToken = "mock_token_debug"
                                        )
                                    } else {
                                        viewModel.reportError("Google Pay is not available on this device.")
                                    }
                                    return@Button
                                }
                                if (activity == null) {
                                    viewModel.reportError("Unable to launch Google Pay from this context.")
                                    return@Button
                                }
                                val paymentDataRequestJson = GooglePayJsonFactory.paymentDataRequest(
                                    price = String.format(Locale.US, "%.2f", parsedAmount)
                                )
                                val paymentDataRequest = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())
                                paymentsClient.loadPaymentData(paymentDataRequest)
                                    .addOnSuccessListener { paymentData ->
                                        val token = extractPaymentToken(paymentData)
                                        if (token.isNullOrBlank()) {
                                            viewModel.reportError("Google Pay token was empty. Please try again.")
                                        } else {
                                            viewModel.scheduleGift(
                                                amount = amount,
                                                location = location,
                                                frequency = if (recurringEnabled) frequency else null,
                                                processDateLabel = processDateLabel,
                                                paymentToken = token
                                            )
                                        }
                                    }
                                    .addOnFailureListener { throwable ->
                                        if (throwable is ResolvableApiException) {
                                            val request = IntentSenderRequest.Builder(throwable.resolution).build()
                                            googlePayLauncher.launch(request)
                                        } else {
                                            val message = when {
                                                throwable is android.os.DeadObjectException || throwable.cause is android.os.DeadObjectException ->
                                                    "System connection failed (DeadObject). Please restart the app or check device space."
                                                throwable.message?.contains("No space left", ignoreCase = true) == true ->
                                                    "Insufficient device storage to complete the payment request."
                                                else -> "Google Pay failed: ${throwable.message ?: "unknown"}"
                                            }
                                            viewModel.reportError(message)
                                            // Trigger a re-check of GMS availability
                                            gmsCheckAttempt++
                                        }
                                    }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = visibleKeyboardGiveButtonContainer,
                                contentColor = visibleKeyboardGiveButtonContent
                            ),
                            shape = RoundedCornerShape(999.dp)
                        ) {
                            if (submitUiState.isSubmitting) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp,
                                    color = visibleKeyboardGiveButtonContent
                                )
                            } else {
                                Text(scheduleButtonText(amount), fontWeight = FontWeight.SemiBold)
                            }
                        }
                        IconButton(
                            onClick = { showKeyboard = false },
                            modifier = Modifier
                                .size(52.dp)
                                .background(lightGrayControlContainer, CircleShape)
                        ) {
                            Icon(Icons.Default.Keyboard, contentDescription = null, tint = lightGrayControlContent)
                        }
                    }
                    KeypadRow(
                        keys = listOf("1", "2", "3"),
                        containerColor = lightGrayControlContainer,
                        contentColor = lightGrayControlContent
                    ) { amount = appendDigit(amount, it) }
                    KeypadRow(
                        keys = listOf("4", "5", "6"),
                        containerColor = lightGrayControlContainer,
                        contentColor = lightGrayControlContent
                    ) { amount = appendDigit(amount, it) }
                    KeypadRow(
                        keys = listOf("7", "8", "9"),
                        containerColor = lightGrayControlContainer,
                        contentColor = lightGrayControlContent
                    ) { amount = appendDigit(amount, it) }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        KeypadButton(".", Modifier.weight(1f), lightGrayControlContainer, lightGrayControlContent) {
                            amount = appendDecimal(amount)
                        }
                        KeypadButton("0", Modifier.weight(1f), lightGrayControlContainer, lightGrayControlContent) {
                            amount = appendDigit(amount, "0")
                        }
                        KeypadButton("⌫", Modifier.weight(1f), lightGrayControlContainer, lightGrayControlContent) {
                            amount = deleteDigit(amount)
                        }
                    }
                }
                submitUiState.errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = Color(0xFFDC2626),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
                submitUiState.successMessage?.let { message ->
                    Text(
                        text = message,
                        color = Color(0xFF15803D),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            currentDate = selectedDate,
            onDismiss = { showDatePicker = false },
            onDone = { date ->
                selectedDate = date
                processDateLabel = formatDisplayDate(date)
                showDatePicker = false
            }
        )
    }
    if (showFundPicker) {
        SelectSheet(title = "Fund", onDismiss = { showFundPicker = false }, dark = true) {
            items(funds) { fund ->
                FundItem(fund = fund, selected = fund.name == category, onClick = { category = fund.name })
            }
        }
    }
    if (showLocationPicker) {
        SelectSheet(title = "Location", onDismiss = { showLocationPicker = false }) {
            if (suggestedLocation != null) {
                item { LocationSectionHeader("SUGGESTED") }
                item {
                    LocationItem(suggestedLocation, selected = suggestedLocation.name == location) {
                        location = suggestedLocation.name
                        showLocationPicker = false
                    }
                }
            }
            item { LocationSectionHeader("GLOBAL") }
            item {
                LocationItem(onlineLocation, selected = onlineLocation.name == location) {
                    location = onlineLocation.name
                    showLocationPicker = false
                }
            }
            item { LocationSectionHeader("ALL LOCATIONS (SORTED BY DISTANCE)") }
            items(physicalLocations) { loc ->
                LocationItem(loc, selected = loc.name == location) {
                    location = loc.name
                    showLocationPicker = false
                }
            }
        }
    }
    if (showFrequencyPicker) {
        SelectSheet(title = "Frequency", onDismiss = { showFrequencyPicker = false }) {
            items(frequencies) { option ->
                SimpleSelectItem(label = option, selected = option == frequency, onClick = { frequency = option })
            }
        }
    }
    if (showPaymentMethod) {
        SelectSheet(title = "Payment Method", onDismiss = { showPaymentMethod = false }) {
            item { SimpleSelectItem("Google Pay", selected = true, onClick = {}) }
            item { SimpleSelectItem("Add New Payment Method", selected = false, onClick = {}) }
        }
    }
}

@Composable
private fun SettingCard(content: @Composable () -> Unit) {
    val cardColor = if (isSystemInDarkTheme()) Color(0xFF2A2A2A) else MaterialTheme.colorScheme.surface
    Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = cardColor)) {
        Column(Modifier.padding(14.dp)) { content() }
    }
}

@Composable
private fun SelectRow(
    title: String,
    value: String,
    onClick: () -> Unit,
    leading: @Composable (() -> Unit)? = null
) {
    val isDarkTheme = isSystemInDarkTheme()
    val titleColor = if (isDarkTheme) Color(0xFF9CA3AF) else MaterialTheme.colorScheme.onSurfaceVariant
    val valueColor = MaterialTheme.colorScheme.onSurface
    val iconColor = if (isDarkTheme) Color(0xFF9CA3AF) else MaterialTheme.colorScheme.onSurfaceVariant
    SettingCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                if (leading != null) leading()
                if (title.isNotBlank()) {
                    Text(title, color = titleColor, style = MaterialTheme.typography.bodySmall)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                if (value.isNotBlank()) {
                    Text(value, color = valueColor, fontWeight = FontWeight.SemiBold)
                }
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = iconColor)
            }
        }
    }
}

@Composable
private fun KeypadRow(
    keys: List<String>,
    containerColor: Color,
    contentColor: Color,
    onKey: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        keys.forEach { key ->
            KeypadButton(key, Modifier.weight(1f), containerColor, contentColor) { onKey(key) }
        }
    }
}

@Composable
private fun KeypadButton(
    label: String,
    modifier: Modifier,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(62.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = contentColor)
    ) {
        Text(label, style = MaterialTheme.typography.titleLarge)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectSheet(
    title: String,
    onDismiss: () -> Unit,
    dark: Boolean = false,
    content: LazyListScope.() -> Unit
) {
    val useDarkSheet = dark && isSystemInDarkTheme()
    val containerColor = if (useDarkSheet) Color(0xFF2A2A2A) else MaterialTheme.colorScheme.surface
    val onContainerColor = if (useDarkSheet) Color.White else MaterialTheme.colorScheme.onSurface
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = containerColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDismiss) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = onContainerColor
                )
            }
            Text(title, color = onContainerColor, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.width(48.dp))
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun SimpleSelectItem(label: String, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, fontWeight = FontWeight.Medium)
            if (selected) Icon(Icons.Default.Check, contentDescription = null)
        }
    }
}

@Composable
private fun FundItem(fund: GivingFund, selected: Boolean, onClick: () -> Unit) {
    val isDarkTheme = isSystemInDarkTheme()
    val containerColor = if (isDarkTheme) Color(0xFF3A3A3A) else MaterialTheme.colorScheme.surface
    val titleColor = MaterialTheme.colorScheme.onSurface
    val descriptionColor = if (isDarkTheme) Color(0xFF9CA3AF) else MaterialTheme.colorScheme.onSurfaceVariant
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(fund.icon, style = MaterialTheme.typography.headlineSmall)
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(fund.name, color = titleColor, fontWeight = FontWeight.SemiBold)
                Text(fund.description, color = descriptionColor, style = MaterialTheme.typography.bodySmall)
            }
            if (selected) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF40D9EA))
            }
        }
    }
}

@Composable
private fun LocationItem(location: GivingLocation, selected: Boolean, onClick: () -> Unit) {
    val isDarkTheme = isSystemInDarkTheme()
    val containerColor = if (isDarkTheme) Color(0xFF3A3A3A) else Color.White
    val titleColor = if (isDarkTheme) Color.White else Color.Black
    val subtitleColor = if (isDarkTheme) Color(0xFF9CA3AF) else Color.Gray
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(location.name, color = titleColor, fontWeight = FontWeight.SemiBold)
                Text(location.addressLine1, color = subtitleColor, style = MaterialTheme.typography.bodySmall)
                if (location.addressLine2.isNotBlank()) {
                    Text(location.addressLine2, color = subtitleColor, style = MaterialTheme.typography.bodySmall)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(location.distanceLabel, color = subtitleColor)
                if (selected) Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF40D9EA))
            }
        }
    }
}

@Composable
private fun LocationSectionHeader(title: String) {
    Text(
        text = title,
        color = Color.Gray,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun DatePickerDialog(
    currentDate: Date,
    onDismiss: () -> Unit,
    onDone: (Date) -> Unit
) {
    var shownMonth by remember { mutableStateOf(monthStart(currentDate)) }
    var chosenDate by remember { mutableStateOf(currentDate) }
    val monthLabel = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(shownMonth)
    val days = buildMonthGrid(shownMonth)

    Dialog(onDismissRequest = onDismiss) {
        val isDarkTheme = isSystemInDarkTheme()
        val dialogContainerColor = if (isDarkTheme) Color(0xFF2A2A2A) else MaterialTheme.colorScheme.surface
        val onDialogColor = MaterialTheme.colorScheme.onSurface
        val mutedTextColor = if (isDarkTheme) Color(0xFFD1D5DB) else MaterialTheme.colorScheme.onSurfaceVariant
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = dialogContainerColor)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = onDialogColor)
                    }
                    Text("Process Date", color = onDialogColor, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.width(48.dp))
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(monthLabel, color = Color(0xFF40D9EA), fontWeight = FontWeight.Bold)
                    Row {
                        IconButton(onClick = { shownMonth = addMonths(shownMonth, -1) }) {
                            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null, tint = Color.Gray)
                        }
                        IconButton(onClick = { shownMonth = addMonths(shownMonth, 1) }) {
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF40D9EA))
                        }
                    }
                }
                val weekdays = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    weekdays.forEach { Text(it, color = Color.Gray, style = MaterialTheme.typography.labelSmall) }
                }
                days.chunked(7).forEach { week ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        week.forEach { day ->
                            if (day == null) {
                                Spacer(Modifier.size(40.dp))
                            } else {
                                val selected = isSameDay(day, chosenDate)
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(if (selected) Color(0xFF40D9EA) else Color.Transparent, CircleShape)
                                        .clickable { chosenDate = day },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        dayOfMonth(day).toString(),
                                        color = if (selected) Color.White else mutedTextColor
                                    )
                                }
                            }
                        }
                    }
                }
                Text(
                    "Process Date: ${formatDisplayDate(chosenDate)}",
                    color = onDialogColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Button(
                    onClick = { onDone(chosenDate) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text("Done")
                }
            }
        }
    }
}

private fun appendDigit(current: String, digit: String): String {
    return if (current == "0") digit else current + digit
}

private fun deleteDigit(current: String): String {
    if (current.length <= 1) return "0"
    return current.dropLast(1)
}

private fun appendDecimal(current: String): String {
    return if (current.contains('.')) current else "$current."
}

private fun scheduleButtonText(amount: String): String {
    return if ((amount.toFloatOrNull() ?: 0f) >= 1f) "Schedule $$amount Gift" else "To Give, Enter Amount"
}

private fun formatDisplayDate(date: Date): String {
    val today = Calendar.getInstance()
    val tomorrow = Calendar.getInstance().apply { add(Calendar.DATE, 1) }
    return when {
        isSameDay(date, today.time) -> "Today"
        isSameDay(date, tomorrow.time) -> "Tomorrow"
        else -> SimpleDateFormat("MMM d", Locale.getDefault()).format(date)
    }
}

private fun monthStart(date: Date): Date {
    return Calendar.getInstance().apply {
        time = date
        set(Calendar.DAY_OF_MONTH, 1)
    }.time
}

private fun addMonths(date: Date, months: Int): Date {
    return Calendar.getInstance().apply {
        time = date
        add(Calendar.MONTH, months)
        set(Calendar.DAY_OF_MONTH, 1)
    }.time
}

private fun buildMonthGrid(date: Date): List<Date?> {
    val cal = Calendar.getInstance().apply {
        time = date
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val firstWeekday = cal.get(Calendar.DAY_OF_WEEK) - 1
    val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    val values = MutableList<Date?>(firstWeekday) { null }
    for (day in 1..daysInMonth) {
        val dayCal = Calendar.getInstance().apply {
            time = date
            set(Calendar.DAY_OF_MONTH, day)
        }
        values.add(dayCal.time)
    }
    while (values.size % 7 != 0) values.add(null)
    return values
}

private fun dayOfMonth(date: Date): Int = Calendar.getInstance().apply { time = date }.get(Calendar.DAY_OF_MONTH)

private fun isSameDay(a: Date, b: Date): Boolean {
    val calA = Calendar.getInstance().apply { time = a }
    val calB = Calendar.getInstance().apply { time = b }
    return calA.get(Calendar.YEAR) == calB.get(Calendar.YEAR) &&
        calA.get(Calendar.DAY_OF_YEAR) == calB.get(Calendar.DAY_OF_YEAR)
}

private fun extractPaymentToken(paymentData: PaymentData?): String? {
    val paymentJson = paymentData?.toJson() ?: return null
    return runCatching {
        JSONObject(paymentJson)
            .getJSONObject("paymentMethodData")
            .getJSONObject("tokenizationData")
            .getString("token")
    }.getOrNull()
}

private fun givingFunds(): List<GivingFund> = listOf(
    GivingFund(
        "Tithe",
        "We encourage you to give the first 10% of your income back to Him as a step of obedience.",
        "\uD83D\uDCB0"
    ),
    GivingFund("New Locations", "Fuel the effort to launch new church locations.", "\uD83C\uDFE2"),
    GivingFund("Local & Global Missions", "Provide relief to people in crisis worldwide.", "\uD83C\uDF0D"),
    GivingFund("NextGen", "Invest in the next generation.", "\uD83D\uDC76"),
    GivingFund("YouVersion", "Accelerate efforts to share the Bible everywhere.", "\uD83D\uDCF1"),
    GivingFund("Free Resources", "Equip churches and leaders with free resources.", "\uD83D\uDCDA")
)

private data class GivingFund(
    val name: String,
    val description: String,
    val icon: String
)

private data class GivingLocation(
    val name: String,
    val addressLine1: String,
    val addressLine2: String,
    val distanceLabel: String,
    val distanceMiles: Double
)

private data class CampusLocation(
    val name: String,
    val addressLine1: String,
    val addressLine2: String,
    val latitude: Double,
    val longitude: Double
)

private data class LatLng(
    val latitude: Double,
    val longitude: Double
)

private fun CampusLocation.toUiLocation(distanceLabel: String): GivingLocation {
    return GivingLocation(
        name = name,
        addressLine1 = addressLine1,
        addressLine2 = addressLine2,
        distanceLabel = distanceLabel,
        distanceMiles = distanceLabel.removeSuffix("mi").toDoubleOrNull() ?: Double.MAX_VALUE
    )
}

private fun hasAnyLocationPermission(context: Context): Boolean {
    val hasFine = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val hasCoarse = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    return hasFine || hasCoarse
}

private fun resolveCurrentLocation(context: Context): LatLng? {
    if (!hasAnyLocationPermission(context)) return null
    val manager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager ?: return null
    val candidates = listOf(
        LocationManager.GPS_PROVIDER,
        LocationManager.NETWORK_PROVIDER,
        LocationManager.PASSIVE_PROVIDER
    )
    val best = candidates
        .mapNotNull { provider ->
            runCatching { manager.getLastKnownLocation(provider) }.getOrNull()
        }
        .maxByOrNull { it.time }
    return best?.let { LatLng(it.latitude, it.longitude) }
}

private fun distanceMiles(userLat: Double, userLon: Double, targetLat: Double, targetLon: Double): Double {
    val results = FloatArray(1)
    Location.distanceBetween(userLat, userLon, targetLat, targetLon, results)
    return results[0] / 1609.344
}

private fun formatMiles(miles: Double): String {
    val roundedTenth = ((miles * 10.0).roundToInt()) / 10.0
    return String.format(Locale.US, "%.1fmi", roundedTenth)
}

private fun givingOnlineLocation(): GivingLocation {
    return GivingLocation(
        name = "Life.Church Online",
        addressLine1 = "Life.Church Online meets anywhere you are with more than 130 services each week.",
        addressLine2 = "",
        distanceLabel = "369.9mi",
        distanceMiles = Double.MAX_VALUE
    )
}

private val UT_AUSTIN_SPEEDWAY_COORDS = LatLng(30.2862, -97.7394)

private fun givingLocationsCatalog(): List<CampusLocation> = listOf(
    CampusLocation("Austin", "13609 N Interstate Hwy 35", "Austin, TX 78753", 30.4437, -97.6683),
    CampusLocation("Fort Worth", "7800 N Beach St", "Fort Worth, TX", 32.8722, -97.2898),
    CampusLocation("Mansfield", "1501 Hwy 287 N", "Mansfield, TX", 32.5843, -97.1403),
    CampusLocation("Keller", "201 Mount Gilead Rd", "Keller, TX", 32.9390, -97.2260),
    CampusLocation("McKinney", "2045 W University Dr", "McKinney, TX", 33.2205, -96.6402),
    CampusLocation("Amarillo", "1501 S Coulter St", "Amarillo, TX", 35.1971, -101.9207),
    CampusLocation("Norman", "2001 24th Ave NW", "Norman, OK", 35.2450, -97.4763),
    CampusLocation("Moore", "2001 NW 12th St", "Moore, OK", 35.3526, -97.5041),
    CampusLocation("Mustang", "460 E State Hwy 152", "Mustang, OK", 35.3910, -97.7217),
    CampusLocation("South Oklahoma City", "2100 SW 119th St", "Oklahoma City, OK", 35.3461, -97.5567),
    CampusLocation("Northwest Oklahoma City", "2001 NW 178th St", "Edmond, OK", 35.6494, -97.5483),
    CampusLocation("Broadway & Britton (OKC)", "1001 W Britton Rd", "Oklahoma City, OK", 35.5662, -97.5331),
    CampusLocation("Midwest City", "901 N Douglas Blvd", "Midwest City, OK", 35.4728, -97.3977),
    CampusLocation("Yukon", "6300 NW Expressway", "Yukon, OK", 35.5513, -97.7524),
    CampusLocation("Shawnee", "5113 N Harrison St", "Shawnee, OK", 35.3800, -96.9001),
    CampusLocation("Edmond", "4600 E 2nd St", "Edmond, OK", 35.6540, -97.4160),
    CampusLocation("Stillwater", "1917 N Country Club Rd", "Stillwater, OK", 36.1436, -97.0568),
    CampusLocation("South Tulsa", "8200 S Lewis Ave", "Tulsa, OK", 36.0435, -95.9584),
    CampusLocation("Midtown Tulsa", "2000 E 15th St", "Tulsa, OK", 36.1418, -95.9640),
    CampusLocation("Broken Arrow", "2420 E Kenosha St", "Broken Arrow, OK", 36.0218, -95.7617),
    CampusLocation("South Broken Arrow", "1400 W Washington St", "Broken Arrow, OK", 36.0528, -95.8127),
    CampusLocation("Jenks", "100 W Main St", "Jenks, OK", 36.0229, -95.9683),
    CampusLocation("Catoosa", "19303 E Admiral Pl", "Catoosa, OK", 36.1882, -95.7637),
    CampusLocation("Owasso", "8513 N 129th E Ave", "Owasso, OK", 36.2788, -95.8293),
    CampusLocation("Fort Smith", "8600 Rogers Ave", "Fort Smith, AR", 35.3618, -94.3416),
    CampusLocation("Rogers", "2220 S Promenade Blvd", "Rogers, AR", 36.3081, -94.1722),
    CampusLocation("Derby", "300 N Rock Rd", "Derby, KS", 37.5483, -97.2449),
    CampusLocation("East Wichita", "3210 N Maize Rd", "Wichita, KS", 37.7427, -97.4622),
    CampusLocation("West Wichita", "2233 N Ridge Rd", "Wichita, KS", 37.7250, -97.4284),
    CampusLocation("Overland Park", "14300 Metcalf Ave", "Overland Park, KS", 38.8730, -94.6694),
    CampusLocation("Lenexa", "9130 Renner Blvd", "Lenexa, KS", 38.9628, -94.7757),
    CampusLocation("Kansas City (East / Lee's Summit)", "400 SW Longview Blvd", "Lee's Summit, MO", 38.9018, -94.3800),
    CampusLocation("Northland (Kansas City)", "721 NE 76th St", "Kansas City, MO", 39.2307, -94.5698),
    CampusLocation("Springfield", "2220 W Republic Rd", "Springfield, MO", 37.1611, -93.3304),
    CampusLocation("Rio Rancho / Albuquerque", "7511 Eagle Ranch Rd NW", "Albuquerque, NM", 35.1924, -106.7220),
    CampusLocation("Colorado Springs", "4000 Lee Vance Dr", "Colorado Springs, CO", 38.8907, -104.7475),
    CampusLocation("South Denver (Littleton)", "7745 Titan Rd", "Littleton, CO", 39.5748, -105.0084),
    CampusLocation("North Denver (Aurora)", "15051 E Alameda Pkwy", "Aurora, CO", 39.7106, -104.8115),
    CampusLocation("Papillion", "10600 S 96th St", "Papillion, NE", 41.1463, -96.0725),
    CampusLocation("Omaha", "18015 Pacific St", "Omaha, NE", 41.2509, -96.1987),
    CampusLocation("Hendersonville", "100 Indian Lake Blvd", "Hendersonville, TN", 36.3021, -86.6202),
    CampusLocation("Des Moines (Pleasant Hill)", "6200 E University Ave", "Pleasant Hill, IA", 41.6005, -93.5003),
    CampusLocation("Wellington", "150 S State Rd 7", "Wellington, FL", 26.6573, -80.2055),
    CampusLocation("West Palm Beach", "2625 Okeechobee Blvd", "West Palm Beach, FL", 26.7060, -80.0950),
    CampusLocation("Albany (Latham)", "595 New Loudon Rd", "Latham, NY", 42.7478, -73.7590)
)

/*
Incident Report: Google Pay Integration Failure (GivingPage)
Status: Partially mitigated with robust error handling; pending system-level resolution.

1. Symptom Summary
When the user attempts to initiate a gift by clicking the "Schedule Gift" button, the Google Pay sheet fails to launch.
The UI either displays a generic "Google Pay was cancelled" message or, with the latest updates, specifically reports a system connection failure.

2. Technical Root Cause
The failure is occurring at the IPC (Inter-Process Communication) layer between the app and the Google Play Services (GMS) process.
- Error Code: android.os.DeadObjectException
- System Error: error: -28 (No space left on device)
- Mechanism: A Binder transaction failure is occurring because the device has run out of storage space or the binder buffer is exhausted.
  This causes the GMS remote process to die or become unreachable when the app calls paymentsClient.loadPaymentData.

3. Relevant Log Evidence
Binder transaction failure. id: 321033, BR_*: 29201, error: -28 (No space left on device)
!!! FAILED BINDER TRANSACTION !!! (parcel size = 1284)
android.os.DeadObjectException: Transaction failed on small parcel; remote process probably died,
but this could also be caused by running out of binder buffer space
    at android.os.BinderProxy.transactNative(Native Method)
    at com.google.android.gms.common.internal.zzaa.getService(...)

4. Impacted Components
- Wallet.getPaymentsClient: Proxies to GMS are invalidated.
- isReadyToPay: Fails to return a result, leaving the "Schedule" button in an uncertain state.
- loadPaymentData: Throws the DeadObjectException immediately upon invocation.

5. Current Mitigations (Applied)
The following "surgical" fixes were applied to GivingPage.kt to prevent app crashes and improve UX:
- Null Safety Fix: Resolved an argument type mismatch where Intent? was passed to PaymentData.getFromIntent (which expects non-null Intent).
- Exception Categorization: Added logic to catch DeadObjectException and specifically check the exception cause.
  The UI now informs the user if the failure is due to system resource exhaustion (storage) rather than a simple user cancellation.
- Retry Logic: Introduced gmsCheckAttempt state. If a system failure occurs, the app now attempts to re-initialize the isReadyToPay check automatically.

6. Recommended Action for Senior Developer
This is primarily a system environment issue exacerbated by the emulator/device state.
- Immediate Fix: Wipe data/Cold boot the emulator or free up space on the physical test device.
- Code Review: Ensure WalletOptions environment is strictly ENVIRONMENT_TEST for debug builds.
- Architecture: Consider moving the PaymentsClient initialization to a higher scope (e.g., Activity level or a Singleton)
  if the current remember block is being invalidated too frequently during system stress.
*/
