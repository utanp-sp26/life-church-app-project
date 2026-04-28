package edu.utap.life_church_app.ui.lifegroups

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search

@Composable
fun FindLifeGroupPage() {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var location by remember { mutableStateOf("") }
    var onlyShowLocal by remember { mutableStateOf(false) }
    var meetingType by remember { mutableStateOf<MeetingType?>(null) }
    var activeTab by remember { mutableStateOf(FindTab.LifeGroups) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { backDispatcher?.onBackPressed() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    HeaderTabButton(
                        text = "LifeGroups",
                        selected = activeTab == FindTab.LifeGroups,
                        onClick = { activeTab = FindTab.LifeGroups }
                    )
                    HeaderTabButton(
                        text = "Local Partners",
                        selected = activeTab == FindTab.LocalPartners,
                        onClick = { activeTab = FindTab.LocalPartners }
                    )
                }
            }

            FilterCard {
                Text("Where?", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = { Text("Address, City, or Zip Code") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    shape = RoundedCornerShape(999.dp)
                )
                TextButton(onClick = {}, modifier = Modifier.align(Alignment.Start)) {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                    Spacer(Modifier.size(6.dp))
                    Text("Use Current Location", fontWeight = FontWeight.SemiBold)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onlyShowLocal = !onlyShowLocal },
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .border(2.dp, Color(0xFFD1D5DB), CircleShape)
                            .background(
                                if (onlyShowLocal) Color.Black else Color.Transparent,
                                CircleShape
                            )
                    )
                    Text(
                        "Only show LifeGroups listed at Life.Church Austin",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            FilterCard {
                Text("I want to meet...", fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MeetingTypeChip(
                        text = "Online",
                        selected = meetingType == MeetingType.Online,
                        icon = { Icon(Icons.Default.Public, contentDescription = null) },
                        onClick = { meetingType = MeetingType.Online }
                    )
                    MeetingTypeChip(
                        text = "In-Person",
                        selected = meetingType == MeetingType.InPerson,
                        icon = { Icon(Icons.Default.Map, contentDescription = null) },
                        onClick = { meetingType = MeetingType.InPerson }
                    )
                }
            }

            FilterCard {
                Text("Who", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Who you'll meet", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            FilterCard {
                Text("What", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("What interests you", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .navigationBarsPadding()
                .imePadding()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = {
                    location = ""
                    onlyShowLocal = false
                    meetingType = null
                    activeTab = FindTab.LifeGroups
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(999.dp)
            ) {
                Text("Reset")
            }
            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(999.dp)
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
private fun HeaderTabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = if (selected) Color.Black else Color(0xFF9CA3AF),
            fontWeight = FontWeight.SemiBold
        )
        Box(
            modifier = Modifier
                .size(width = 74.dp, height = 2.dp)
                .background(if (selected) Color.Black else Color.Transparent)
        )
    }
}

@Composable
private fun FilterCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = content
        )
    }
}

@Composable
private fun MeetingTypeChip(
    text: String,
    selected: Boolean,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(999.dp),
        border = androidx.compose.foundation.BorderStroke(
            2.dp,
            if (selected) Color.Black else Color(0xFFD1D5DB)
        ),
        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) Color.Black else Color.White,
            contentColor = if (selected) Color.White else Color.Black
        )
    ) {
        icon()
        Spacer(Modifier.size(6.dp))
        Text(text, fontWeight = FontWeight.SemiBold)
    }
}

private enum class MeetingType { Online, InPerson }

private enum class FindTab { LifeGroups, LocalPartners }
