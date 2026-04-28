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
    val fallbackColor: Long = 0xFF6C4AB6,
    val label: String? = null,
    val textColor: Long? = null
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
        MediaCard(1, "Jesus Always", "https://images.unsplash.com/photo-1731102413744-390432dd5c3e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxtb3VudGFpbiUyMG9jZWFuJTIwY2xpZmYlMjBKZXN1cyUyMEFsd2F5c3xlbnwxfHx8fDE3NzU3NTEzMzB8MA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(2, "How to Pray", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjaHVyY2glMjBzZXJ2aWNlJTIwcGFzdG9yfGVufDF8fHx8MTc3NTc2MjkwOHww&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(3, "Heal Your Mind", "https://images.unsplash.com/photo-1502598919583-c7374009d759?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwYXN0b3IlMjBwcmVhY2hpbmclMjBzZXJtb24lMjBtb3VudGFpbnN8ZW58MXx8fHwxNzc1NzUwMjQ4fDA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(4, "A Church Picks Up Where God Left Off", "https://images.unsplash.com/photo-1438232992991-995b7058bbb3?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwYXN0b3IlMjBwcmVhY2hpbmclMjBiaWJsZXxlbnwxfHx8fDE3NzU3NjI5MDd8MA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(5, "More Days Than Not", "https://images.unsplash.com/photo-1519074069444-1ba4fff66d16?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjaHVyY2glMjBwcmVhY2hlciUyMHNlcm1vbnxlbnwxfHx8fDE3NzU3NjI5MDl8MA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(6, "He Will Be Called", "https://images.unsplash.com/photo-1528605248644-14dd04022da1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzcGVha2VyJTIwcHJlYWNoaW5nJTIwY2h1cmNofGVufDF8fHx8MTc3NTc2MjkxMHww&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(7, "This Is What We Do", "https://images.unsplash.com/photo-1499209974431-9dddcece7f88?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxtaW5pc3RyeSUyMHNlcnZpY2UlMjBjaHVyY2h8ZW58MXx8fHwxNzc1NzYyOTExfDA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(8, "From One to a Billion", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3b3JzaGlwJTIwc29uZ3xlbnwxfHx8fDE3NzU3NjI5MDN8MA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(9, "Connected Life", "https://images.unsplash.com/photo-1470229538611-16ba8c7ffbd7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3b3JzaGlwJTIwbXVzaWMlMjBhbGJ1bXxlbnwxfHx8fDE3NzU3NjI5MDB8MA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(10, "Living In The Last Days", "https://images.unsplash.com/photo-1501612780327-45045538702b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3b3JzaGlwJTIwYmFuZCUyMHBlcmZvcm1hbmNlfGVufDF8fHx8MTc3NTc2MjkwMXww&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(11, "Faith Over Fear", "https://images.unsplash.com/photo-1510915228340-29c85a43dcfe?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjaHVyY2glMjB3b3JzaGlwJTIwc2luZ2luZ3xlbnwxfHx8fDE3NzU3NjI5MDJ8MA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(12, "The Belonging", "https://images.unsplash.com/photo-1459749411175-04bf5292ceea?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjaHVyY2glMjBjaG9pciUyMHNpbmdpbmd8ZW58MXx8fHwxNzc1NzYyOTA0fDA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(13, "Goodness of God", "https://images.unsplash.com/photo-1415886541506-6efc5e4b1786?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjaHJpc3RpYW4lMjB3b3JzaGlwJTIwY29uY2VydHxlbnwxfHx8fDE3NzU3NjI5MDV8MA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(14, "Living Hope", "https://images.unsplash.com/photo-1506157786151-b8491531f063?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwcmFpc2UlMjB3b3JzaGlwJTIwc2luZ2VyfGVufDF8fHx8MTc3NTc2MjkwNnww&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(15, "No Turning Back", "https://images.unsplash.com/photo-1630467267476-c67b34ffc837?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3b3JzaGlwJTIwY29uY2VydCUyMGNyb3dkJTIwaGFuZHN8ZW58MXx8fHwxNzc1NzUxMzI5fDA&ixlib=rb-4.1.0&q=80&w=1080"),
        MediaCard(16, "The Victory Is Won", "https://images.unsplash.com/photo-1651514645899-de1cadd1ec31?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzbWFsbCUyMGdyb3VwJTIwYmlibGUlMjBzdHVkeSUyMGNvbW11bml0eXxlbnwxfHx8fDE3NzU3NTEzMzB8MA&ixlib=rb-4.1.0&q=80&w=1080")
    )

    val worshipDescription = "A collection of worship songs and albums."

    val worshipAlbums = listOf(
        WorshipAlbum(
            id = 1,
            title = "El Camino",
            imageUrl = "https://images.unsplash.com/photo-1630467267476-c67b34ffc837?w=1080",
            label = "LifeChurch Worship"
        ),
        WorshipAlbum(id = 2, title = "Digno De Alabar", imageUrl = null, fallbackColor = 0xFFF97316),
        WorshipAlbum(id = 3, title = "Tanto", imageUrl = null, fallbackColor = 0xFF3730A3),
        WorshipAlbum(
            id = 4,
            title = "Upside Down",
            imageUrl = null,
            fallbackColor = 0xFFFEF3C7,
            textColor = 0xFF111827
        ),
        WorshipAlbum(id = 5, title = "The Way", imageUrl = null, fallbackColor = 0xFF000000),
        WorshipAlbum(
            id = 6,
            title = "Creed",
            imageUrl = null,
            fallbackColor = 0xFFD1D5DB,
            textColor = 0xFFB91C1C
        ),
        WorshipAlbum(id = 7, title = "Love Like This", imageUrl = null, fallbackColor = 0xFFB91C1C),
        WorshipAlbum(id = 8, title = "Still Small Voice", imageUrl = null, fallbackColor = 0xFFFB923C)
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

    val lifeGroupsViewAll = listOf(
        MediaCard(1, "Sisters 2025: Draw Near", "https://images.unsplash.com/photo-1651514645899-de1cadd1ec31?w=1080"),
        MediaCard(2, "Draw Near for LifeGroups", "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=1080"),
        MediaCard(3, "Basics of Faith: Growing Through Doubt", "https://images.unsplash.com/photo-1529070538774-1843cb3265df?w=1080"),
        MediaCard(4, "Growing Through Doubt", "https://images.unsplash.com/photo-1511632765486-a01980e01a18?w=1080"),
        MediaCard(5, "More Habits for Following Jesus", "https://images.unsplash.com/photo-1541845157-a6d2d100c931?w=1080"),
        MediaCard(6, "Sisters 2024: Praying Like Jesus", "https://images.unsplash.com/photo-1521791055366-0d553872125f?w=1080"),
        MediaCard(7, "Praying Like Jesus", "https://images.unsplash.com/photo-1438232992991-995b7058bbb3?w=1080"),
        MediaCard(8, "Love Your Neighbor", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=1080"),
        MediaCard(9, "Basics of Faith: Habits for Following Jesus", "https://images.unsplash.com/photo-1519074069444-1ba4fff66d16?w=1080"),
        MediaCard(10, "Think Ahead", "https://images.unsplash.com/photo-1528605248644-14dd04022da1?w=1080"),
        MediaCard(11, "Sisters 2023", "https://images.unsplash.com/photo-1499209974431-9dddcece7f88?w=1080"),
        MediaCard(12, "Marriage Studies", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?w=1080")
    )

    val storiesDescription =
        "We're all on a journey together-a journey full of stories. And you're a part of the " +
            "Life.Church story! Here, you'll see some amazing ways God is working through your church."

    val stories = listOf(
        MediaCard(
            1,
            "Looking Different Is Okay | Sarah's Story",
            "https://images.unsplash.com/photo-1747214300321-4b4993dc4ac4?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJzb24lMjB0ZXN0aW1vbnklMjBwb3J0cmFpdCUyMHN0b3J5fGVufDF8fHx8MTc3NTc1MTMzMHww&ixlib=rb-4.1.0&q=80&w=1080"
        ),
        MediaCard(
            2,
            "Transforming Our Cities",
            "https://images.unsplash.com/photo-1651514645899-de1cadd1ec31?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzbWFsbCUyMGdyb3VwJTIwYmlibGUlMjBzdHVkeSUyMGNvbW11bml0eXxlbnwxfHx8fDE3NzU3NTEzMzB8MA&ixlib=rb-4.1.0&q=80&w=1080"
        ),
        MediaCard(
            3,
            "I Had to Get Back to Church | Donna's Story",
            "https://images.unsplash.com/photo-1644514539002-2a6baa243564?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3b3JzaGlwJTIwcHJheWVyJTIwY29udGVtcGxhdGlvbnxlbnwxfHx8fDE3NzU3NTAyNDl8MA&ixlib=rb-4.1.0&q=80&w=1080"
        ),
        MediaCard(
            4,
            "God Is at Work | Year in Review 2025",
            "https://images.unsplash.com/photo-1502598919583-c7374009d759?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwYXN0b3IlMjBwcmVhY2hpbmclMjBzZXJtb24lMjBtb3VudGFpbnN8ZW58MXx8fHwxNzc1NzUwMjQ4fDA&ixlib=rb-4.1.0&q=80&w=1080"
        )
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
