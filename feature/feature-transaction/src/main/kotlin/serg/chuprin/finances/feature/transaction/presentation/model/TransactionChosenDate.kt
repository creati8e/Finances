package serg.chuprin.finances.feature.transaction.presentation.model

import java.time.LocalDate

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
data class TransactionChosenDate(
    val formatted: String,
    val date: LocalDate
)