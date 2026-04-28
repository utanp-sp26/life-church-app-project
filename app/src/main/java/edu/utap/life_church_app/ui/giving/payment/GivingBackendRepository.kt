package edu.utap.life_church_app.ui.giving.payment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

interface GivingBackendRepository {
    suspend fun scheduleGift(request: ScheduleGiftRequest): ScheduleGiftResult
}

class GivingBackendRepositoryImpl(
    private val baseUrl: String
) : GivingBackendRepository {
    override suspend fun scheduleGift(request: ScheduleGiftRequest): ScheduleGiftResult = withContext(Dispatchers.IO) {
        if (baseUrl.isBlank()) {
            return@withContext ScheduleGiftResult(
                success = false,
                message = "Backend URL is missing. Configure your payment backend."
            )
        }

        val endpoint = URL("$baseUrl/api/gifts/schedule")
        val connection = (endpoint.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15000
            readTimeout = 15000
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
        }

        return@withContext runCatching {
            val payload = JSONObject().apply {
                put("amount", request.amount)
                put("location", request.location)
                put("frequency", request.frequency ?: JSONObject.NULL)
                put("processDateLabel", request.processDateLabel)
                put("paymentToken", request.paymentToken)
            }
            connection.outputStream.bufferedWriter().use { it.write(payload.toString()) }

            val code = connection.responseCode
            val stream = if (code in 200..299) connection.inputStream else connection.errorStream
            val response = stream?.bufferedReader()?.use { it.readText() }.orEmpty()
            if (code in 200..299) {
                ScheduleGiftResult(success = true, message = response.ifBlank { "Gift scheduled." })
            } else {
                ScheduleGiftResult(success = false, message = response.ifBlank { "Unable to schedule gift." })
            }
        }.getOrElse {
            ScheduleGiftResult(success = false, message = "Network error: ${it.message ?: "unknown"}")
        }.also {
            connection.disconnect()
        }
    }
}
