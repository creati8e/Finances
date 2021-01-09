package serg.chuprin.finances.feature.transaction.presentation.model

import serg.chuprin.finances.core.api.domain.model.category.Category

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
data class TransactionChosenCategory(
    val formattedName: String,
    val category: Category?
)