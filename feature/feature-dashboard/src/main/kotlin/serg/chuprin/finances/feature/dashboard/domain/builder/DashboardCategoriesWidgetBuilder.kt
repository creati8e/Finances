package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.extensions.amount
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardTransactionCategoriesTypeRepository
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
class DashboardCategoriesWidgetBuilder @Inject constructor(
    private val transactionCategoryRetrieverService: TransactionCategoryRetrieverService,
    private val transactionCategoriesTypeRepository: DashboardTransactionCategoriesTypeRepository
) : DashboardWidgetBuilder<DashboardWidget.Categories> {

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.Categories>? {
        return transactionCategoriesTypeRepository
            .transactionCategoriesType
            .flatMapLatest { transactionType ->
                transactionCategoryRetrieverService
                    .userCategoryTransactionsInPeriod(
                        userId = currentUser.id,
                        dataPeriod = currentPeriod,
                        transactionType = transactionType
                    )
                    .map { categoryTransactionsMap ->
                        DashboardWidget.Categories(
                            transactionType = transactionType,
                            currency = currentUser.defaultCurrency,
                            totalAmount = categoryTransactionsMap.values.flatten().amount.abs(),
                            categoryAmounts = calculateCategoryAmounts(categoryTransactionsMap)
                        )
                    }
            }
    }

    private fun calculateCategoryAmounts(
        categoryTransactionsMap: Map<TransactionCategoryWithParent?, List<Transaction>>
    ): Map<TransactionCategory?, BigDecimal> {
        return categoryTransactionsMap
            .map { (categoryWithParent, transactions) ->
                categoryWithParent?.category to transactions.amount.abs()
            }
            .toMap()
    }

}