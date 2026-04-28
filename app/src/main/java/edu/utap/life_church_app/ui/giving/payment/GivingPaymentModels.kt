package edu.utap.life_church_app.ui.giving.payment

data class ScheduleGiftRequest(
    val amount: String,
    val location: String,
    val frequency: String?,
    val processDateLabel: String,
    val paymentToken: String
)

data class ScheduleGiftResult(
    val success: Boolean,
    val message: String
)
