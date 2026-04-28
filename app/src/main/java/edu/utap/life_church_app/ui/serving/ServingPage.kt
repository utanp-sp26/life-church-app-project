package edu.utap.life_church_app.ui.serving

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ServingPage() {
    var expanded by remember { mutableIntStateOf(1) }
    val steps = listOf(
        "Fill Out the Interest Form" to "Let us know which team you want to serve with.",
        "Complete a Background Check" to "For the safety of our community, all volunteers complete this check.",
        "Attend Orientation" to "Meet leaders and learn how to serve effectively.",
        "Start Serving" to "Sign up for your first serving opportunity."
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF8F8F8)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Start Serving", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold) }
        item { Text("How to Get Started", style = MaterialTheme.typography.headlineSmall) }
        items(steps.size) { index ->
            val stepNumber = index + 1
            Card(
                modifier = Modifier.fillMaxWidth().clickable { expanded = if (expanded == stepNumber) 0 else stepNumber },
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("$stepNumber. ${steps[index].first}", fontWeight = FontWeight.SemiBold)
                        Icon(
                            imageVector = if (expanded == stepNumber) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                    if (expanded == stepNumber) {
                        Text(steps[index].second, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
        item { Text("More Resources", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 8.dp)) }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Connect With Our Team", fontWeight = FontWeight.Medium)
                    Text("Invite a Friend to Serve", fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
