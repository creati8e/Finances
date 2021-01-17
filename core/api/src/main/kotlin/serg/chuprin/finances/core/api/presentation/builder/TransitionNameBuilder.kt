package serg.chuprin.finances.core.api.presentation.builder

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType

/**
 * Created by Sergey Chuprin on 08.05.2020.
 */
interface TransitionNameBuilder {

    fun buildForTransactionsReport(categoryId: Id?): String

    fun buildForTransactionsReportOtherCategory(transactionType: PlainTransactionType): String

    fun buildForTransactionsReportUnknownCategory(transactionType: PlainTransactionType): String

    fun buildForForMoneyAccountDetails(moneyAccountId: Id): String

    fun buildForTransaction(transactionId: Id? = null): String

    fun buildForMoneyAccountCreation(): String

}