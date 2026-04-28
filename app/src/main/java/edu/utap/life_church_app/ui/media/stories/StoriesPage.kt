package edu.utap.life_church_app.ui.media.stories

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
fun StoriesPage(onOpenVideo: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Stories", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(
                "We're all on a journey together. Here are some amazing ways God is working through your church.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        items(MediaData.stories) { story ->
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onOpenVideo(story.id) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(shape = RoundedCornerShape(12.dp)) {
                    AsyncImage(
                        model = story.imageUrl,
                        contentDescription = story.title,
                        modifier = Modifier.fillMaxWidth(0.35f),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(Modifier.padding(start = 12.dp)) {
                    Text(story.title, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
