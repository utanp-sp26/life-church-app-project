package edu.utap.life_church_app.ui.media.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.utap.life_church_app.ui.media.MediaData

@Composable
fun VideoPlayerPage(id: String, onOpenVideo: (Int) -> Unit) {
    var showControls by remember { mutableStateOf(true) }
    var playing by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0.08f) }
    val story = MediaData.stories.firstOrNull { it.id.toString() == id } ?: MediaData.stories.first()

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Box(modifier = Modifier.fillMaxWidth().height(260.dp).clickable { showControls = !showControls }) {
            AsyncImage(model = story.imageUrl, contentDescription = story.title, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            if (showControls) {
                Column(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.SpaceBetween) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        IconButton(onClick = { }) { Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.White) }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = { playing = !playing }) {
                            Icon(if (playing) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                        }
                    }
                }
            }
        }
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Slider(value = progress, onValueChange = { progress = it })
            Text(story.title, color = Color.White, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text("Stories", color = Color.LightGray)
            Text("IN THIS SERIES", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
        }
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(MediaData.stories) { item ->
                Row(modifier = Modifier.fillMaxWidth().clickable { onOpenVideo(item.id) }, verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(model = item.imageUrl, contentDescription = item.title, modifier = Modifier.fillMaxWidth(0.35f), contentScale = ContentScale.Crop)
                    Text(item.title, modifier = Modifier.padding(start = 10.dp), color = Color.White)
                }
            }
        }
    }
}
