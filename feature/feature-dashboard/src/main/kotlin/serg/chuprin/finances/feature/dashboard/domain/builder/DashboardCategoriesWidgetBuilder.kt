package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.extensions.amount
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardCategoriesWidgetPage
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import java.math.BigDecimal
import javax.inject.Inject

typealias CategoryAmounts = List<Pair<TransactionCategory?, BigDecimal>>

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class DashboardCategoriesWidgetBuilder @Inject constructor(
    private val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
) : DashboardWidgetBuilder<DashboardWidget.Categories> {

    private companion object {
        private const val TOP_CATEGORIES_COUNT = 6
    }

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.Categories>? {
        return combine(
            pageFlow(currentUser, currentPeriod, PlainTransactionType.EXPENSE),
            pageFlow(currentUser, currentPeriod, PlainTransactionType.INCOME)
        ) { page1, page2 ->
            DashboardWidget.Categories(currentUser.defaultCurrency, listOf(page1, page2))
        }
    }

    private fun pageFlow(
        currentUser: User,
        currentPeriod: DataPeriod,
        transactionType: PlainTransactionType
    ): Flow<DashboardCategoriesWidgetPage> {
        return transactionCategoryRetrieverService
            .userCategoryTransactionsInPeriod(
                userId = currentUser.id,
                dataPeriod = currentPeriod,
                transactionType = transactionType
            )
            .map { categoryTransactionsMap -> buildPage(transactionType, categoryTransactionsMap) }
    }

    private fun buildPage(
        transactionType: PlainTransactionType,
        categoryTransactionsMap: Map<TransactionCategoryWithParent?, List<Transaction>>
    ): DashboardCategoriesWidgetPage {

        val (topCategories, otherCategories) = categoryTransactionsMap
            .calculateCategoryAmounts()
            .splitToPopularAndOther()

        return DashboardCategoriesWidgetPage(
            transactionType = transactionType,
            categoryAmounts = topCategories,
            totalAmount = topCategories.calculateAmount(),
            otherAmount = otherCategories.calculateAmount()
        )
    }

    private fun CategoryAmounts.splitToPopularAndOther(): Pair<CategoryAmounts, CategoryAmounts> {
        return if (size > TOP_CATEGORIES_COUNT) {
            take(TOP_CATEGORIES_COUNT) to takeLast(size - TOP_CATEGORIES_COUNT)
        } else {
            this to this
        }
    }

    private fun Map<TransactionCategoryWithParent?, List<Transaction>>.calculateCategoryAmounts(): CategoryAmounts {
        return map { (categoryWithParent, transactions) ->
            categoryWithParent?.category to transactions.amount.abs()
        }.sortedByDescending { (_, amount) -> amount }
    }

    private fun CategoryAmounts.calculateAmount(): BigDecimal {
        return fold(BigDecimal.ZERO, { acc, (_, amount) -> acc + amount })
    }

}