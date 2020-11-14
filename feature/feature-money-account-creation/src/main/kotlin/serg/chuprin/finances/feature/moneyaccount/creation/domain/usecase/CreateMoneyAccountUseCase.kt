package serg.chuprin.finances.feature.moneyaccount.creation.domain.usecase

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 14.11.2020.
 */
class CreateMoneyAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val moneyAccountRepository: MoneyAccountRepository
) {

    suspend fun execute(
        currency: Currency,
        accountName: String,
        initialBalance: BigDecimal
    ) {
        val moneyAccountId = Id.createNew()
        val currencyCode = currency.currencyCode
        val ownerId = userRepository.getCurrentUser().id

        val moneyAccount = MoneyAccount(
            ownerId = ownerId,
            name = accountName,
            isFavorite = false,
            id = moneyAccountId,
            currencyCode = currencyCode
        )
        moneyAccountRepository.createAccount(moneyAccount)

        if (initialBalance != BigDecimal.ZERO) {
            val balanceTransaction = Transaction(
                id = Id.createNew(),
                ownerId = ownerId,
                type = TransactionType.BALANCE,
                moneyAccountId = moneyAccountId,
                _amount = initialBalance.toString(),
                currencyCode = currencyCode
            )
            transactionRepository.createTransaction(balanceTransaction)
        }
    }

}