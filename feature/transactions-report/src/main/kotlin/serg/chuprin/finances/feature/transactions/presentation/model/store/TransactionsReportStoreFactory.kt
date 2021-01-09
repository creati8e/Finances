package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
@ScreenScope
class TransactionsReportStoreFactory @Inject constructor(
    actionExecutor: TransactionsReportActionExecutor,
    bootstrapper: TransactionsReportStoreBootstrapper
) : AbsStoreFactory<TransactionsReportIntent, TransactionsReportEffect, TransactionsReportAction, TransactionsReportState, TransactionsReportEvent, TransactionsReportStore>(
    TransactionsReportState(),
    TransactionsReportStateReducer(),
    bootstrapper,
    actionExecutor,
    TransactionsReportAction::ExecuteIntent
)