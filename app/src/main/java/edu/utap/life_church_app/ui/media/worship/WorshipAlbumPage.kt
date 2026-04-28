package edu.utap.life_church_app.ui.media.worship

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WorshipAlbumPage(id: String, onPlaySong: (Int) -> Unit) {
    val songs = listOf("El Nos Redimio", "Digno de Alabar", "Tanto", "Abrimos Camino", "Quien Mas Es Digno")
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1630467267476-c67b34ffc837?w=800",
                contentDescription = "album",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text("El Camino", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            Text("Life.Church Worship", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        item {
            Card {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Streaming Services", style = MaterialTheme.typography.titleMedium)
                    Text("Apple Music   Amazon Music   Spotify")
                }
            }
        }
        items(songs.indices.toList()) { index ->
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onPlaySong(index + 1) }.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${index + 1}. ${songs[index]}", fontWeight = FontWeight.Medium)
                Icon(Icons.Default.PlayArrow, contentDescription = null)
            }
        }
    }
}
