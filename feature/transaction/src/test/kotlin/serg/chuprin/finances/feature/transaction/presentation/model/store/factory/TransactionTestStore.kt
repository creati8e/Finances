package serg.chuprin.finances.feature.transaction.presentation.model.store.factory

import serg.chuprin.finances.core.test.presentation.mvi.store.TestStore
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionEvent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionIntent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionState

/**
 * Created by Sergey Chuprin on 11.01.2021.
 */
typealias TransactionTestStore = TestStore<TransactionIntent, TransactionState, TransactionEvent>