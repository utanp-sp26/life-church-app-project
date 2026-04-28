package edu.utap.life_church_app.ui.serving

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ServingPage() {
    var expandedStep by remember { mutableIntStateOf(1) }
    val steps = listOf(
        ServingStep(
            title = "Fill Out the Interest Form",
            body = "Let us know which team you want to serve with.",
            showPrimaryAction = true
        ),
        ServingStep(
            title = "Complete a Background Check",
            body = "For the safety of our community, all volunteers must complete a background check."
        ),
        ServingStep(
            title = "Attend Orientation",
            body = "Learn more about serving at Life.Church and get connected with your team."
        ),
        ServingStep(
            title = "Start Serving",
            body = "You're ready to start making a difference! Sign up for your first shift."
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                FilledIconButton(
                    onClick = {},
                    modifier = Modifier.size(44.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Text(
                    text = "Start Serving",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "How to Get Started",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        item {
            Box {
                Box(
                    modifier = Modifier
                        .offset(x = 19.dp, y = 8.dp)
                        .height((steps.size * 122).dp)
                        .width(2.dp)
                        .background(Color(0xFFE5E7EB))
                )
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    steps.forEachIndexed { index, step ->
                        val stepNumber = index + 1
                        ServingStepCard(
                            stepNumber = stepNumber,
                            step = step,
                            expanded = expandedStep == stepNumber,
                            onToggle = {
                                expandedStep = if (expandedStep == stepNumber) 0 else stepNumber
                            }
                        )
                    }
                }
            }
        }
        item {
            Text(
                text = "More Resources",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ResourceCardRow(
                    icon = Icons.Default.Message,
                    label = "Connect With Our Team"
                )
                ResourceCardRow(
                    icon = Icons.Default.PersonAdd,
                    label = "Invite a Friend to Serve"
                )
            }
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun ServingStepCard(
    stepNumber: Int,
    step: ServingStep,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (stepNumber == 1) Color(0xFF2563EB) else Color(0xFF111111),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stepNumber.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = step.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (expanded) {
                        Text(
                            text = step.body,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (step.showPrimaryAction) {
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(100.dp)
                            ) {
                                Text("Get Started")
                            }
                        }
                    }
                }
            }
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded) "Collapse step" else "Expand step",
                tint = Color(0xFF9CA3AF)
            )
        }
    }
}

@Composable
private fun ResourceCardRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        TextButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFF9CA3AF)
                )
            }
        }
    }
}

private data class ServingStep(
    val title: String,
    val body: String,
    val showPrimaryAction: Boolean = false
)
