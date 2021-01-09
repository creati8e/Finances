package serg.chuprin.finances.feature.dashboard.domain.builder.categories

import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.model.CategoryShares
import serg.chuprin.finances.core.api.domain.model.CategoryToTransactionsList
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardCategoriesWidgetPage
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
class DashboardCategoriesPageBuilder @Inject constructor(
    private val transactionAmountCalculator: TransactionAmountCalculator
) {

    suspend fun build(
        transactionType: PlainTransactionType,
        categoryToTransactionsList: CategoryToTransactionsList,
        topCategoriesCount: Int
    ): DashboardCategoriesWidgetPage {

        val categoryShares = categoryToTransactionsList.calculateCategoryShares()

        val (topCategoryShares, otherCategoryShares) = categoryShares
            .splitToPopularAndOther(topCategoriesCount)

        val totalAmount = categoryShares.fold(
            BigDecimal.ZERO,
            { totalShare, (_, categoryShare) -> totalShare + categoryShare }
        )

        return DashboardCategoriesWidgetPage(
            totalAmount = totalAmount,
            transactionType = transactionType,
            categoryShares = topCategoryShares,
            otherCategoryShares = otherCategoryShares,
            otherCategoriesShare = otherCategoryShares?.calculateTotal() ?: BigDecimal.ZERO
        )
    }

    private fun CategoryShares.splitToPopularAndOther(
        topCategoriesCount: Int
    ): Pair<CategoryShares, CategoryShares?> {
        return if (size > topCategoriesCount) {
            val popularCategoryShares = CategoryShares(take(topCategoriesCount))
            val otherCategoryShares = CategoryShares(takeLast(size - topCategoriesCount))
            popularCategoryShares to otherCategoryShares
        } else {
            this to null
        }
    }

    private suspend fun CategoryToTransactionsList.calculateCategoryShares(): CategoryShares {
        return CategoryShares(
            map { (categoryWithParent, transactions) ->
                Pair(
                    categoryWithParent,
                    transactionAmountCalculator.calculate(
                        transactions,
                        isAbsoluteAmount = true
                    )
                )
            }.sortedByDescending { (_, amount) -> amount }
        )
    }

    private fun CategoryShares.calculateTotal(): BigDecimal {
        return fold(BigDecimal.ZERO, { acc, (_, amount) -> acc + amount })
    }

}