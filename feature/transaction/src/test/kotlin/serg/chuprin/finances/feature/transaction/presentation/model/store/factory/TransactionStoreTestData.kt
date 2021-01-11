package serg.chuprin.finances.feature.transaction.presentation.model.store.factory

import serg.chuprin.finances.core.api.domain.model.category.CategoryIdToCategory
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount

/**
 * Created by Sergey Chuprin on 11.01.2021.
 */
data class TransactionStoreTestData(
    val testStore: TransactionTestStore,
    val moneyAccounts: () -> Collection<MoneyAccount>,
    val expenseCategories: () -> CategoryIdToCategory
)