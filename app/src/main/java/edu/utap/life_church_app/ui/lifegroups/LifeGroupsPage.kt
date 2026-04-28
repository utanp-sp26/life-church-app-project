package edu.utap.life_church_app.ui.lifegroups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PlayCircle
import coil.compose.AsyncImage

@Composable
fun LifeGroupsPage(
    onFindLifeGroup: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("LifeGroups", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1769672774209-73ce5964bced?w=1000",
                    contentDescription = "Meaningful friendships",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Meaningful Friendships Start Here.", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("Find your people and discover a group that fits your season.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Button(onClick = onFindLifeGroup, modifier = Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.Groups, contentDescription = null)
                            Text("Find a LifeGroup")
                        }
                    }
                    OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.PersonAdd, contentDescription = null)
                            Text("Start a LifeGroup")
                        }
                    }
                }
            }
        }

        Text("LifeGroup Resources", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        ResourceRow(
            title = "What to Expect",
            subtitle = "Learn how LifeGroups work and what your first gathering looks like.",
            icon = { Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Color(0xFF6B7280)) }
        )
        ResourceRow(
            title = "Talk It Over",
            subtitle = "Conversation starters and guides for group discussion.",
            icon = { Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color(0xFF6B7280)) }
        )
    }
}

@Composable
private fun ResourceRow(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit
) {
    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFFF3F4F6), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(Modifier.size(4.dp))
        }
    }
}
