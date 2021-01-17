package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.MoneyAccountDefaultData
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.01.2021.
 */
@ScreenScope
class MoneyAccountCreationStoreBootstrapper @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val screenArguments: MoneyAccountScreenArguments,
    private val moneyAccountRepository: MoneyAccountRepository,
    private val transactionAmountCalculator: TransactionAmountCalculator
) : StoreBootstrapper<MoneyAccountCreationAction> {

    override fun invoke(): Flow<MoneyAccountCreationAction> {
        return when (screenArguments) {
            is MoneyAccountScreenArguments.Editing -> {
                flowOfSingleValue {
                    buildActionForExistingMoneyAccount(screenArguments.moneyAccountId)
                }
            }
            is MoneyAccountScreenArguments.Creation -> emptyFlow()
        }
    }

    private suspend fun buildActionForExistingMoneyAccount(
        accountId: Id
    ): MoneyAccountCreationAction.SetInitialStateForExistingAccount {
        // TODO: Create abstraction for calculation account balance.
        val account = moneyAccountRepository.accountFlow(accountId).first()!!
        val balance = calculateAccountBalance(accountId)
        return MoneyAccountCreationAction.SetInitialStateForExistingAccount(
            account.name,
            balance,
            moneyAccountDefaultData = MoneyAccountDefaultData(balance, account.name)
        )
    }

    private suspend fun calculateAccountBalance(accountId: Id): BigDecimal {
        val transactions = transactionRepository.transactions(
            TransactionsQuery(moneyAccountIds = setOf(accountId))
        )
        return transactionAmountCalculator.calculate(
            transactions,
            isAbsoluteAmount = false
        )
    }

}