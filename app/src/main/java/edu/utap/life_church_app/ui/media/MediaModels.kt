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

data class HeroCardData(
    val title: String,
    val imageUrl: String
)

data class HeroTextOverlay(
    val emoji: String = "",
    val line1: String,
    val line2: String = ""
)

object MediaData {
    val actionButtons = listOf(
        "My Downloads",
        "Media Settings",
        "More Ways to Watch"
    )

    val messagesHeroOverlay = HeroTextOverlay(
        emoji = "🕊️",
        line1 = "Jesus",
        line2 = "Always"
    )

    val messagesHero = HeroCardData(
        title = "Jesus Always",
        imageUrl = "https://images.unsplash.com/photo-1731102413744-390432dd5c3e?w=1080"
    )

    val messagesDescription =
        "What if the Jesus story is bigger than you thought? Jesus Always looks back to reveal " +
            "He was there from the very beginning and that He is just as present today, in whatever " +
            "you are facing right now."

    val messages = listOf(
        MediaCard(1, "Jesus Always - Part 1", "https://images.unsplash.com/photo-1502598919583-c7374009d759?w=800"),
        MediaCard(2, "Jesus Always - Part 2", "https://images.unsplash.com/photo-1438232992991-995b7058bbb3?w=800"),
        MediaCard(3, "Jesus Always - Part 3", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=800"),
        MediaCard(4, "Jesus Always - Part 4", "https://images.unsplash.com/photo-1519074069444-1ba4fff66d16?w=800"),
        MediaCard(5, "Jesus Always - Part 5", "https://images.unsplash.com/photo-1528605248644-14dd04022da1?w=800"),
        MediaCard(6, "Jesus Always - Part 6", "https://images.unsplash.com/photo-1499209974431-9dddcece7f88?w=800")
    )

    val worshipDescription = "A collection of worship songs and albums."

    val worshipAlbums = listOf(
        WorshipAlbum(1, "El Camino", "https://images.unsplash.com/photo-1630467267476-c67b34ffc837?w=800"),
        WorshipAlbum(2, "Digno De Alabar", "https://images.unsplash.com/photo-1470229538611-16ba8c7ffbd7?w=800"),
        WorshipAlbum(3, "Tanto", "https://images.unsplash.com/photo-1501612780327-45045538702b?w=800"),
        WorshipAlbum(4, "Here As In Heaven", "https://images.unsplash.com/photo-1510915228340-29c85a43dcfe?w=800"),
        WorshipAlbum(5, "The Belonging Co", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?w=800"),
        WorshipAlbum(6, "Graves Into Gardens", "https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=800")
    )

    val lifeGroupsDescription =
        "We were meant to grow, laugh, and serve together. Here, you will find videos and " +
            "discussion tools to help your LifeGroup connect with God and each other."

    val lifeGroups = listOf(
        MediaCard(1, "Sisters 2025: Draw Near", "https://images.unsplash.com/photo-1651514645899-de1cadd1ec31?w=800"),
        MediaCard(2, "Marriage Night", "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=800"),
        MediaCard(3, "Men's Group Study", "https://images.unsplash.com/photo-1529070538774-1843cb3265df?w=800"),
        MediaCard(4, "Young Adults Connect", "https://images.unsplash.com/photo-1511632765486-a01980e01a18?w=800"),
        MediaCard(5, "Parents & Kids", "https://images.unsplash.com/photo-1541845157-a6d2d100c931?w=800"),
        MediaCard(6, "Recovery & Hope", "https://images.unsplash.com/photo-1521791055366-0d553872125f?w=800")
    )

    val storiesDescription =
        "We are all on a journey together, and you are part of the Life.Church story. " +
            "See amazing ways God is working through your church."

    val stories = listOf(
        MediaCard(1, "Looking Different Is Okay", "https://images.unsplash.com/photo-1747214300321-4b4993dc4ac4?w=800"),
        MediaCard(2, "From Addiction to Freedom", "https://images.unsplash.com/photo-1552058544-f2b08422138a?w=800"),
        MediaCard(3, "Finding Hope Again", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=800"),
        MediaCard(4, "A Second Chance", "https://images.unsplash.com/photo-1531746020798-e6953c6e8e04?w=800"),
        MediaCard(5, "Restored Relationships", "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=800"),
        MediaCard(6, "God's Perfect Timing", "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=800")
    )

    val leadershipPodcastHero = HeroCardData(
        title = "Craig Groeschel Leadership Podcast",
        imageUrl = "https://images.unsplash.com/photo-1772396719557-5743c644877f?w=1080"
    )

    val leadershipPodcastDescription =
        "Leadership. It's in Your DNA. It's the way God created you. You don't have to know it " +
            "all to be a great leader! In this podcast, you'll get timely insights from Pastor " +
            "Craig and his guests to make the most of your leadership potential."

    val podcastEpisodes = listOf(
        PodcastEpisode(
            192,
            "Cognitive Neuroscientist: The Formula to Rewire Your Mind | Dr. Caroline Leaf",
            "https://images.unsplash.com/photo-1688039763740-9036cb5d566e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwb2RjYXN0JTIwZXBpc29kZSUyMG1pY3JvcGhvbmUlMjBzdHVkaW98ZW58MXx8fHwxNzc1NzUxODY0fDA&ixlib=rb-4.1.0&q=80&w=1080"
        ),
        PodcastEpisode(
            191,
            "Right Person, Wrong Seat",
            "https://images.unsplash.com/photo-1772396719557-5743c644877f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjcmFpZyUyMGdyb2VzY2hlbCUyMHBvZGNhc3QlMjBsZWFkZXJzaGlwfGVufDF8fHx8MTc3NTc1MTg2NHww&ixlib=rb-4.1.0&q=80&w=1080"
        ),
        PodcastEpisode(
            190,
            "NBA Psychologist: The Secret to Thriving Under Pressure | Dr. Wayne Chappelle",
            "https://images.unsplash.com/photo-1694881227503-ddc502d4b4cc?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwYXN0b3IlMjBzcGVha2luZyUyMHBvZGNhc3QlMjBtaWNyb3Bob25lfGVufDF8fHx8MTc3NTc1MTMyOXww&ixlib=rb-4.1.0&q=80&w=1080"
        ),
        PodcastEpisode(
            189,
            "How to Stay Motivated When Things Get Hard",
            "https://images.unsplash.com/photo-1630467267476-c67b34ffc837?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3b3JzaGlwJTIwY29uY2VydCUyMGNyb3dkJTIwaGFuZHN8ZW58MXx8fHwxNzc1NzUxMzI5fDA&ixlib=rb-4.1.0&q=80&w=1080"
        ),
        PodcastEpisode(
            188,
            "The Leadership Habit You Need to Develop",
            "https://images.unsplash.com/photo-1651514645899-de1cadd1ec31?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzbWFsbCUyMGdyb3VwJTIwYmlibGUlMjBzdHVkeSUyMGNvbW11bml0eXxlbnwxfHx8fDE3NzU3NTEzMzB8MA&ixlib=rb-4.1.0&q=80&w=1080"
        ),
        PodcastEpisode(
            187,
            "Building a Culture of Excellence",
            "https://images.unsplash.com/photo-1747214300321-4b4993dc4ac4?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJzb24lMjB0ZXN0aW1vbnklMjBwb3J0cmFpdCUyMHN0b3J5fGVufDF8fHx8MTc3NTc1MTMzMHww&ixlib=rb-4.1.0&q=80&w=1080"
        )
    )
}
