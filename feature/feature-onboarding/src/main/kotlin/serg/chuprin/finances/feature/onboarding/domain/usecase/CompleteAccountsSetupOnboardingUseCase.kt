package serg.chuprin.finances.feature.onboarding.domain.usecase

import serg.chuprin.finances.core.api.domain.model.*
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.feature.onboarding.domain.OnboardingMoneyAccountCreationParams
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
class CompleteAccountsSetupOnboardingUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val onboardingRepository: OnboardingRepository,
    private val transactionRepository: TransactionRepository,
    private val moneyAccountRepository: MoneyAccountRepository
) {

    suspend fun execute(
        cashAccountParams: OnboardingMoneyAccountCreationParams?,
        bankAccountCardParams: OnboardingMoneyAccountCreationParams?
    ) {
        if (cashAccountParams != null) {
            createMoneyAccount(cashAccountParams)
        }
        if (bankAccountCardParams != null) {
            createMoneyAccount(bankAccountCardParams)
        }
        onboardingRepository.onboardingStep = OnboardingStep.COMPLETED
    }

    // TODO: Add some kind of transactions.
    private suspend fun createMoneyAccount(accountParams: OnboardingMoneyAccountCreationParams) {
        val currentUser = userRepository.getCurrentUser()
        val moneyAccount = MoneyAccount(
            id = Id.createNew(),
            ownerId = currentUser.id,
            name = accountParams.accountName,
            currencyCode = currentUser.defaultCurrencyCode
        )
        moneyAccountRepository.createAccount(moneyAccount)
        setInitialAccountBalance(
            user = currentUser,
            account = moneyAccount,
            balance = accountParams.balance
        )
    }

    private suspend fun setInitialAccountBalance(
        user: User,
        account: MoneyAccount,
        balance: MoneyAccountBalance
    ) {
        val balanceTransaction = Transaction(
            _date = Date(),
            ownerId = user.id,
            id = Id.createNew(),
            moneyAccountId = account.id,
            type = TransactionType.BALANCE,
            currencyCode = account.currencyCode,
            _amount = balance.bigDecimal.toString()
        )
        transactionRepository.createTransaction(balanceTransaction)
    }

}