package edu.utap.life_church_app.ui.media.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.utap.life_church_app.ui.media.MediaData

@Composable
fun AudioPlayerPage(id: String) {
    var currentId by remember { mutableIntStateOf(id.toIntOrNull() ?: 191) }
    var playing by remember { mutableStateOf(true) }
    var progress by remember { mutableFloatStateOf(0.1f) }
    val episodes = MediaData.podcastEpisodes
    val currentIndex = episodes.indexOfFirst { it.id == currentId }.coerceAtLeast(0)
    val current = episodes[currentIndex]

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = current.imageUrl, contentDescription = current.title, modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
        Text(current.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        Text("Craig Groeschel Leadership Podcast", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Slider(value = progress, onValueChange = { progress = it })
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("0:04")
            Text(current.duration)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (currentIndex > 0) currentId = episodes[currentIndex - 1].id
            }) { Icon(Icons.Default.SkipPrevious, contentDescription = null) }
            IconButton(onClick = { playing = !playing }) {
                Icon(if (playing) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = null)
            }
            IconButton(onClick = {
                if (currentIndex < episodes.lastIndex) currentId = episodes[currentIndex + 1].id
            }) { Icon(Icons.Default.SkipNext, contentDescription = null) }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("Speed 1.0x")
            Text("Sleep Timer")
        }
    }
}
