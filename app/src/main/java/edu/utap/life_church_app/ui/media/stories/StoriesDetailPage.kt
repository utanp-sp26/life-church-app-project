package edu.utap.life_church_app.ui.media.stories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StoriesDetailPage(id: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Story", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        Text("Story detail for id: $id", style = MaterialTheme.typography.bodyLarge)
        Text("Story detail page coming soon...", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
