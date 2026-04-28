package edu.utap.life_church_app.ui.pages.home

data class PrayerRequest(
    val name: String,
    val churchLocation: String,
    val prayerRequest: String,
    val createdAt: Any? = null,
)
