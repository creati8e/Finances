package serg.chuprin.finances.core.api.presentation.model

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
data class AmountInputState(
    val hasError: Boolean = false,
    val formattedAmount: String = "0"
)