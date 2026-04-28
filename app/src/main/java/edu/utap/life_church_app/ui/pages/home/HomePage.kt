package edu.utap.life_church_app.ui.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    onOpenMenu: () -> Unit = {},
    openPrayerFromMenu: Boolean = false,
    onConsumeOpenPrayerFromMenu: () -> Unit = {},
) {
    val prayerRequestRepository = remember { PrayerRequestRepository() }
    val coroutineScope = rememberCoroutineScope()
    var showPrayerModal by remember { mutableStateOf(false) }
    var showPhoneModal by remember { mutableStateOf(false) }
    var showConfirmationModal by remember { mutableStateOf(false) }
    var showShareModal by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
    var prayerText by remember { mutableStateOf("") }
    var hasPhone by remember { mutableStateOf(false) }
    var isSubmittingPrayer by remember { mutableStateOf(false) }
    var prayerSubmitError by remember { mutableStateOf<String?>(null) }
    val profileName = "Perry Ehimuh"
    val churchLocation = "Austin Branch"

    LaunchedEffect(openPrayerFromMenu) {
        if (openPrayerFromMenu) {
            showPrayerModal = true
            onConsumeOpenPrayerFromMenu()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection(onOpenMenu = onOpenMenu)
            MainContent(
                onPrayerClick = { showPrayerModal = true },
                onShareClick = { showShareModal = true }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        if (showPrayerModal) {
            PrayerRequestModal(
                profileName = profileName,
                profileChurch = "Life.Church Austin",
                hasPhone = hasPhone,
                prayerText = prayerText,
                isSubmitting = isSubmittingPrayer,
                errorMessage = prayerSubmitError,
                onPrayerTextChange = { prayerText = it },
                onClose = {
                    if (!isSubmittingPrayer) {
                        showPrayerModal = false
                        prayerSubmitError = null
                    }
                },
                onEditPhone = { showPhoneModal = true },
                onSubmit = {
                    if (!isSubmittingPrayer) {
                        if (prayerText.isBlank()) {
                            prayerSubmitError = "Please enter your prayer request before submitting."
                        } else {
                            prayerSubmitError = null
                            isSubmittingPrayer = true

                            coroutineScope.launch {
                                runCatching {
                                    prayerRequestRepository.submitPrayerRequest(
                                        name = profileName,
                                        churchLocation = churchLocation,
                                        prayerRequest = prayerText.trim(),
                                    )
                                }.onSuccess {
                                    showPrayerModal = false
                                    showConfirmationModal = true
                                }.onFailure {
                                    prayerSubmitError = "We couldn't submit your request. Please try again."
                                }
                                isSubmittingPrayer = false
                            }
                        }
                    }
                }
            )
        }

        if (showPhoneModal) {
            PhoneNumberModal(
                phoneNumber = phoneNumber,
                onPhoneChange = { phoneNumber = it },
                onClose = { showPhoneModal = false },
                onSave = {
                    if (phoneNumber.isNotBlank()) {
                        hasPhone = true
                        showPhoneModal = false
                    }
                }
            )
        }

        if (showConfirmationModal) {
            ConfirmationModal(
                onDone = {
                    showConfirmationModal = false
                    prayerText = ""
                    prayerSubmitError = null
                }
            )
        }

        if (showShareModal) {
            ShareModal(onClose = { showShareModal = false })
        }
    }
}

@Composable
fun HeaderSection(onOpenMenu: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onOpenMenu) { Icon(Icons.Default.Menu, contentDescription = "Menu") }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { }) { Icon(Icons.Default.Notifications, contentDescription = "Notifications") }
                Box(
                    modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFFE5E5E5)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Austin", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ActionButton("Check In", { Icon(Icons.Default.QrCode, contentDescription = null) }, Modifier.weight(1f))
            ActionButton("Invite", { Icon(Icons.Default.PersonAdd, contentDescription = null) }, Modifier.weight(1f))
            ActionButton("Location Info", { Icon(Icons.Default.Info, contentDescription = null) }, Modifier.weight(1f))
        }
    }
}

