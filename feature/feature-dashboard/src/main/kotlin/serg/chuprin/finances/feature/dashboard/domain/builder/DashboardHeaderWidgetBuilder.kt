package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.DataPeriod
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.extensions.amount
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
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
            val balanceAmount = allTransactions.amount
            val transactionsInPeriod = allTransactions.filter { transaction ->
                !transaction.isBalance && transaction.dateTime in period
            }
            val incomeAmount = transactionsInPeriod.filter(Transaction::isIncome).amount.abs()
            val expenseAmount = transactionsInPeriod.filter(Transaction::isExpense).amount.abs()
            DashboardWidget.Header(
                dataPeriod = period,
                balance = balanceAmount,
                currency = user.defaultCurrency,
                currentPeriodIncomes = incomeAmount,
                currentPeriodExpenses = expenseAmount
            )
        }
    }

    private fun allUserTransactionsFlow(currentUser: User): Flow<List<Transaction>> {
        return transactionRepository.userTransactionsFlow(currentUser.id)
    }

}