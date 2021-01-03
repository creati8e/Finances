package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
data class TransactionReportRawData(
    val filter: TransactionReportFilter,
    val moneyAccounts: Map<Id, MoneyAccount>,
    val dataPeriodAmounts: Map<DataPeriod, List<Transaction>>,
    val listData: Map<Transaction, TransactionCategoryWithParent?>,
    val categoryTransactions: Map<TransactionCategory?, List<Transaction>>
)