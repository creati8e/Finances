package serg.chuprin.finances.feature.dashboard.domain.builder.categories

import serg.chuprin.finances.core.api.domain.model.DashboardCategoriesWidgetPage
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.extensions.amount
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.05.2020.
 *
 * This class builds [DashboardCategoriesWidgetPage] for [PlainTransactionType].
 * It calculates amount for each category,
 * sorts them by amount and takes only most passed count.
 * For other categories amount is calculated too.
 */
class DashboardCategoriesPageBuilder @Inject constructor() {

    fun build(
        transactionType: PlainTransactionType,
        categoryTransactionsMap: Map<TransactionCategory?, List<Transaction>>,
        topCategoriesCount: Int
    ): DashboardCategoriesWidgetPage {

        val categoryAmounts = categoryTransactionsMap.calculateCategoryAmounts()

        val (topCategories, otherCategories) = categoryAmounts
            .splitToPopularAndOther(topCategoriesCount)

        val totalAmount = categoryAmounts.fold(
            BigDecimal.ZERO,
            { amount, (_, categoryAmount) -> amount + categoryAmount }
        )

        return DashboardCategoriesWidgetPage(
            totalAmount = totalAmount,
            transactionType = transactionType,
            categoryAmounts = topCategories,
            otherAmount = otherCategories?.calculateAmount() ?: BigDecimal.ZERO
        )
    }

    private fun CategoryAmounts.splitToPopularAndOther(
        topCategoriesCount: Int
    ): Pair<CategoryAmounts, CategoryAmounts?> {
        return if (size > topCategoriesCount) {
            take(topCategoriesCount) to takeLast(size - topCategoriesCount)
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