package edu.utap.life_church_app.ui.media

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
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
    val listState = rememberLazyListState()
    val headerAlpha by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset < 180) 1f else 0f
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                HeaderRow(modifier = Modifier.alpha(headerAlpha))
                Spacer(modifier = Modifier.height(12.dp))
                HeaderActions(modifier = Modifier.alpha(headerAlpha), onOpenSettings = onOpenSettings)
            }
        }
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader("Messages", onOpenMessages)
                Spacer(modifier = Modifier.height(12.dp))
                HeroCard(
                    hero = MediaData.messagesHero,
                    overlay = MediaData.messagesHeroOverlay,
                    gradient = Brush.linearGradient(listOf(Color(0xFF38BDF8), Color(0xFF0284C7))),
                    onClick = { onOpenVideo(MediaData.messages.firstOrNull()?.id ?: 1) }
                )
                Spacer(modifier = Modifier.height(12.dp))
                DescriptionWithRail(
                    description = MediaData.messagesDescription,
                    cards = MediaData.messages
                ) { card -> onOpenVideo(card.id) }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFF9333EA), Color(0xFF6B21A8))))
                    .padding(16.dp)
            ) {
                SectionHeader("Worship", onOpenWorship, forceLight = true)
                Spacer(modifier = Modifier.height(12.dp))
                AlbumRail(MediaData.worshipAlbums, onOpenWorshipAlbum)
            }
        }
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader("LifeGroups", onOpenLifeGroups)
                Spacer(modifier = Modifier.height(12.dp))
                DescriptionWithRail(
                    description = MediaData.lifeGroupsDescription,
                    cards = MediaData.lifeGroups
                ) { onOpenLifeGroups() }
            }
        }
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader("Stories", onOpenStories)
                Spacer(modifier = Modifier.height(12.dp))
                DescriptionWithRail(
                    description = MediaData.storiesDescription,
                    cards = MediaData.stories
                ) { card -> onOpenVideo(card.id) }
            }
        }
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                SectionHeader("Leadership Podcast", onOpenLeadershipPodcast)
                Spacer(modifier = Modifier.height(12.dp))
                HeroCard(
                    hero = MediaData.leadershipPodcastHero,
                    overlay = null,
                    gradient = Brush.linearGradient(listOf(Color.LightGray, Color.Gray)),
                    onClick = onOpenLeadershipPodcast
                )
                Spacer(modifier = Modifier.height(12.dp))
                DescriptionWithRail(
                    description = MediaData.leadershipPodcastDescription,
                    cards = MediaData.podcastEpisodes.map {
                        MediaCard(id = it.id, title = it.title, imageUrl = it.imageUrl)
                    }
                ) { card -> onOpenAudio(card.id) }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun HeaderRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Media",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Profile",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun HeaderActions(modifier: Modifier = Modifier, onOpenSettings: () -> Unit) {
    val scrollState = rememberScrollState()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ActionButton(
            title = MediaData.actionButtons[0],
            icon = Icons.Outlined.Download,
            onClick = {}
        )
        ActionButton(
            title = MediaData.actionButtons[1],
            icon = Icons.Outlined.Settings,
            onClick = onOpenSettings
        )
        ActionButton(
            title = MediaData.actionButtons[2],
            icon = Icons.Outlined.PlayArrow,
            onClick = {}
        )
    }
}

@Composable
private fun ActionButton(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Icon(imageVector = icon, contentDescription = title)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = title)
    }
}

@Composable
private fun SectionHeader(
    title: String,
    onClick: () -> Unit,
    forceLight: Boolean = false
) {
    val contentColor = if (forceLight) Color.White else MaterialTheme.colorScheme.onBackground
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = contentColor
        )
        AssistChip(
            onClick = onClick,
            label = { Text("View All") },
            colors = if (forceLight) {
                AssistChipDefaults.assistChipColors(
                    containerColor = Color.White,
                    labelColor = Color.Black
                )
            } else {
                AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                    labelColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        )
    }
}

@Composable
private fun HeroCard(
    hero: HeroCardData,
    overlay: HeroTextOverlay?,
    gradient: Brush,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(gradient)
        ) {
            AsyncImage(
                model = hero.imageUrl,
                contentDescription = hero.title,
                modifier = Modifier.fillMaxSize().alpha(0.85f),
                contentScale = ContentScale.Crop
            )
            if (overlay != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    if (overlay.emoji.isNotEmpty()) {
                        Text(text = overlay.emoji, color = Color.White)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Text(
                        text = overlay.line1,
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall
                    )
                    if (overlay.line2.isNotEmpty()) {
                        Text(
                            text = overlay.line2,
                            color = Color.White,
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DescriptionWithRail(
    description: String,
    cards: List<MediaCard>,
    onCardClick: (MediaCard) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        SquareCardRail(
            cards = cards,
            modifier = Modifier.weight(1f),
            onCardClick = onCardClick
        )
    }
}

@Composable
private fun AlbumRail(cards: List<WorshipAlbum>, onCardClick: (Int) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(cards.size) { index ->
            val album = cards[index]
            Card(
                modifier = Modifier
                    .width(140.dp)
                    .clickable { onCardClick(album.id) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                if (album.imageUrl != null) {
                    AsyncImage(
                        model = album.imageUrl,
                        contentDescription = album.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(Color(album.fallbackColor)),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(text = album.title, color = Color.White, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
private fun SquareCardRail(
    cards: List<MediaCard>,
    modifier: Modifier = Modifier,
    onCardClick: (MediaCard) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(cards.size) { index ->
            val card = cards[index]
            Card(
                modifier = Modifier
                    .width(140.dp)
                    .clickable { onCardClick(card) },
                shape = RoundedCornerShape(12.dp)
            ) {
                AsyncImage(
                    model = card.imageUrl,
                    contentDescription = card.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    card.title,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
