package edu.utap.life_church_app.ui.giving.payment

import edu.utap.life_church_app.BuildConfig
import org.json.JSONArray
import org.json.JSONObject

object GooglePayJsonFactory {
    private val allowedCardNetworks = JSONArray(listOf("VISA", "MASTERCARD", "AMEX", "DISCOVER"))
    private val allowedCardAuthMethods = JSONArray(listOf("PAN_ONLY", "CRYPTOGRAM_3DS"))

    private fun baseCardPaymentMethod(): JSONObject = JSONObject().apply {
        put("type", "CARD")
        put(
            "parameters",
            JSONObject().apply {
                put("allowedAuthMethods", allowedCardAuthMethods)
                put("allowedCardNetworks", allowedCardNetworks)
                put("billingAddressRequired", true)
                put(
                    "billingAddressParameters",
                    JSONObject().apply {
                        put("format", "FULL")
                    }
                )
            }
        )
    }

    fun isReadyToPayRequest(): JSONObject = JSONObject().apply {
        put("apiVersion", 2)
        put("apiVersionMinor", 0)
        put("allowedPaymentMethods", JSONArray().put(baseCardPaymentMethod()))
    }

    fun paymentDataRequest(price: String): JSONObject = JSONObject().apply {
        put("apiVersion", 2)
        put("apiVersionMinor", 0)
        put(
            "allowedPaymentMethods",
            JSONArray().put(
                baseCardPaymentMethod().apply {
                    put(
                        "tokenizationSpecification",
                        JSONObject().apply {
                            put("type", "PAYMENT_GATEWAY")
                            put(
                                "parameters",
                                JSONObject().apply {
                                    put("gateway", "stripe")
                                    put("gatewayMerchantId", BuildConfig.STRIPE_PUBLISHABLE_KEY)
                                }
                            )
                        }
                    )
                }
            )
        )
        put(
            "transactionInfo",
            JSONObject().apply {
                put("totalPriceStatus", "FINAL")
                put("totalPrice", price)
                put("currencyCode", "USD")
                put("countryCode", "US")
            }
        )
        put(
            "merchantInfo",
            JSONObject().apply {
                put("merchantName", "Life.Church")
            }
        )
    }
}
