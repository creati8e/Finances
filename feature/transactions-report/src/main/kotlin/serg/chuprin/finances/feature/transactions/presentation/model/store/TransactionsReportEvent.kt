package serg.chuprin.finances.feature.transactions.presentation.model.store

import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
sealed class TransactionsReportEvent {

    class NavigateToTransactionScreen(
        val screenArguments: TransactionScreenArguments
    ) : TransactionsReportEvent()

}