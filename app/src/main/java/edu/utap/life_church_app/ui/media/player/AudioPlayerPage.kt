package edu.utap.life_church_app.ui.media.player

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.utap.life_church_app.ui.media.MediaData
import edu.utap.life_church_app.ui.media.PodcastEpisode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerPage(id: String) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var currentId by remember { mutableIntStateOf(id.toIntOrNull() ?: 191) }
    var isPlaying by remember { mutableStateOf(true) }
    var progress by remember { mutableFloatStateOf(0.1f) }
    var showShareModal by remember { mutableStateOf(false) }

    val episodes = MediaData.podcastEpisodes
    val currentIndex = episodes.indexOfFirst { it.id == currentId }.let { if (it == -1) 0 else it }
    val current = episodes[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayerHeader(
            onBack = { backDispatcher?.onBackPressed() },
            onShare = { showShareModal = true }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = current.imageUrl,
                contentDescription = current.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(18.dp)),
                contentScale = ContentScale.Crop
            )
        }

        ProgressSection(
            progress = progress,
            duration = current.duration
        )

        Text(
            text = current.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Craig Groeschel Leadership Podcast",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        PlayerControls(
            isPlaying = isPlaying,
            canGoPrevious = currentIndex > 0,
            canGoNext = currentIndex < episodes.lastIndex,
            onPrevious = {
                if (currentIndex > 0) currentId = episodes[currentIndex - 1].id
            },
            onNext = {
                if (currentIndex < episodes.lastIndex) currentId = episodes[currentIndex + 1].id
            },
            onPlayPause = { isPlaying = !isPlaying }
        )

        SpeedSleepRow()
        Spacer(Modifier.height(24.dp))
    }

    if (showShareModal) {
        ShareSheet(
            episode = current,
            onDismiss = { showShareModal = false }
        )
    }
}

@Composable
private fun PlayerHeader(onBack: () -> Unit, onShare: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Collapse player")
        }
        IconButton(onClick = onShare) {
            Icon(Icons.Default.Share, contentDescription = "Share")
        }
    }
}

@Composable
private fun ProgressSection(
    progress: Float,
    duration: String
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("0:04", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(duration, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color(0xFFE5E7EB), androidx.compose.foundation.shape.RoundedCornerShape(999.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .height(4.dp)
                    .background(Color.Black, androidx.compose.foundation.shape.RoundedCornerShape(999.dp))
            )
        }
    }
}

@Composable
private fun PlayerControls(
    isPlaying: Boolean,
    canGoPrevious: Boolean,
    canGoNext: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onPlayPause: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}, modifier = Modifier.size(40.dp)) {
            Text("«", style = MaterialTheme.typography.titleMedium)
        }
        Box(contentAlignment = Alignment.Center) {
            IconButton(onClick = onPrevious, enabled = canGoPrevious) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "Previous", modifier = Modifier.size(34.dp))
            }
            Text("30", modifier = Modifier.padding(top = 26.dp), style = MaterialTheme.typography.labelSmall)
        }
        Surface(
            modifier = Modifier.size(64.dp),
            color = Color.Black,
            shape = androidx.compose.foundation.shape.CircleShape
        ) {
            IconButton(onClick = onPlayPause) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play or pause",
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }
        }
        Box(contentAlignment = Alignment.Center) {
            IconButton(onClick = onNext, enabled = canGoNext) {
                Icon(Icons.Default.SkipNext, contentDescription = "Next", modifier = Modifier.size(34.dp))
            }
            Text("30", modifier = Modifier.padding(top = 26.dp), style = MaterialTheme.typography.labelSmall)
        }
        IconButton(onClick = {}, modifier = Modifier.size(40.dp)) {
            Text("»", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun SpeedSleepRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButtonLike(label = "Speed 1.0x", modifier = Modifier.weight(1f))
        Divider(
            modifier = Modifier
                .height(28.dp)
                .width(1.dp),
            color = Color(0xFFD1D5DB)
        )
        TextButtonLike(label = "Sleep Timer", modifier = Modifier.weight(1f))
    }
}

@Composable
private fun TextButtonLike(label: String, modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(label, fontWeight = FontWeight.SemiBold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShareSheet(episode: PodcastEpisode, onDismiss: () -> Unit) {
    val shareApps = listOf(
        ShareOption("AirDrop", "📱", Color(0xFF3B82F6)),
        ShareOption("Messages", "💬", Color(0xFF22C55E)),
        ShareOption("Gmail", "✉️", Color(0xFFEF4444)),
        ShareOption("LinkedIn", "💼", Color(0xFF1D4ED8))
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Close share")
                }
                Text(
                    text = episode.title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.size(40.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                shareApps.forEach { option ->
                    ShareShortcut(option = option)
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null)
                    Text("Copy", fontWeight = FontWeight.Medium)
                }
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ShareShortcut(option: ShareOption) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .background(option.color, androidx.compose.foundation.shape.RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(option.emoji)
        }
        Text(option.name, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center)
    }
}

private data class ShareOption(
    val name: String,
    val emoji: String,
    val color: Color
)
