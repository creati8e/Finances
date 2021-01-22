package serg.chuprin.finances.feature.moneyaccount.domain.usecase

import com.github.ajalt.timberkt.Timber
import kotlinx.coroutines.flow.firstOrNull
import serg.chuprin.finances.core.api.domain.MoneyAccountBalanceCalculator
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.feature.moneyaccount.domain.model.MoneyAccountEditingParams
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 18.01.2021.
 */
class EditMoneyAccountUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val moneyAccountRepository: MoneyAccountRepository,
    private val moneyAccountBalanceCalculator: MoneyAccountBalanceCalculator
) {

    suspend fun execute(params: MoneyAccountEditingParams) {
        val (moneyAccountId, newName, newBalance) = params

        require(newBalance >= BigDecimal.ZERO) {
            "Negative balances is not supported yet"
        }
        val moneyAccount = moneyAccountRepository
            .accountFlow(moneyAccountId)
            .firstOrNull()

        if (moneyAccount == null) {
            Timber.d { "Money account was not deleted" }
            return
        }

        if (newName != moneyAccount.name) {
            moneyAccountRepository.createOrUpdateAccount(moneyAccount.copy(name = newName))
        }
        correctBalance(moneyAccount, newBalance)
    }

    private suspend fun correctBalance(moneyAccount: MoneyAccount, newBalance: BigDecimal) {
        val moneyAccountId = moneyAccount.id
        val balanceCorrectionAmount =
            calculateAmountForBalanceCorrection(moneyAccountId, newBalance)
        if (balanceCorrectionAmount != null) {
            val balanceCorrectionTransaction = Transaction(
                id = Id.createNew(),
                categoryId = null,
                ownerId = moneyAccount.ownerId,
                moneyAccountId = moneyAccountId,
                type = TransactionType.BALANCE,
                _dateTime = LocalDateTime.now(),
                currencyCode = moneyAccount.currencyCode,
                _amount = balanceCorrectionAmount.toString()
            )
            transactionRepository.createOrUpdate(setOf(balanceCorrectionTransaction))
        }
    }

    private suspend fun calculateAmountForBalanceCorrection(
        moneyAccountId: Id,
        newBalance: BigDecimal
    ): BigDecimal? {
        val currentBalance = moneyAccountBalanceCalculator.calculate(moneyAccountId)
        if (currentBalance == newBalance) {
            return null
        }
        return if (currentBalance < newBalance) {
            newBalance - currentBalance
        } else {
            currentBalance - newBalance
        }
    }

}