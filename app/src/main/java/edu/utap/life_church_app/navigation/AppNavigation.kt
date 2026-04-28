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
import edu.utap.life_church_app.ui.giving.GivingPage
import edu.utap.life_church_app.ui.lifegroups.FindLifeGroupPage
import edu.utap.life_church_app.ui.lifegroups.LifeGroupsPage
import edu.utap.life_church_app.ui.lifegroups.LifeGroupsViewAllPage
import edu.utap.life_church_app.ui.media.MediaPage
import edu.utap.life_church_app.ui.media.messages.MessagesPage
import edu.utap.life_church_app.ui.media.player.AudioPlayerPage
import edu.utap.life_church_app.ui.media.player.VideoPlayerPage
import edu.utap.life_church_app.ui.media.podcast.LeadershipPodcastPage
import edu.utap.life_church_app.ui.media.settings.MediaSettingsPage
import edu.utap.life_church_app.ui.media.stories.StoriesDetailPage
import edu.utap.life_church_app.ui.media.stories.StoriesPage
import edu.utap.life_church_app.ui.media.worship.WorshipAlbumPage
import edu.utap.life_church_app.ui.media.worship.WorshipPage
import edu.utap.life_church_app.ui.pages.home.HomePage
import edu.utap.life_church_app.ui.shell.FullScreenMenuDrawer
import edu.utap.life_church_app.ui.shell.LifeBottomBar
import edu.utap.life_church_app.navigation.BottomTab
import edu.utap.life_church_app.ui.serving.ServingPage

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
                composable(AppRoute.Media.pattern) {
                    MediaPage(
                        onOpenMessages = { navController.navigate(AppRoute.MediaMessages.pattern) },
                        onOpenWorship = { navController.navigate(AppRoute.MediaWorship.pattern) },
                        onOpenStories = { navController.navigate(AppRoute.MediaStories.pattern) },
                        onOpenLeadershipPodcast = { navController.navigate(AppRoute.MediaLeadershipPodcast.pattern) },
                        onOpenLifeGroups = { navController.navigate(AppRoute.MediaLifeGroups.pattern) },
                        onOpenSettings = { navController.navigate(AppRoute.MediaSettings.pattern) },
                        onOpenVideo = { id -> navController.navigate(AppRoute.VideoPlayer.create(id.toString())) },
                        onOpenAudio = { id -> navController.navigate(AppRoute.AudioPlayer.create(id.toString())) },
                        onOpenWorshipAlbum = { id -> navController.navigate(AppRoute.WorshipAlbum.create(id.toString())) }
                    )
                }
                composable(AppRoute.MediaMessages.pattern) {
                    MessagesPage(onOpenVideo = { id -> navController.navigate(AppRoute.VideoPlayer.create(id.toString())) })
                }
                composable(AppRoute.MediaWorship.pattern) {
                    WorshipPage(onOpenAlbum = { id -> navController.navigate(AppRoute.WorshipAlbum.create(id.toString())) })
                }
                composable(
                    route = AppRoute.WorshipAlbum.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    WorshipAlbumPage(id = id, onPlaySong = { songId ->
                        navController.navigate(AppRoute.AudioPlayer.create(songId.toString()))
                    })
                }
                composable(AppRoute.MediaStories.pattern) {
                    StoriesPage(onOpenVideo = { id -> navController.navigate(AppRoute.VideoPlayer.create(id.toString())) })
                }
                composable(AppRoute.MediaLeadershipPodcast.pattern) {
                    LeadershipPodcastPage(onOpenAudio = { id -> navController.navigate(AppRoute.AudioPlayer.create(id.toString())) })
                }
                composable(AppRoute.MediaLifeGroups.pattern) { LifeGroupsViewAllPage() }
                composable(AppRoute.MediaSettings.pattern) { MediaSettingsPage() }
                composable(
                    route = AppRoute.AudioPlayer.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    AudioPlayerPage(id = id)
                }
                composable(
                    route = AppRoute.VideoPlayer.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    VideoPlayerPage(id = id, onOpenVideo = { nextId ->
                        navController.navigate(AppRoute.VideoPlayer.create(nextId.toString()))
                    })
                }
                composable(AppRoute.LifeGroups.pattern) {
                    LifeGroupsPage(onFindLifeGroup = { navController.navigate(AppRoute.FindLifeGroup.pattern) })
                }
                composable(AppRoute.FindLifeGroup.pattern) { FindLifeGroupPage() }
                composable(AppRoute.Serving.pattern) { ServingPage() }
                composable(AppRoute.Giving.pattern) { GivingPage() }
                composable(
                    route = AppRoute.StoryDetail.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    StoriesDetailPage(id = id)
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
