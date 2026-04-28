package edu.utap.life_church_app.ui.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import edu.utap.life_church_app.ui.theme.LifeInputBackground
import edu.utap.life_church_app.ui.theme.LifeMutedForeground

/**
 * Full-screen menu (port of web `MenuDrawer`: scrim + full-height scroll surface).
 */
@Composable
fun FullScreenMenuDrawer(
    onClose: () -> Unit,
    onPrayerClick: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = onClose),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LifeInputBackground)
                .clickable(enabled = false) { },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 4.dp, vertical = 8.dp),
            ) {
                IconButton(onClick = onClose, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(Icons.Default.Close, contentDescription = "Close menu")
                }
                AsyncImage(
                    model = "https://www.life.church/assets/img/logo-full-dark.svg",
                    contentDescription = "Life.Church",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(24.dp)
                        .width(200.dp),
                    contentScale = ContentScale.Fit,
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .navigationBarsPadding(),
            ) {
                CollapsedSectionCard(
                    title = "New Here?",
                    subtitle = "Get to know Life.Church",
                ) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    ) {
                        Box(Modifier.fillMaxWidth().height(200.dp)) {
                            AsyncImage(
                                model = "https://images.unsplash.com/photo-1631478650929-5704a12152ce?w=1080",
                                contentDescription = null,
                                modifier = Modifier.matchParentSize(),
                                contentScale = ContentScale.Crop,
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color.Transparent, Color(0xB3000000)),
                                        ),
                                    ),
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    "No matter who you are, you're\nwelcome here.",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "Learn More",
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .border(2.dp, Color.White, RoundedCornerShape(50))
                                        .padding(horizontal = 20.dp, vertical = 8.dp),
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                SimpleSectionCard("Next Steps", "Ready for what's next?")
                Spacer(Modifier.height(12.dp))
                SimpleSectionCard("Tools for Growth", "Take your faith deeper")
                Spacer(Modifier.height(12.dp))
                SimpleSectionCard("Missions", "Bring hope and healing")
                Spacer(Modifier.height(12.dp))
                SimpleSectionCard("Careers", "Bring your skills to Life")
                Spacer(Modifier.height(16.dp))
                PrayerCalloutCard(onPrayerClick = onPrayerClick)
            }
        }
    }
}

@Composable
private fun CollapsedSectionCard(
    title: String,
    subtitle: String,
    extraContent: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, color = LifeMutedForeground, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(12.dp))
            extraContent()
        }
    }
}

@Composable
private fun SimpleSectionCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, color = LifeMutedForeground, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun PrayerCalloutCard(onPrayerClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        Box(Modifier.fillMaxWidth().height(220.dp)) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1763361676562-720e21e8e4e9?w=1080",
                contentDescription = "Prayer",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xB3000000)),
                        ),
                    ),
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "How can we pray for you?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    "Ask for Prayer",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .border(2.dp, Color.White, RoundedCornerShape(50))
                        .clickable { onPrayerClick() }
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                )
            }
        }
    }
}
