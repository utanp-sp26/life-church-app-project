package edu.utap.life_church_app.ui.giving

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.utap.life_church_app.BuildConfig
import edu.utap.life_church_app.ui.giving.payment.GivingBackendRepository
import edu.utap.life_church_app.ui.giving.payment.GivingBackendRepositoryImpl
import edu.utap.life_church_app.ui.giving.payment.ScheduleGiftRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GivingSubmitUiState(
    val isSubmitting: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class GivingViewModel(
    private val repository: GivingBackendRepository = GivingBackendRepositoryImpl(
        baseUrl = BuildConfig.GIVING_BACKEND_URL
    )
) : ViewModel() {
    private val _submitUiState = MutableStateFlow(GivingSubmitUiState())
    val submitUiState: StateFlow<GivingSubmitUiState> = _submitUiState.asStateFlow()

    fun clearMessages() {
        _submitUiState.value = _submitUiState.value.copy(successMessage = null, errorMessage = null)
    }

    fun reportError(message: String) {
        _submitUiState.value = GivingSubmitUiState(isSubmitting = false, errorMessage = message)
    }

    fun scheduleGift(
        amount: String,
        location: String,
        frequency: String?,
        processDateLabel: String,
        paymentToken: String
    ) {
        viewModelScope.launch {
            _submitUiState.value = GivingSubmitUiState(isSubmitting = true)
            val result = repository.scheduleGift(
                ScheduleGiftRequest(
                    amount = amount,
                    location = location,
                    frequency = frequency,
                    processDateLabel = processDateLabel,
                    paymentToken = paymentToken
                )
            )
            _submitUiState.value = if (result.success) {
                GivingSubmitUiState(isSubmitting = false, successMessage = result.message)
            } else {
                GivingSubmitUiState(isSubmitting = false, errorMessage = result.message)
            }
        }
    }
}
