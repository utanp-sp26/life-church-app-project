package edu.utap.life_church_app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import edu.utap.life_church_app.ui.theme.Life_church_appTheme
import java.util.Calendar
import kotlinx.coroutines.delay

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
                    ThemedNonHomePage {
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
                }
                composable(AppRoute.MediaMessages.pattern) {
                    ThemedNonHomePage {
                        MessagesPage(onOpenVideo = { id -> navController.navigate(AppRoute.VideoPlayer.create(id.toString())) })
                    }
                }
                composable(AppRoute.MediaWorship.pattern) {
                    ThemedNonHomePage {
                        WorshipPage(onOpenAlbum = { id -> navController.navigate(AppRoute.WorshipAlbum.create(id.toString())) })
                    }
                }
                composable(
                    route = AppRoute.WorshipAlbum.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    ThemedNonHomePage {
                        WorshipAlbumPage(id = id, onPlaySong = { songId ->
                            navController.navigate(AppRoute.AudioPlayer.create(songId.toString()))
                        })
                    }
                }
                composable(AppRoute.MediaStories.pattern) {
                    ThemedNonHomePage {
                        StoriesPage(onOpenVideo = { id -> navController.navigate(AppRoute.VideoPlayer.create(id.toString())) })
                    }
                }
                composable(AppRoute.MediaLeadershipPodcast.pattern) {
                    ThemedNonHomePage {
                        LeadershipPodcastPage(onOpenAudio = { id -> navController.navigate(AppRoute.AudioPlayer.create(id.toString())) })
                    }
                }
                composable(AppRoute.MediaLifeGroups.pattern) {
                    ThemedNonHomePage { LifeGroupsViewAllPage() }
                }
                composable(AppRoute.MediaSettings.pattern) {
                    ThemedNonHomePage { MediaSettingsPage() }
                }
                composable(
                    route = AppRoute.AudioPlayer.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    ThemedNonHomePage { AudioPlayerPage(id = id) }
                }
                composable(
                    route = AppRoute.VideoPlayer.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    ThemedNonHomePage {
                        VideoPlayerPage(id = id, onOpenVideo = { nextId ->
                            navController.navigate(AppRoute.VideoPlayer.create(nextId.toString()))
                        })
                    }
                }
                composable(AppRoute.LifeGroups.pattern) {
                    ThemedNonHomePage {
                        LifeGroupsPage(onFindLifeGroup = { navController.navigate(AppRoute.FindLifeGroup.pattern) })
                    }
                }
                composable(AppRoute.FindLifeGroup.pattern) {
                    ThemedNonHomePage { FindLifeGroupPage() }
                }
                composable(AppRoute.Serving.pattern) {
                    ThemedNonHomePage { ServingPage() }
                }
                composable(AppRoute.Giving.pattern) {
                    ThemedNonHomePage { GivingPage() }
                }
                composable(
                    route = AppRoute.StoryDetail.pattern,
                    arguments = listOf(navArgument("id") { type = NavType.StringType }),
                ) { backStack ->
                    val id = backStack.arguments?.getString("id").orEmpty()
                    ThemedNonHomePage { StoriesDetailPage(id = id) }
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

@Composable
private fun ThemedNonHomePage(content: @Composable () -> Unit) {
    var darkTheme by remember { mutableStateOf(isNightTime()) }

    // Matches web ThemeContext behavior: recompute every minute.
    LaunchedEffect(Unit) {
        while (true) {
            darkTheme = isNightTime()
            delay(60_000)
        }
    }

    Life_church_appTheme(
        darkTheme = darkTheme,
        dynamicColor = false,
        content = content
    )
}

private fun isNightTime(): Boolean {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return hour < 6 || hour >= 18
}
