package edu.utap.life_church_app.ui.media

data class MediaCard(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val subtitle: String = ""
)

data class WorshipAlbum(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val fallbackColor: Long = 0xFF6C4AB6
)

data class PodcastEpisode(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val duration: String = "24:32"
)

object MediaData {
    val messages = listOf(
        MediaCard(1, "Jesus Always", "https://images.unsplash.com/photo-1731102413744-390432dd5c3e?w=800"),
        MediaCard(2, "How to Pray", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=800"),
        MediaCard(3, "Heal Your Mind", "https://images.unsplash.com/photo-1502598919583-c7374009d759?w=800"),
        MediaCard(4, "A Church Picks Up Where God Left Off", "https://images.unsplash.com/photo-1438232992991-995b7058bbb3?w=800"),
        MediaCard(5, "More Days Than Not", "https://images.unsplash.com/photo-1519074069444-1ba4fff66d16?w=800"),
        MediaCard(6, "He Will Be Called", "https://images.unsplash.com/photo-1528605248644-14dd04022da1?w=800")
    )

    val worshipAlbums = listOf(
        WorshipAlbum(1, "El Camino", "https://images.unsplash.com/photo-1630467267476-c67b34ffc837?w=800"),
        WorshipAlbum(2, "Digno De Alabar", "https://images.unsplash.com/photo-1470229538611-16ba8c7ffbd7?w=800"),
        WorshipAlbum(3, "Tanto", null, 0xFF3A1078),
        WorshipAlbum(4, "Upside Down", null, 0xFFEDB7ED)
    )

    val stories = listOf(
        MediaCard(1, "Looking Different Is Okay | Sarah's Story", "https://images.unsplash.com/photo-1747214300321-4b4993dc4ac4?w=800"),
        MediaCard(2, "Transforming Our Cities", "https://images.unsplash.com/photo-1651514645899-de1cadd1ec31?w=800"),
        MediaCard(3, "I Had to Get Back to Church | Donna's Story", "https://images.unsplash.com/photo-1644514539002-2a6baa243564?w=800"),
        MediaCard(4, "God Is at Work | Year in Review 2025", "https://images.unsplash.com/photo-1502598919583-c7374009d759?w=800")
    )

    val podcastEpisodes = listOf(
        PodcastEpisode(192, "Cognitive Neuroscientist: The Formula to Rewire Your Mind", "https://images.unsplash.com/photo-1688039763740-9036cb5d566e?w=800"),
        PodcastEpisode(191, "Right Person, Wrong Seat", "https://images.unsplash.com/photo-1772396719557-5743c644877f?w=800"),
        PodcastEpisode(190, "NBA Psychologist: Thriving Under Pressure", "https://images.unsplash.com/photo-1694881227503-ddc502d4b4cc?w=800"),
        PodcastEpisode(189, "How to Stay Motivated", "https://images.unsplash.com/photo-1630467267476-c67b34ffc837?w=800")
    )

    val lifeGroups = listOf(
        MediaCard(1, "Sisters 2025: Draw Near", "https://images.unsplash.com/photo-1651514645899-de1cadd1ec31?w=800"),
        MediaCard(2, "Marriage Night", "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=800"),
        MediaCard(3, "Young Adults Connect", "https://images.unsplash.com/photo-1511632765486-a01980e01a18?w=800"),
        MediaCard(4, "Recovery & Hope", "https://images.unsplash.com/photo-1521791055366-0d553872125f?w=800")
    )
}
