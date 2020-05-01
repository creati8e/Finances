package serg.chuprin.finances.feature.onboarding.domain.usecase

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.repository.*
import serg.chuprin.finances.feature.onboarding.domain.OnboardingMoneyAccountCreationParams
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
class CompleteAccountsSetupOnboardingUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val onboardingRepository: OnboardingRepository,
    private val transactionRepository: TransactionRepository,
    private val moneyAccountRepository: MoneyAccountRepository,
    private val transactionCategoryRepository: TransactionCategoryRepository
) {

    suspend fun execute(
        cashAccountParams: OnboardingMoneyAccountCreationParams?,
        bankAccountCardParams: OnboardingMoneyAccountCreationParams?
    ) {
        val currentUser = userRepository.getCurrentUser()

        if (cashAccountParams != null) {
            createMoneyAccount(cashAccountParams, currentUser)
        }
        if (bankAccountCardParams != null) {
            createMoneyAccount(bankAccountCardParams, currentUser)
        }
        transactionCategoryRepository.createPredefinedCategories(currentUser.id)
        onboardingRepository.onboardingStep = OnboardingStep.COMPLETED
    }

    // TODO: Add some kind of transactions.
    private fun createMoneyAccount(
        accountParams: OnboardingMoneyAccountCreationParams,
        currentUser: User
    ) {
        val moneyAccount = MoneyAccount(
            isFavorite = false,
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

    private fun setInitialAccountBalance(
        user: User,
        account: MoneyAccount,
        balance: BigDecimal
    ) {
        val balanceTransaction =
            Transaction(
                ownerId = user.id,
                id = Id.createNew(),
                moneyAccountId = account.id,
                _amount = balance.toString(),
                type = TransactionType.BALANCE,
                currencyCode = account.currencyCode
            )
        transactionRepository.createTransaction(balanceTransaction)
    }

}