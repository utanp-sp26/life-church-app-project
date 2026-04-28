package edu.utap.life_church_app.navigation

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
