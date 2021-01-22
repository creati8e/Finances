package serg.chuprin.finances.feature.moneyaccount.domain.usecase

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.feature.moneyaccount.domain.model.MoneyAccountCreationParams
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 14.11.2020.
 */
class CreateMoneyAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
    private val moneyAccountRepository: MoneyAccountRepository
) {

    suspend fun execute(params: MoneyAccountCreationParams) {
        val (currency, accountName, initialBalance) = params

        val moneyAccountId = Id.createNew()
        val currencyCode = currency.currencyCode
        val userId = userRepository.getCurrentUser().id

        createMoneyAccount(userId, accountName, moneyAccountId, currencyCode)

        if (initialBalance != BigDecimal.ZERO) {
            createBalanceTransaction(userId, moneyAccountId, initialBalance, currencyCode)
        }
        createPredefinedCategoriesIfNotExists(userId)
    }

    private fun createMoneyAccount(
        userId: Id,
        accountName: String,
        moneyAccountId: Id,
        currencyCode: String
    ) {
        val moneyAccount = MoneyAccount(
            ownerId = userId,
            name = accountName,
            isFavorite = false,
            id = moneyAccountId,
            currencyCode = currencyCode
        )
        moneyAccountRepository.createOrUpdateAccount(moneyAccount)
    }

    private fun createBalanceTransaction(
        userId: Id,
        moneyAccountId: Id,
        initialBalance: BigDecimal,
        currencyCode: String
    ) {
        val balanceTransaction = Transaction(
            id = Id.createNew(),
            ownerId = userId,
            currencyCode = currencyCode,
            type = TransactionType.BALANCE,
            _dateTime = LocalDateTime.now(),
            moneyAccountId = moneyAccountId,
            _amount = initialBalance.toString()
        )
        transactionRepository.createOrUpdate(listOf(balanceTransaction))
    }

    private suspend fun createPredefinedCategoriesIfNotExists(ownerId: Id) {
        if (categoryRepository.categories(CategoriesQuery(ownerId = ownerId)).isEmpty()) {
            categoryRepository.createPredefinedCategories(ownerId)
        }
    }

}