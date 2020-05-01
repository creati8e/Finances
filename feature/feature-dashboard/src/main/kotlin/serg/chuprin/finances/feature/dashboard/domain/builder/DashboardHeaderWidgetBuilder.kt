package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardHeaderWidgetBuilder @Inject constructor(
    private val transactionRepository: TransactionRepository
) : DashboardWidgetBuilder<DashboardWidget.Header> {

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.Header> {
        return combine(
            flowOf(currentUser),
            flowOf(currentPeriod),
            allUserTransactionsFlow(currentUser)
        ) { user, period, allTransactions ->
            val (incomeAmount, expenseAmount, balanceAmount) = calculateAmounts(
                period = period,
                transactions = allTransactions
            )
            DashboardWidget.Header(
                dataPeriod = period,
                balance = balanceAmount,
                currency = user.defaultCurrency,
                currentPeriodIncomes = incomeAmount,
                currentPeriodExpenses = expenseAmount
            )
        }
    }

    private fun calculateAmounts(
        transactions: List<Transaction>,
        period: DataPeriod
    ): Triple<BigDecimal, BigDecimal, BigDecimal> {
        // Looks not very good but it's more better to iterate the collection only once
        // and calculate both amounts simultaneously without creating intermediate collections.
        var incomeAmount = BigDecimal.ZERO
        var expenseAmount = BigDecimal.ZERO
        var balanceAmount = BigDecimal.ZERO

        transactions.forEach { transaction ->
            if (transaction.dateTime <= period.endDate) {
                balanceAmount += transaction.amount
            }
            if (transaction.dateTime in period) {
                if (transaction.isExpense) {
                    expenseAmount += transaction.amount
                } else if (transaction.isIncome) {
                    incomeAmount += transaction.amount
                }
            }
        }
        return Triple(incomeAmount.abs(), expenseAmount.abs(), balanceAmount.abs())
    }

    private fun allUserTransactionsFlow(currentUser: User): Flow<List<Transaction>> {
        return transactionRepository.userTransactionsFlow(currentUser.id)
    }

}