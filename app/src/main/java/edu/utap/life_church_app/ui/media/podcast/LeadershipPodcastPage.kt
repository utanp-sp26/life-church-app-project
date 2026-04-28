package edu.utap.life_church_app.ui.media.podcast

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.utap.life_church_app.ui.media.MediaData

@Composable
fun LeadershipPodcastPage(
    onOpenAudio: (Int) -> Unit,
    onBack: () -> Unit,
    onOpenSettings: () -> Unit,
    onShare: () -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        stickyHeader { _ ->
            Surface(shadowElevation = 2.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onOpenSettings) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "Settings",
                            )
                        }
                        IconButton(onClick = onShare) {
                            Icon(
                                imageVector = Icons.Outlined.IosShare,
                                contentDescription = "Share",
                            )
                        }
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                AsyncImage(
                    model = MediaData.leadershipPodcastHero.imageUrl,
                    contentDescription = MediaData.leadershipPodcastHero.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(224.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = MediaData.leadershipPodcastHero.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = MediaData.leadershipPodcastDescription,
                    modifier = Modifier.padding(top = 12.dp, bottom = 20.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        items(MediaData.podcastEpisodes) { episode ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenAudio(episode.id) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                AsyncImage(
                    model = episode.imageUrl,
                    contentDescription = episode.title,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = episode.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "Ep. ${episode.id}",
                        modifier = Modifier.padding(top = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        item {
            Box(modifier = Modifier.height(16.dp))
        }
    }
}
