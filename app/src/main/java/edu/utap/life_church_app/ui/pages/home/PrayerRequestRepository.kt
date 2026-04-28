package edu.utap.life_church_app.ui.pages.home

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

class PrayerRequestRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
) {
    suspend fun submitPrayerRequest(
        name: String,
        churchLocation: String,
        prayerRequest: String,
    ) {
        val requestPayload = PrayerRequest(
            name = name,
            churchLocation = churchLocation,
            prayerRequest = prayerRequest,
            createdAt = FieldValue.serverTimestamp(),
        )

        suspendCancellableCoroutine<Unit> { continuation ->
            firestore.collection(PRAYER_REQUESTS_COLLECTION)
                .add(requestPayload)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { error ->
                    continuation.resumeWithException(error)
                }
        }
    }

    companion object {
        private const val PRAYER_REQUESTS_COLLECTION = "prayer_requests"
    }
}
