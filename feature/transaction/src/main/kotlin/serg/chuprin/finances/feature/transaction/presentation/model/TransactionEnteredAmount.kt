package serg.chuprin.finances.feature.transaction.presentation.model

import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
data class TransactionEnteredAmount(
    val formatted: String,
    val hasError: Boolean,
    val amount: BigDecimal?
)