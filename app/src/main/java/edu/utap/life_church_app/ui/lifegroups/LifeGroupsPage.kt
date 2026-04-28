package edu.utap.life_church_app.ui.lifegroups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun LifeGroupsPage(
    onFindLifeGroup: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("LifeGroups", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1769672774209-73ce5964bced?w=1000",
                    contentDescription = "Meaningful friendships",
                    modifier = Modifier.fillMaxWidth().height(240.dp),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Meaningful friendships start here.", style = MaterialTheme.typography.headlineMedium)
                    Text("Find your people today.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Button(onClick = onFindLifeGroup, modifier = Modifier.fillMaxWidth()) { Text("Find a LifeGroup") }
                    OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("Start a LifeGroup") }
                }
            }
        }
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("LifeGroup Resources", style = MaterialTheme.typography.titleLarge)
                Text("What to Expect")
                Text("Talk It Over")
            }
        }
    }
}
