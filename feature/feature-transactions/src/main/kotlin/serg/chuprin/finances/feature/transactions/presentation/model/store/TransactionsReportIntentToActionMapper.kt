package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.core.mvi.mapper.StoreIntentToActionMapper

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportIntentToActionMapper :
    StoreIntentToActionMapper<TransactionsReportIntent, TransactionsReportAction> {

    override fun invoke(intent: TransactionsReportIntent): TransactionsReportAction {
        return TransactionsReportAction.ExecuteIntent(intent)
    }

}