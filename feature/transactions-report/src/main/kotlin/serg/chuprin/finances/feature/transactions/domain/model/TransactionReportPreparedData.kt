package serg.chuprin.finances.feature.transactions.domain.model

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.TransactionsGroupedByDay
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 13.12.2020.
 */
data class TransactionReportPreparedData(
    val currency: Currency,
    val dataPeriodAmount: BigDecimal,
    val moneyAccounts: Map<Id, MoneyAccount>,
    val dataPeriodTransactions: TransactionsGroupedByDay,
    val dataPeriodAmounts: Map<DataPeriod, BigDecimal>,
    val categorySharesChart: TransactionReportCategorySharesChart?
)