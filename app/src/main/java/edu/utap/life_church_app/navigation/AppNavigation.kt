package edu.utap.life_church_app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.utap.life_church_app.ui.common.PlaceholderScreen
import edu.utap.life_church_app.ui.media.MediaPage
import edu.utap.life_church_app.ui.pages.home.HomePage
import edu.utap.life_church_app.ui.shell.FullScreenMenuDrawer
import edu.utap.life_church_app.ui.shell.LifeBottomBar
import edu.utap.life_church_app.navigation.BottomTab

/**
 * **Single owner** of the root [NavHost] and app shell (see [AppRoute] KDoc). Add new
 * destinations by appending a [composable] block; see [AGENTS.md] for multi-agent rules.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var menuOpen by remember { mutableStateOf(false) }
    var openPrayerFromMenu by remember { mutableStateOf(false) }
    val navBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStack?.destination?.route

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                LifeBottomBar(
                    currentRoute = currentRoute,
                    onSelect = { tab ->
                        val target = tab.appRoute.pattern
                        navController.navigate(target) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            },
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = AppRoute.Home.pattern,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                composable(AppRoute.Home.pattern) {
                    HomePage(
                        onOpenMenu = { menuOpen = true },
                        openPrayerFromMenu = openPrayerFromMenu,
                        onConsumeOpenPrayerFromMenu = { openPrayerFromMenu = false },
                    )
                }
                composable(AppRoute.Media.pattern) { MediaPage() }
                composable(AppRoute.MediaMessages.pattern) { PlaceholderScreen("Messages") }
                composable(AppRoute.MediaWorship.pattern) { PlaceholderScreen("Worship") }
                composable(
                    route = AppRoute.WorshipAlbum.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    PlaceholderScreen("Worship album: $id")
                }
                composable(AppRoute.MediaStories.pattern) { PlaceholderScreen("Stories") }
                composable(AppRoute.MediaLeadershipPodcast.pattern) { PlaceholderScreen("Leadership podcast") }
                composable(AppRoute.MediaLifeGroups.pattern) { PlaceholderScreen("Media — Life groups") }
                composable(AppRoute.MediaSettings.pattern) { PlaceholderScreen("Media settings") }
                composable(
                    route = AppRoute.AudioPlayer.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    PlaceholderScreen("Audio: $id")
                }
                composable(
                    route = AppRoute.VideoPlayer.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    PlaceholderScreen("Video: $id")
                }
                composable(AppRoute.LifeGroups.pattern) { PlaceholderScreen("Life groups") }
                composable(AppRoute.FindLifeGroup.pattern) { PlaceholderScreen("Find a life group") }
                composable(AppRoute.Serving.pattern) { PlaceholderScreen("Serving") }
                composable(AppRoute.Giving.pattern) { PlaceholderScreen("Giving") }
                composable(
                    route = AppRoute.StoryDetail.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    PlaceholderScreen("Story: $id")
                }
            }
        }

        if (menuOpen) {
            FullScreenMenuDrawer(
                onClose = { menuOpen = false },
                onPrayerClick = {
                    menuOpen = false
                    openPrayerFromMenu = true
                    if (currentRoute != AppRoute.Home.pattern) {
                        navController.navigate(AppRoute.Home.pattern) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
            )
        }
    }
}
