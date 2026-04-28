package edu.utap.life_church_app.ui.giving

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GivingPage() {
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
    var showPaymentConfirmation by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().time) }
    var processDateLabel by remember { mutableStateOf("Today") }
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF1A1A1A) else MaterialTheme.colorScheme.background
    val surfaceColor = if (isDarkTheme) Color(0xFF2A2A2A) else MaterialTheme.colorScheme.surface
    val elevatedSurfaceColor = if (isDarkTheme) Color(0xFF3A3A3A) else MaterialTheme.colorScheme.surface
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor = if (isDarkTheme) Color(0xFF9CA3AF) else MaterialTheme.colorScheme.onSurfaceVariant

    val funds = remember { givingFunds() }
    val frequencies = remember { listOf("Weekly", "Every Two Weeks", "Twice Monthly (1st & 15th)", "Monthly") }
    val locations = remember {
        listOf(
            GivingLocation("Austin", "13609 N. Interstate Hwy 35", "Austin, TX, 78753", "SUGGESTED", "9.9mi"),
            GivingLocation(
                "Life.Church Online",
                "Life.Church Online meets anywhere you are with more than 130 services each week.",
                "",
                "GLOBAL",
                "369.9mi"
            )
        )
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
                    Box(Modifier.padding(18.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
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
                            modifier = Modifier.align(Alignment.TopEnd),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
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
                    title = "Payment Method",
                    value = "Apple Pay",
                    onClick = { showPaymentMethod = true },
                    leading = {
                        Text(
                            "Pay",
                            color = onSurfaceColor,
                            modifier = Modifier
                                .background(surfaceColor, RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
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
                    colors = ButtonDefaults.buttonColors(containerColor = surfaceColor, contentColor = onSurfaceColor),
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
                                if ((amount.toFloatOrNull() ?: 0f) >= 1f) showPaymentConfirmation = true
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = surfaceColor, contentColor = onSurfaceColor),
                            shape = RoundedCornerShape(999.dp)
                        ) {
                            Text(scheduleButtonText(amount), fontWeight = FontWeight.SemiBold)
                        }
                        IconButton(
                            onClick = { showKeyboard = false },
                            modifier = Modifier
                                .size(52.dp)
                                .background(elevatedSurfaceColor, CircleShape)
                        ) {
                            Icon(Icons.Default.Keyboard, contentDescription = null, tint = onSurfaceColor)
                        }
                    }
                    KeypadRow(listOf("1", "2", "3")) { amount = appendDigit(amount, it) }
                    KeypadRow(listOf("4", "5", "6")) { amount = appendDigit(amount, it) }
                    KeypadRow(listOf("7", "8", "9")) { amount = appendDigit(amount, it) }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        KeypadButton(".", Modifier.weight(1f)) { amount = appendDecimal(amount) }
                        KeypadButton("0", Modifier.weight(1f)) { amount = appendDigit(amount, "0") }
                        KeypadButton("⌫", Modifier.weight(1f)) { amount = deleteDigit(amount) }
                    }
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
            items(locations) { loc ->
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
            item { SimpleSelectItem("Apple Pay", selected = true, onClick = {}) }
            item { SimpleSelectItem("Add New Payment Method", selected = false, onClick = {}) }
        }
    }
    if (showPaymentConfirmation) {
        Dialog(onDismissRequest = { showPaymentConfirmation = false }) {
            Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Pay", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { showPaymentConfirmation = false }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                    Text("Pay Life.Church $location", color = Color.Gray)
                    Text("$${amount}", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier
                            .size(62.dp)
                            .background(Color(0xFF3B82F6), CircleShape)
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                    }
                    Text("Confirm with side button", modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
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
                Text(title, color = titleColor, style = MaterialTheme.typography.bodySmall)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(value, color = valueColor, fontWeight = FontWeight.SemiBold)
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = iconColor)
            }
        }
    }
}

@Composable
private fun KeypadRow(keys: List<String>, onKey: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        keys.forEach { key ->
            KeypadButton(key, Modifier.weight(1f)) { onKey(key) }
        }
    }
}

@Composable
private fun KeypadButton(label: String, modifier: Modifier, onClick: () -> Unit) {
    val isDarkTheme = isSystemInDarkTheme()
    val containerColor = if (isDarkTheme) Color(0xFF3A3A3A) else MaterialTheme.colorScheme.surface
    val contentColor = MaterialTheme.colorScheme.onSurface
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
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(location.group, color = Color.Gray, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
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
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(location.name, fontWeight = FontWeight.SemiBold)
                    Text(location.addressLine1, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    if (location.addressLine2.isNotBlank()) {
                        Text(location.addressLine2, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(location.distance, color = Color.Gray)
                    if (selected) Icon(Icons.Default.Check, contentDescription = null)
                }
            }
        }
    }
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
        val dayButtonColor = if (isDarkTheme) Color(0xFFE5E7EB) else MaterialTheme.colorScheme.surface
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
                    colors = ButtonDefaults.buttonColors(containerColor = dayButtonColor, contentColor = onDialogColor),
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
    val group: String,
    val distance: String
)
