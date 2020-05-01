package serg.chuprin.finances.feature.dashboard.domain.builder.categories

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.extensions.amount
import serg.chuprin.finances.feature.dashboard.domain.builder.CategoryAmounts
import serg.chuprin.finances.feature.dashboard.domain.builder.categories.DashboardCategoriesPageBuilder.Companion.TOP_CATEGORIES_COUNT
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardCategoriesWidgetPage
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.05.2020.
 *
 * This class builds [DashboardCategoriesWidgetPage] for [PlainTransactionType].
 * It calculates amount for each category,
 * sorts them by amount and takes only most [TOP_CATEGORIES_COUNT].
 * For other categories amount is calculated too.
 */
class DashboardCategoriesPageBuilder @Inject constructor() {

    private companion object {
        private const val TOP_CATEGORIES_COUNT = 6
    }

    fun build(
        transactionType: PlainTransactionType,
        categoryTransactionsMap: Map<TransactionCategory?, List<Transaction>>
    ): DashboardCategoriesWidgetPage {

        val (topCategories, otherCategories) = categoryTransactionsMap
            .calculateCategoryAmounts()
            .splitToPopularAndOther()

        return DashboardCategoriesWidgetPage(
            transactionType = transactionType,
            categoryAmounts = topCategories,
            totalAmount = topCategories.calculateAmount(),
            otherAmount = otherCategories?.calculateAmount() ?: BigDecimal.ZERO
        )
    }

    private fun CategoryAmounts.splitToPopularAndOther(): Pair<CategoryAmounts, CategoryAmounts?> {
        return if (size > TOP_CATEGORIES_COUNT) {
            take(TOP_CATEGORIES_COUNT) to takeLast(size - TOP_CATEGORIES_COUNT)
        } else {
            this to null
        }
    }

    private fun Map<TransactionCategory?, List<Transaction>>.calculateCategoryAmounts(): CategoryAmounts {
        return this
            .map { (categoryWithParent, transactions) ->
                categoryWithParent to transactions.amount.abs()
            }
            .sortedByDescending { (_, amount) -> amount }
    }

    private fun CategoryAmounts.calculateAmount(): BigDecimal {
        return fold(BigDecimal.ZERO, { acc, (_, amount) -> acc + amount })
    }

}