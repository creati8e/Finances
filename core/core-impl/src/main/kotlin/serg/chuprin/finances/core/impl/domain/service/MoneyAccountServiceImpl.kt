package serg.chuprin.finances.core.impl.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.MoneyAccountBalances
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.service.MoneyAccountService
import serg.chuprin.finances.core.api.extensions.amount
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
internal class MoneyAccountServiceImpl @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val moneyAccountRepository: MoneyAccountRepository
) : MoneyAccountService {

    override fun moneyAccountBalancesFlow(user: User): Flow<MoneyAccountBalances> {
        return moneyAccountRepository
            .userAccountsFlow(user.id)
            .flatMapLatest(::calculateBalance)
    }

    private fun calculateBalance(moneyAccounts: List<MoneyAccount>): Flow<MoneyAccountBalances> {
        if (moneyAccounts.isEmpty()) {
            return flowOf(MoneyAccountBalances())
        }
        val flows = moneyAccounts.map { account ->
            combine(
                flowOf(account),
                transactionRepository.transactionsFlow(
                    TransactionsQuery(moneyAccountIds = setOf(account.id))
                )
            ) { acc, transactions -> acc to transactions.amount }
        }
        return combine(flows) { array ->
            array.fold(MoneyAccountBalances(), { accounts, (account, balance) ->
                accounts.add(account, balance)
            })
        }
    }

}