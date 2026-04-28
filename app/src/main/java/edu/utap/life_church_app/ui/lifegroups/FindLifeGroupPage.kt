package edu.utap.life_church_app.ui.lifegroups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FindLifeGroupPage() {
    var location by remember { mutableStateOf("") }
    var onlyLocal by remember { mutableStateOf(false) }
    var meetingType by remember { mutableStateOf("Online") }
    var tab by remember { mutableStateOf("LifeGroups") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { tab = "LifeGroups" }) { Text("LifeGroups") }
            OutlinedButton(onClick = { tab = "Local Partners" }) { Text("Local Partners") }
        }
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Where?", style = MaterialTheme.typography.headlineMedium)
                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Address, City, or Zip Code") }, modifier = Modifier.fillMaxWidth())
                Row {
                    Checkbox(checked = onlyLocal, onCheckedChange = { onlyLocal = it })
                    Text("Only show LifeGroups listed at Life.Church Austin", modifier = Modifier.padding(top = 12.dp))
                }
            }
        }
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("I want to meet...", fontWeight = FontWeight.SemiBold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { meetingType = "Online" }) { Text("Online") }
                    Button(onClick = { meetingType = "In-Person" }) { Text("In-Person") }
                }
                Text("Selected: $meetingType")
            }
        }
        Card { Column(Modifier.padding(16.dp)) { Text("Who"); Text("Who you'll meet", color = MaterialTheme.colorScheme.onSurfaceVariant) } }
        Card { Column(Modifier.padding(16.dp)) { Text("What"); Text("What interests you", color = MaterialTheme.colorScheme.onSurfaceVariant) } }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = {
                location = ""
                onlyLocal = false
                meetingType = "Online"
            }, modifier = Modifier.weight(1f)) { Text("Reset") }
            Button(onClick = { }, modifier = Modifier.weight(1f)) { Text("Next") }
        }
    }
}
