package com.blankspace.domain.entity

data class TransactionEntity(
    val transactionId: String,
    val type: String,
    val amountInCents: Long,
    val commaSeparatedTags: String,
    val timestamp: Long,
    val flagged: Boolean,
    val remarks: String
) {

    companion object {
        private const val DEBIT_HVT_LIMIT = (4000 * 100).toLong() // $4,000
        private const val CREDIT_HVT_LIMIT = (4000 * 100).toLong() // $4,000
    }

    val isHVT: Boolean
        get() = when (type) {
            "credit" -> amountInCents >= CREDIT_HVT_LIMIT
            "debit" -> amountInCents >= DEBIT_HVT_LIMIT
            else -> false
        }
}