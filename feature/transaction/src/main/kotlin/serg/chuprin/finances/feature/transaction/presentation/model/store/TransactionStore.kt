package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
typealias TransactionStore = StateStore<TransactionIntent, TransactionState, TransactionEvent>