@Composable
private fun MainContent(onPrayerClick: () -> Unit, onShareClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CampusHeroCard()
        QuickActionsSection(onPrayerClick)
        LatestMessageSection(onShareClick)
        MoreFromLifeChurchSection()
    }
}

@Composable
private fun CampusHeroCard() {
    ImageCard(
        imageUrl = "https://images.unsplash.com/photo-1765309541707-4b2bf887e1ca?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1080",
        imageDescription = "Church building"
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Austin ", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Now", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF7E57C2))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text("See what is happening at Austin", color = Color.Gray, fontSize = 14.sp)
    }
}

@Composable
private fun QuickActionsSection(onPrayerClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickActionCard("🙏", "Ask for Prayer", "We'd love to pray with you.", Color(0xFFFFF3CD), onPrayerClick)
        QuickActionCard("💜", "Commit to Christ", "Share your decision to follow Jesus.", Color(0xFFEDE7F6))
        QuickActionCard("👤", "I'm New", "Tell us about yourself.", Color(0xFFE0F7FA))
        QuickActionCard("☰", "Find Your Next Step", "More ways to connect", Color(0xFFE3F2FD))
    }
}

@Composable
private fun QuickActionCard(emoji: String, title: String, subtitle: String, circleColor: Color, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.width(280.dp).clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(circleColor), contentAlignment = Alignment.Center) {
                Text(emoji, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(subtitle, color = Color.Gray, fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun LatestMessageSection(onShareClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Latest Message", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)), shape = RoundedCornerShape(10.dp)) {
                Text("📝 Message Notes")
            }
            SmallOutlinedAction("Share", onShareClick) { Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp)) }
            SmallOutlinedAction("Attend Online") { Icon(Icons.Default.Public, contentDescription = null, modifier = Modifier.size(18.dp)) }
            SmallOutlinedAction("Talk It Over") { Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(18.dp)) }
            SmallOutlinedAction("View Series") { Icon(Icons.Default.List, contentDescription = null, modifier = Modifier.size(18.dp)) }
        }

        Card(
            modifier = Modifier.fillMaxWidth().clickable { },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1502598919583-c7374009d759?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1080",
                        contentDescription = "Latest message",
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(modifier = Modifier.matchParentSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color(0x66000000)))))
                    Text("Jesus Always", color = Color.White, modifier = Modifier.align(Alignment.TopStart).padding(16.dp))
                    Text("Waiting\nOn God", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center))
                    Box(modifier = Modifier.align(Alignment.Center).offset(y = 60.dp).size(64.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.9f)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.Black, modifier = Modifier.size(36.dp))
                    }
                }
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFB2DFDB)), contentAlignment = Alignment.Center) {
                        Text("🌊", fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Trusting God Without Understanding", fontWeight = FontWeight.SemiBold)
                        Text("Jesus Always", color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun MoreFromLifeChurchSection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("More from Life.Church", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MoreCard(
                title = "Behind One Day",
                subtitle = "This week, we're going behind the scenes with Pastor Craig—and inviting you to be part of what's next.",
                cta = "Check It Out",
                imageUrl = "https://images.unsplash.com/photo-1519074069444-1ba4fff66d16?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1080"
            )
            MoreCard(
                title = "Who Was Jacob in the Bible?",
                subtitle = "Jacob's story is messy, complicated, and honest. Explore how God works through flawed people.",
                cta = "Find Hope",
                imageUrl = "https://images.unsplash.com/photo-1459749411175-04bf5292ceea?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1080"
            )
            MoreCard(
                title = "Invite a Friend to Church",
                subtitle = "Someone you know needs to hear about the hope of Jesus. Inviting them is easy—tap Invite.",
                cta = "Send an Invite",
                imageUrl = "https://images.unsplash.com/photo-1511632765486-a01980e01a18?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1080"
            )
            MoreCard(
                title = "Rewire Your Mind with God's Help",
                subtitle = "Get timely insights from Pastor Craig about leadership and making the most of your potential.",
                cta = "Listen Now",
                imageUrl = "https://images.unsplash.com/photo-1772396719557-5743c644877f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1080"
            )
        }
    }
}

@Composable
private fun MoreCard(title: String, subtitle: String, cta: String, imageUrl: String) {
    Card(
        modifier = Modifier.width(280.dp).clickable { },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            AsyncImage(model = imageUrl, contentDescription = title, modifier = Modifier.fillMaxWidth().height(190.dp), contentScale = ContentScale.Crop)
            Column(modifier = Modifier.padding(16.dp)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(subtitle, color = Color.Gray, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(cta, color = Color(0xFF00A6C8), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun PrayerRequestModal(
    profileName: String,
    profileChurch: String,
    hasPhone: Boolean,
    prayerText: String,
    isSubmitting: Boolean,
    errorMessage: String?,
    onPrayerTextChange: (String) -> Unit,
    onClose: () -> Unit,
    onEditPhone: () -> Unit,
    onSubmit: () -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        Surface(shape = RoundedCornerShape(28.dp), color = Color(0xFFF4F4F4), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp).verticalScroll(rememberScrollState())) {
                IconButton(onClick = onClose, modifier = Modifier.clip(CircleShape).background(Color(0xFFE0E0E0))) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Close")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.size(64.dp).clip(CircleShape).background(Color(0xFFFFC107)), contentAlignment = Alignment.Center) { Text("🙏", fontSize = 32.sp) }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Ask for Prayer", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    if (hasPhone) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF00A6C8), modifier = Modifier.size(16.dp))
                            Text(" Everything looks good", color = Color(0xFF00A6C8), fontSize = 13.sp)
                        }
                    } else {
                        Text("All fields are required", color = Color.Gray, fontSize = 13.sp)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                ProfileInfoCard(
                    profileName = profileName,
                    profileChurch = profileChurch,
                    onEditPhone = onEditPhone
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = prayerText,
                    onValueChange = onPrayerTextChange,
                    placeholder = { Text("Type your message here ...") },
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                )
                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = if (hasPhone) onSubmit else onEditPhone,
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    shape = CircleShape,
                    enabled = !isSubmitting,
                    colors = ButtonDefaults.buttonColors(containerColor = if (hasPhone) Color.Black else Color(0xFFD5D5D5), contentColor = if (hasPhone) Color.White else Color.Black)
                ) { Text(if (hasPhone && isSubmitting) "Submitting..." else if (hasPhone) "Submit" else "Add Missing Info", fontWeight = FontWeight.SemiBold) }
            }
        }
    }
}

