package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.moneyaccount.creation.R
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.MoneyAccountDefaultData
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.01.2021.
 */
@ScreenScope
class MoneyAccountCreationStoreBootstrapper @Inject constructor(
    private val resourceManger: ResourceManger,
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
            is MoneyAccountScreenArguments.Creation -> {
                flowOf(
                    buildActionForAccountCreation()
                )
            }
        }
    }

    private fun buildActionForAccountCreation(): MoneyAccountCreationAction.SetInitialState {
        return MoneyAccountCreationAction.SetInitialState(
            accountName = EMPTY_STRING,
            balance = BigDecimal.ZERO,
            moneyAccountDefaultData = null,
            currencyPickerIsClickable = true,
            toolbarTitle = resourceManger.getString(R.string.money_account_creation_toolbar_title)
        )
    }

    private suspend fun buildActionForExistingMoneyAccount(
        accountId: Id
    ): MoneyAccountCreationAction.SetInitialState {
        // TODO: Create abstraction for calculation account balance.
        val account = moneyAccountRepository.accountFlow(accountId).first()!!
        val balance = calculateAccountBalance(accountId)
        return MoneyAccountCreationAction.SetInitialState(
            balance = balance,
            accountName = account.name,
            currencyPickerIsClickable = false,
            moneyAccountDefaultData = MoneyAccountDefaultData(balance, account.name),
            toolbarTitle = resourceManger.getString(R.string.money_account_editing_toolbar_title)
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