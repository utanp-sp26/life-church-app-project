package edu.utap.life_church_app.ui.pages.home

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import java.io.IOException
import java.util.UUID
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class PrayerRequestRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
) {
    suspend fun submitPrayerRequest(
        name: String,
        churchLocation: String,
        prayerRequest: String,
    ): PrayerSubmitResult {
        val requestId = UUID.randomUUID().toString().take(8)
        Log.d(TAG, "submitPrayerRequest start requestId=$requestId location=$churchLocation")
        val requestPayload = PrayerRequest(
            name = name,
            churchLocation = churchLocation,
            prayerRequest = prayerRequest,
            createdAt = FieldValue.serverTimestamp(),
        )

        return try {
            withTimeout(SUBMIT_TIMEOUT_MS) {
                firestore.collection(PRAYER_REQUESTS_COLLECTION)
                    .add(requestPayload)
                    .await()
            }
            Log.d(TAG, "submitPrayerRequest success requestId=$requestId")
            PrayerSubmitResult.Success
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "submitPrayerRequest timeout requestId=$requestId", e)
            PrayerSubmitResult.Failure("Submission timed out. Please check your connection and try again.")
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "submitPrayerRequest firestore failure requestId=$requestId", e)
            val message = when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                    "You do not have permission to submit this prayer request."
                FirebaseFirestoreException.Code.UNAVAILABLE ->
                    "Service is temporarily unavailable. Please try again."
                else ->
                    "We couldn't submit your request right now. Please try again."
            }
            PrayerSubmitResult.Failure(message)
        } catch (e: IOException) {
            Log.e(TAG, "submitPrayerRequest network failure requestId=$requestId", e)
            PrayerSubmitResult.Failure("Network error while submitting. Please try again.")
        } catch (e: Exception) {
            Log.e(TAG, "submitPrayerRequest unexpected failure requestId=$requestId", e)
            PrayerSubmitResult.Failure("Unexpected error. Please try again.")
        }
    }

    companion object {
        private const val PRAYER_REQUESTS_COLLECTION = "prayer_requests"
        private const val SUBMIT_TIMEOUT_MS = 15_000L
        private const val TAG = "PrayerRequestRepository"
    }
}

sealed interface PrayerSubmitResult {
    data object Success : PrayerSubmitResult
    data class Failure(val message: String) : PrayerSubmitResult
}
