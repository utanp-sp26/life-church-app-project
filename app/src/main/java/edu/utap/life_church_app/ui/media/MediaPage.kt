package edu.utap.life_church_app.ui.media

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
fun MediaPage(
    onOpenMessages: () -> Unit,
    onOpenWorship: () -> Unit,
    onOpenStories: () -> Unit,
    onOpenLeadershipPodcast: () -> Unit,
    onOpenLifeGroups: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenVideo: (Int) -> Unit,
    onOpenAudio: (Int) -> Unit,
    onOpenWorshipAlbum: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Media", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
            OutlinedButton(onClick = { }) { Text("My Downloads") }
            OutlinedButton(onClick = onOpenSettings) { Text("Media Settings") }
            OutlinedButton(onClick = { }) { Text("More Ways to Watch") }
        }
        MediaSection("Messages", onOpenMessages, "View All")
        HorizontalCards(MediaData.messages.take(3).map { it.title to it.imageUrl }) { onOpenVideo(it + 1) }
        MediaSection("Worship", onOpenWorship, "View All")
        HorizontalCards(MediaData.worshipAlbums.map { it.title to (it.imageUrl ?: "https://images.unsplash.com/photo-1630467267476-c67b34ffc837?w=800") }) { onOpenWorshipAlbum(it + 1) }
        MediaSection("LifeGroups", onOpenLifeGroups, "View All")
        HorizontalCards(MediaData.lifeGroups.map { it.title to it.imageUrl }) { onOpenLifeGroups() }
        MediaSection("Stories", onOpenStories, "View All")
        HorizontalCards(MediaData.stories.map { it.title to it.imageUrl }) { onOpenVideo(it + 1) }
        MediaSection("Leadership Podcast", onOpenLeadershipPodcast, "View All")
        HorizontalCards(MediaData.podcastEpisodes.map { it.title to it.imageUrl }) { index ->
            onOpenAudio(MediaData.podcastEpisodes[index].id)
        }
    }
}

@Composable
private fun MediaSection(title: String, onClick: () -> Unit, cta: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Button(onClick = onClick) { Text(cta) }
    }
}

@Composable
private fun HorizontalCards(cards: List<Pair<String, String>>, onClick: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
        cards.forEachIndexed { index, (title, image) ->
            Card(modifier = Modifier.fillMaxWidth(0.45f).clickable { onClick(index) }, shape = RoundedCornerShape(12.dp)) {
                AsyncImage(model = image, contentDescription = title, modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
                Text(title, modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
