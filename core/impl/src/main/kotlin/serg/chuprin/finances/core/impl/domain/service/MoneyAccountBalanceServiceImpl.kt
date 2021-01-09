package serg.chuprin.finances.core.impl.domain.service

import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccountToBalance
import serg.chuprin.finances.core.api.domain.model.moneyaccount.query.MoneyAccountsQuery
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.service.MoneyAccountBalanceService
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
internal class MoneyAccountBalanceServiceImpl @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val moneyAccountRepository: MoneyAccountRepository,
    private val transactionAmountCalculator: TransactionAmountCalculator
) : MoneyAccountBalanceService {

    override fun balancesFlow(user: User): Flow<MoneyAccountToBalance> {
        return moneyAccountRepository
            .accountsFlow(MoneyAccountsQuery(ownerId = user.id))
            .flatMapLatest(::calculateBalances)
    }

    private fun calculateBalances(moneyAccounts: List<MoneyAccount>): Flow<MoneyAccountToBalance> {
        if (moneyAccounts.isEmpty()) {
            return flowOf(MoneyAccountToBalance())
        }
        val balanceFlows = moneyAccounts.map { account ->
            combine(
                flowOf(account),
                transactionRepository
                    .transactionsFlow(
                        TransactionsQuery(moneyAccountIds = setOf(account.id))
                    )
                    .map { transactions ->
                        transactionAmountCalculator.calculate(
                            transactions,
                            isAbsoluteAmount = false
                        )
                    },
                ::Pair
            )
        }
        return combine(balanceFlows) { array ->
            array.fold(MoneyAccountToBalance(), { accountBalances, (account, balance) ->
                accountBalances.add(account, balance)
            })
        }
    }

}