@Composable
private fun ProfileInfoCard(
    profileName: String,
    profileChurch: String,
    onEditPhone: () -> Unit
) {
    Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFE0E0E0)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.Gray)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(profileName, fontWeight = FontWeight.SemiBold)
                    Text(profileChurch, color = Color.Gray, fontSize = 13.sp)
                }
            }
            Button(onClick = onEditPhone, shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0), contentColor = Color.Black)) { Text("Edit") }
        }
    }
}

@Composable
private fun PhoneNumberModal(phoneNumber: String, onPhoneChange: (String) -> Unit, onClose: () -> Unit, onSave: () -> Unit) {
    Dialog(onDismissRequest = onClose) {
        Surface(shape = RoundedCornerShape(28.dp), color = Color(0xFFF4F4F4), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onClose, modifier = Modifier.clip(CircleShape).background(Color(0xFFE0E0E0))) { Icon(Icons.Default.Close, contentDescription = "Close") }
                    Text("Add Phone Number", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.size(48.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = onPhoneChange,
                    placeholder = { Text("(555) 123-4567") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = onSave, modifier = Modifier.fillMaxWidth().height(54.dp), shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text("Save", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun ConfirmationModal(onDone: () -> Unit) {
    Dialog(onDismissRequest = onDone) {
        Surface(shape = RoundedCornerShape(28.dp), color = Color(0xFFF4F4F4), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = onDone, modifier = Modifier.clip(CircleShape).background(Color(0xFFE0E0E0))) { Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Close") }
                }
                Box(modifier = Modifier.size(80.dp).clip(CircleShape).border(4.dp, Color(0xFF00A6C8), CircleShape), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF00A6C8), modifier = Modifier.size(42.dp))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("Your prayers matter to us.", fontSize = 30.sp, lineHeight = 34.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(12.dp))
                Text("God hears every prayer, and we're honored you shared this one with us, too. We'll reach out soon.", color = Color.DarkGray, fontSize = 14.sp, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onDone, modifier = Modifier.fillMaxWidth().height(54.dp), shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text("Done", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun ShareModal(onClose: () -> Unit) {
    Dialog(onDismissRequest = onClose) {
        Surface(shape = RoundedCornerShape(28.dp), color = Color.White, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState())) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onClose) { Icon(Icons.Default.Close, contentDescription = "Close") }
                    Text("I'm watching \"Barely Standing but Still Holding On\" from the series Jesus Always...", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.size(48.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    listOf("Coda", "Whitfield", "Jerry Ehimuh", "Farhan", "Mom").forEach { ContactBubble(it) }
                }
                Spacer(modifier = Modifier.height(20.dp))
                val apps = listOf("AirDrop" to "📱", "Messages" to "💬", "Notes" to "📝", "Journal" to "📔", "Gmail" to "✉️", "Discord" to "💬", "LinkedIn" to "💼", "WhatsApp" to "📞")
                apps.chunked(4).forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        row.forEach { (name, icon) -> AppShareIcon(name, icon) }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
                Divider()
                ShareOption("📋", "Copy")
                ShareOption("📝", "New Quick Note")
                ShareOption("💾", "Save to Files")
            }
        }
    }
}

@Composable
private fun ContactBubble(name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(70.dp)) {
        Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(Color(0xFFE5E5E5)), contentAlignment = Alignment.Center) { Text("👤") }
        Spacer(modifier = Modifier.height(6.dp))
        Text(name, fontSize = 11.sp, textAlign = TextAlign.Center, maxLines = 2)
    }
}

@Composable
private fun AppShareIcon(name: String, icon: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(72.dp)) {
        Box(modifier = Modifier.size(54.dp).clip(RoundedCornerShape(16.dp)).background(Color(0xFFE5E5E5)), contentAlignment = Alignment.Center) { Text(icon, fontSize = 24.sp) }
        Spacer(modifier = Modifier.height(6.dp))
        Text(name, fontSize = 11.sp, textAlign = TextAlign.Center, maxLines = 2)
    }
}

@Composable
private fun ShareOption(icon: String, text: String) {
    Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(icon, fontSize = 22.sp)
        Spacer(modifier = Modifier.width(14.dp))
        Text(text, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun SmallOutlinedAction(text: String, onClick: () -> Unit = {}, icon: @Composable () -> Unit) {
    OutlinedButton(onClick = onClick, shape = RoundedCornerShape(10.dp), contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)) {
        icon()
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, fontSize = 13.sp, maxLines = 1)
    }
}

@Composable
private fun ImageCard(imageUrl: String, imageDescription: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            AsyncImage(model = imageUrl, contentDescription = imageDescription, modifier = Modifier.fillMaxWidth().height(220.dp), contentScale = ContentScale.Crop)
            Column(modifier = Modifier.padding(16.dp), content = content)
        }
    }
}

@Composable
private fun ActionButton(text: String, icon: @Composable () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(onClick = { }, modifier = modifier.height(48.dp), shape = RoundedCornerShape(10.dp), contentPadding = PaddingValues(horizontal = 8.dp)) {
        icon()
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, fontSize = 12.sp, maxLines = 1)
    }
}
