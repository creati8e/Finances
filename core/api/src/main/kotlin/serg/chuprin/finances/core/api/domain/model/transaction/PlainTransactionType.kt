package serg.chuprin.finances.core.api.domain.model.transaction

import serg.chuprin.finances.core.api.domain.model.category.CategoryType

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
enum class PlainTransactionType {
    EXPENSE, INCOME;

    fun toCategoryType(): CategoryType {
        return when (this) {
            INCOME -> CategoryType.INCOME
            EXPENSE -> CategoryType.EXPENSE
        }
    }

}