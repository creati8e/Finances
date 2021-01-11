package serg.chuprin.finances.feature.transaction.presentation.model.store.factory

import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.test.presentation.mvi.store.TestStore
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionEvent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionIntent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionState

/**
 * Created by Sergey Chuprin on 11.01.2021.
 */
data class TransactionStoreTestData(
    val testStore: TestStore<TransactionIntent, TransactionState, TransactionEvent>,
    val moneyAccounts: () -> Collection<MoneyAccount>
)