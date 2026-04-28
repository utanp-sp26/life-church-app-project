package edu.utap.life_church_app.ui.shell

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import edu.utap.life_church_app.navigation.AppRoute
import edu.utap.life_church_app.navigation.BottomTab
import edu.utap.life_church_app.ui.theme.LifeMutedForeground

@Composable
fun LifeBottomBar(
    currentRoute: String?,
    onSelect: (BottomTab) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
    ) {
        BottomTab.entries.forEach { tab ->
            val selected = isTabSelected(tab, currentRoute)
            val icon = when (tab) {
                BottomTab.Home -> Icons.Default.Home
                BottomTab.Media -> Icons.Default.PlayArrow
                BottomTab.LifeGroups -> Icons.Default.Groups
                BottomTab.Serving -> Icons.Default.Handyman
                BottomTab.Giving -> Icons.Default.Favorite
            }
            NavigationBarItem(
                selected = selected,
                onClick = { onSelect(tab) },
                icon = { Icon(icon, contentDescription = tab.label) },
                label = { Text(tab.label, style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onBackground,
                    selectedTextColor = MaterialTheme.colorScheme.onBackground,
                    unselectedIconColor = LifeMutedForeground,
                    unselectedTextColor = LifeMutedForeground,
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
            )
        }
    }
}

fun isTabSelected(tab: BottomTab, current: String?): Boolean {
    if (current == null) return false
    return when (tab) {
        BottomTab.Home -> current == AppRoute.Home.pattern
        BottomTab.Media -> current == AppRoute.Media.pattern || current.startsWith("media/")
        BottomTab.LifeGroups -> current == AppRoute.LifeGroups.pattern || current.startsWith("lifegroups/")
        BottomTab.Serving -> current == AppRoute.Serving.pattern
        BottomTab.Giving -> current == AppRoute.Giving.pattern
    }
}
