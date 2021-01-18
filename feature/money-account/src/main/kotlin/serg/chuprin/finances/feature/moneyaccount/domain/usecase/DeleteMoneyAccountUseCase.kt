package serg.chuprin.finances.feature.moneyaccount.domain.usecase

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.01.2021.
 */
class DeleteMoneyAccountUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val moneyAccountRepository: MoneyAccountRepository
) {

    suspend fun execute(moneyAccountId: Id) {
        val transactions = transactionRepository.transactions(
            TransactionsQuery(moneyAccountIds = setOf(moneyAccountId))
        )
        transactionRepository.deleteTransactions(transactions.map(Transaction::id))
        moneyAccountRepository.deleteAccounts(setOf(moneyAccountId))
    }

}