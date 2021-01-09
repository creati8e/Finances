package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
@ScreenScope
class TransactionStoreFactory @Inject constructor(
    actionExecutor: TransactionActionExecutor,
    bootstrapper: TransactionStoreBootstrapper
) : AbsStoreFactory<TransactionIntent, TransactionEffect, TransactionAction, TransactionState, TransactionEvent, TransactionStore>(
    TransactionState(),
    TransactionStateReducer(),
    bootstrapper,
    actionExecutor,
    TransactionAction::ExecuteIntent
)