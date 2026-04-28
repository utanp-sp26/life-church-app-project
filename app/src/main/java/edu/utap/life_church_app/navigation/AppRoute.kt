package edu.utap.life_church_app.navigation

/**
 * **Multi-agent contract** (agree before adding screens or parallel work):
 *
 * - **Single `NavHost` owner**: [AppNavigation] is the only file that defines the root
 *   [androidx.navigation.compose.NavHost] and registers [androidx.navigation.compose.composable]
 *   entries. Other agents add one `composable(...)` block per route (or a nav owner adds
 *   TODO stubs first) to avoid merge conflicts.
 * - **Per-feature packages** (no overlap between agents):
 *   - `edu.utap.life_church_app.ui.pages.home` — Home
 *   - `...ui.media` — Media, messages, worship, players, settings, …
 *   - `...ui.lifegroups` or `...ui.groups` — Life groups, find, view all
 *   - `...ui.serving` — Serving
 *   - `...ui.giving` — Giving
 *   - `...ui.stories` (or `...ui.pages` if you keep a pages tree) — story detail
 *   - Shared: `...ui.common` / `...ui.components` when a second feature needs the same composable
 * - **Foundation merge order**: land `ui/theme/*` + this navigation shell on the main branch
 *   before parallel page ports so `AppRoute` and `AppNavigation` stay consistent.
 *
 * Route strings mirror the web app’s `routes.tsx` order (path segments
 * under the root layout, without a leading slash in the graph for top-level segments).
 */
sealed class AppRoute(val pattern: String) {

    data object Home : AppRoute("home")
    data object Media : AppRoute("media")
    data object MediaMessages : AppRoute("media/messages")
    data object MediaWorship : AppRoute("media/worship")
    data object MediaStories : AppRoute("media/stories")
    data object MediaLeadershipPodcast : AppRoute("media/leadership-podcast")
    data object MediaLifeGroups : AppRoute("media/lifegroups")
    data object MediaSettings : AppRoute("media/settings")
    data object WorshipAlbum : AppRoute("media/worship/album/{id}") {
        fun create(id: String) = "media/worship/album/$id"
    }
    data object AudioPlayer : AppRoute("media/player/audio/{id}") {
        fun create(id: String) = "media/player/audio/$id"
    }
    data object VideoPlayer : AppRoute("media/player/video/{id}") {
        fun create(id: String) = "media/player/video/$id"
    }
    data object LifeGroups : AppRoute("lifegroups")
    data object FindLifeGroup : AppRoute("lifegroups/find")
    data object Serving : AppRoute("serving")
    data object Giving : AppRoute("giving")
    data object StoryDetail : AppRoute("stories/{id}") {
        fun create(id: String) = "stories/$id"
    }
}

/** Bottom navigation: same five roots as the web app’s `BottomNav` (Home, Media, LifeGroups, Serving, Giving). */
enum class BottomTab(
    val appRoute: AppRoute,
    val label: String,
) {
    Home(AppRoute.Home, "Home"),
    Media(AppRoute.Media, "Media"),
    LifeGroups(AppRoute.LifeGroups, "LifeGroups"),
    Serving(AppRoute.Serving, "Serving"),
    Giving(AppRoute.Giving, "Giving"),
}
