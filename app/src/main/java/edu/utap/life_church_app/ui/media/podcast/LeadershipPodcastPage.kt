package edu.utap.life_church_app.ui.media.podcast

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.utap.life_church_app.ui.media.MediaData

@Composable
fun LeadershipPodcastPage(onOpenAudio: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1772396719557-5743c644877f?w=800",
                contentDescription = "Podcast hero",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text("Craig Groeschel Leadership Podcast", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            Text("Timely insights from Pastor Craig and guests.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        items(MediaData.podcastEpisodes) { episode ->
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onOpenAudio(episode.id) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(shape = RoundedCornerShape(12.dp)) {
                    AsyncImage(
                        model = episode.imageUrl,
                        contentDescription = episode.title,
                        modifier = Modifier.fillMaxWidth(0.35f),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(Modifier.padding(start = 12.dp)) {
                    Text(episode.title, fontWeight = FontWeight.SemiBold)
                    Text("Ep. ${episode.id}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
