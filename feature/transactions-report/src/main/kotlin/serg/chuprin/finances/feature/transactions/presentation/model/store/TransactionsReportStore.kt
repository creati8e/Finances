package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
@ScreenScope
class TransactionsReportStore @Inject constructor(
    executor: TransactionsReportActionExecutor,
    bootstrapper: TransactionsReportStoreBootstrapper
) : BaseStateStore<TransactionsReportIntent, TransactionsReportEffect, TransactionsReportAction, TransactionsReportState, TransactionsReportEvent>(
    TransactionsReportState(),
    TransactionsReportStateReducer(),
    bootstrapper,
    executor,
    TransactionsReportIntentToActionMapper()
)