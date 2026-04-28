package edu.utap.life_church_app.ui.media

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.utap.life_church_app.ui.theme.LifeMutedForeground

private data class MediaListItem(
    val title: String,
    val series: String,
    val imageUrl: String
)

private val sampleItems = listOf(
    MediaListItem(
        title = "Trusting God Without Understanding",
        series = "Jesus Always",
        imageUrl = "https://images.unsplash.com/photo-1502598919583-c7374009d759?w=800"
    ),
    MediaListItem(
        title = "Barely Standing but Still Holding On",
        series = "Jesus Always",
        imageUrl = "https://images.unsplash.com/photo-1519074069444-1ba4fff66d16?w=800"
    ),
    MediaListItem(
        title = "Who Was Jacob in the Bible?",
        series = "Bible Questions",
        imageUrl = "https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=800"
    )
)

/**
 * Media hub — list pattern for the next page after Home.
 */
@Composable
fun MediaPage() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "Media",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(
                "Messages, worship, and more from Life.Church.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        items(sampleItems, key = { it.title }) { item ->
            MediaRowCard(item)
        }
    }
}

@Composable
private fun MediaRowCard(item: MediaListItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier
                    .width(120.dp)
                    .height(88.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(item.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    item.series,
                    style = MaterialTheme.typography.bodySmall,
                    color = LifeMutedForeground
                )
            }
        }
    }
}
