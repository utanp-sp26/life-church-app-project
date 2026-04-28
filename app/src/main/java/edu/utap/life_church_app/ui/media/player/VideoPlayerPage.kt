package edu.utap.life_church_app.ui.media.player

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Airplay
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.utap.life_church_app.ui.media.MediaCard
import edu.utap.life_church_app.ui.media.MediaData

@Composable
fun VideoPlayerPage(id: String, onOpenVideo: (Int) -> Unit) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var showControls by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0.08f) }
    var showShareModal by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var playbackSpeed by remember { mutableStateOf("1.0x") }
    var subtitles by remember { mutableStateOf("Off") }
    val isMessage = MediaData.messages.any { it.id.toString() == id }
    val selected: MediaCard = when {
        isMessage -> MediaData.messages.firstOrNull { it.id.toString() == id } ?: MediaData.messages.first()
        else -> MediaData.stories.firstOrNull { it.id.toString() == id } ?: MediaData.stories.first()
    }
    val relatedItems = if (isMessage) MediaData.messages else MediaData.stories
    val sectionLabel = if (isMessage) "Messages" else "Stories"
    val inSeries = relatedItems.take(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clickable { showControls = !showControls }
        ) {
            AsyncImage(
                model = selected.imageUrl,
                contentDescription = selected.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            if (showControls) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0x80000000), Color.Transparent, Color(0x90000000))
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    VideoOverlayHeader(
                        onBack = { backDispatcher?.onBackPressed() },
                        onShare = { showShareModal = true }
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            IconButton(
                                onClick = { isPlaying = !isPlaying },
                                modifier = Modifier.size(72.dp)
                            ) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Play/Pause",
                                    tint = Color.White,
                                    modifier = Modifier.size(38.dp)
                                )
                            }
                        }
                    }
                    VideoPlaybackControls(
                        isPlaying = isPlaying,
                        progress = progress,
                        onPlayPause = { isPlaying = !isPlaying },
                        onProgressChange = { progress = it },
                        onOpenSettings = { showSettings = true }
                    )
                }
            }
        }

        if (showControls) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActionPill(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Download,
                        label = "Download"
                    )
                    ActionPill(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.PlayArrow,
                        label = "Video"
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        selected.title,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        sectionLabel,
                        color = Color(0xFF9CA3AF),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "IN THIS SERIES",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(inSeries) { item ->
                        SeriesListItem(
                            item = item,
                            onClick = { onOpenVideo(item.id) }
                        )
                    }
                }
            }
        }

        if (showSettings) {
            SettingsSheet(
                playbackSpeed = playbackSpeed,
                subtitles = subtitles,
                onDismiss = { showSettings = false }
            )
        }
        if (showShareModal) {
            ShareSheet(onDismiss = { showShareModal = false })
        }
    }
}

@Composable
private fun VideoOverlayHeader(
    onBack: () -> Unit,
    onShare: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ChevronLeft, contentDescription = "Back", tint = Color.White)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            IconButton(onClick = {}) { Icon(Icons.Default.Cast, contentDescription = null, tint = Color.White) }
            IconButton(onClick = {}) { Icon(Icons.Default.Airplay, contentDescription = null, tint = Color.White) }
            IconButton(onClick = {}) { Icon(Icons.Default.VolumeUp, contentDescription = null, tint = Color.White) }
            IconButton(onClick = onShare) { Icon(Icons.Default.Share, contentDescription = null, tint = Color.White) }
        }
    }
}

@Composable
private fun VideoPlaybackControls(
    isPlaying: Boolean,
    progress: Float,
    onPlayPause: () -> Unit,
    onProgressChange: (Float) -> Unit,
    onOpenSettings: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}, modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)) {
                Icon(Icons.Default.SkipPrevious, contentDescription = null, tint = Color.White)
            }
            Spacer(Modifier.size(14.dp))
            IconButton(onClick = onPlayPause, modifier = Modifier.background(Color.White.copy(alpha = 0.9f), CircleShape)) {
                Icon(
                    if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Spacer(Modifier.size(14.dp))
            IconButton(onClick = {}, modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)) {
                Icon(Icons.Default.SkipNext, contentDescription = null, tint = Color.White)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("0:04", color = Color.White, style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(999.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .height(4.dp)
                        .background(Color.White, RoundedCornerShape(999.dp))
                        .clickable { onProgressChange((progress + 0.05f).coerceAtMost(1f)) }
                )
            }
            Spacer(Modifier.size(8.dp))
            Text("-11:48", color = Color.White, style = MaterialTheme.typography.labelSmall)
            IconButton(onClick = onOpenSettings) {
                Icon(Icons.Default.MoreVert, contentDescription = "Settings", tint = Color.White)
            }
        }
    }
}

@Composable
private fun ActionPill(
    modifier: Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Row(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
            .clickable {}
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
        Spacer(Modifier.size(8.dp))
        Text(label, color = Color.White, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun SeriesListItem(item: MediaCard, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.title,
            modifier = Modifier
                .fillMaxWidth(0.32f)
                .aspectRatio(16f / 10f)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            item.title,
            modifier = Modifier.padding(start = 10.dp),
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun SettingsSheet(
    playbackSpeed: String,
    subtitles: String,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2A2A2A)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = Color.White)
                }
                Text("Settings", color = Color.White, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.size(40.dp))
            }
            SettingRow(
                icon = { Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White) },
                title = "Playback Speed",
                value = playbackSpeed
            )
            SettingRow(
                icon = { Icon(Icons.Default.Subtitles, contentDescription = null, tint = Color.White) },
                title = "Subtitles",
                value = subtitles
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingRow(
    icon: @Composable () -> Unit,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF3A3A3A), RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(Modifier.size(10.dp))
        Text(title, color = Color.White, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
        Text(value, color = Color(0xFF9CA3AF))
    }
}

@Composable
private fun ShareSheet(onDismiss: () -> Unit) {
    val apps = listOf(
        ShareItem("Messages", "💬", Color(0xFF16A34A)),
        ShareItem("WhatsApp", "📞", Color(0xFF15803D)),
        ShareItem("Gmail", "✉️", Color(0xFFDC2626)),
        ShareItem("LinkedIn", "💼", Color(0xFF1D4ED8))
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = null)
                }
                Text("Share Story", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.size(40.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                apps.forEach { app -> ShareGridCell(app) }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📋", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.size(10.dp))
                Text("Copy", fontWeight = FontWeight.Medium)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ShareGridCell(item: ShareItem) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .background(item.color, RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(item.emoji)
        }
        Text(
            item.name,
            modifier = Modifier.padding(top = 6.dp),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }
}

private data class ShareItem(
    val name: String,
    val emoji: String,
    val color: Color
)
