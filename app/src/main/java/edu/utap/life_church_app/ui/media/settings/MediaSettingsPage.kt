package edu.utap.life_church_app.ui.media.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MediaSettingsPage() {
    var autoPlay by remember { mutableStateOf(true) }
    var wifiOnly by remember { mutableStateOf(true) }
    var autoDelete by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Media Settings", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Card {
            SettingRow("Auto Play Media", autoPlay) { autoPlay = it }
            SettingRow("Download Only on Wi-Fi", wifiOnly) { wifiOnly = it }
            SettingRow("Auto Delete Played Downloads", autoDelete) { autoDelete = it }
        }
    }
}

@Composable
private fun SettingRow(label: String, checked: Boolean, onToggle: (Boolean) -> Unit) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onToggle)
    }
}
