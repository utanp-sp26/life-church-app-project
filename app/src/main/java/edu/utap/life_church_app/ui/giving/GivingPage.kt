package edu.utap.life_church_app.ui.giving

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.foundation.lazy.LazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GivingPage() {
    var recurring by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("0") }
    var fund by remember { mutableStateOf("Tithe") }
    var processDate by remember { mutableStateOf("Today") }
    var frequency by remember { mutableStateOf("Monthly") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Austin", color = Color.White, style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = { }) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
                }
            }
        }
        item {
            Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFAB5A3D))) {
                Column(Modifier.padding(16.dp)) {
                    Text("One Day", color = Color.White, style = MaterialTheme.typography.displaySmall)
                    Text("Give one day's income to Local & Global Missions.", color = Color.White)
                    Button(onClick = { }, modifier = Modifier.padding(top = 8.dp)) { Text("Calculate") }
                }
            }
        }
        item {
            Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("$$amount", color = Color(0xFF43D6F3), style = MaterialTheme.typography.displaySmall)
                        Text(fund, color = Color.White)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("10", "25", "50", "100").forEach {
                            Button(onClick = { amount = it }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF404040))) {
                                Text(it)
                            }
                        }
                    }
                }
            }
        }
        item {
            Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Make Gift Recurring", color = Color.White)
                        Switch(checked = recurring, onCheckedChange = { recurring = it })
                    }
                    if (recurring) {
                        Text("Frequency: $frequency", color = Color.White)
                    }
                    Text("Process Date: $processDate", color = Color.White)
                    Text("Payment Method: Apple Pay", color = Color.White)
                }
            }
        }
        item {
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
            ) {
                Text(if (amount.toIntOrNull() ?: 0 > 0) "Schedule $$amount Gift" else "To Give, Enter Amount", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
