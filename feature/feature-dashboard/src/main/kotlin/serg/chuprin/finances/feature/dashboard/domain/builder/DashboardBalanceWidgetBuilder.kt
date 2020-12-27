package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardBalanceWidgetBuilder @Inject constructor(
    private val transactionRepository: TransactionRepository
) : DashboardWidgetBuilder<DashboardWidget.Balance> {

    override fun isForType(widgetType: DashboardWidgetType): Boolean {
        return widgetType == DashboardWidgetType.BALANCE
    }

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.Balance> {
        return combine(
            flowOf(currentUser),
            flowOf(currentPeriod),
            allUserTransactionsFlow(currentUser)
        ) { user, dataPeriod, allTransactions ->
            val (incomeAmount, expenseAmount, balanceAmount) = calculateAmounts(
                dataPeriod = dataPeriod,
                transactions = allTransactions
            )
            DashboardWidget.Balance(
                balance = balanceAmount,
                currency = user.defaultCurrency,
                currentPeriodIncomes = incomeAmount,
                currentPeriodExpenses = expenseAmount
            )
        }
    }

    private fun calculateAmounts(
        transactions: List<Transaction>,
        dataPeriod: DataPeriod
    ): Triple<BigDecimal, BigDecimal, BigDecimal> {
        // Looks not very good but it's more better to iterate the collection only once
        // and calculate both amounts simultaneously without creating intermediate collections.
        var incomeAmount = BigDecimal.ZERO
        var expenseAmount = BigDecimal.ZERO
        var balanceAmount = BigDecimal.ZERO

        transactions.forEach { transaction ->
            if (transaction.dateTime <= dataPeriod.endDate) {
                balanceAmount += transaction.amount
            }
            if (transaction.dateTime in dataPeriod) {
                if (transaction.isExpense) {
                    expenseAmount += transaction.amount
                } else if (transaction.isIncome) {
                    incomeAmount += transaction.amount
                }
            }
        }
        return Triple(incomeAmount.abs(), expenseAmount.abs(), balanceAmount)
    }

    private fun allUserTransactionsFlow(currentUser: User): Flow<List<Transaction>> {
        return transactionRepository.transactionsFlow(TransactionsQuery(ownerId = currentUser.id))
    }

}