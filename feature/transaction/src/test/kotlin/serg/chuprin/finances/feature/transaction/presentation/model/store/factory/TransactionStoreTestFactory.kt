package serg.chuprin.finances.feature.transaction.presentation.model.store.factory

import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.test.presentation.formatter.CategoryWithParentFormatterTestImpl
import serg.chuprin.finances.core.test.presentation.mvi.factory.TestStoreFactory.Companion.test
import serg.chuprin.finances.feature.transaction.R
import serg.chuprin.finances.feature.transaction.domain.usecase.CreateTransactionUseCase
import serg.chuprin.finances.feature.transaction.domain.usecase.DeleteTransactionUseCase
import serg.chuprin.finances.feature.transaction.domain.usecase.EditTransactionUseCase
import serg.chuprin.finances.feature.transaction.presentation.model.formatter.TransactionChosenDateFormatter
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionActionExecutor
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionStoreBootstrapper
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionStoreFactory
import java.util.*

/**
 * Created by Sergey Chuprin on 11.01.2021.
 */
object TransactionStoreTestFactory {

    fun testStore(
        screenArguments: TransactionScreenArguments
    ): TransactionStoreTestData {

        val testUser = createTestUser()
        val testMoneyAccounts = createTestMoneyAccounts(testUser.id)

        val amountParser = mockk<AmountParser>()
        val resourceManger = mockk<ResourceManger>().apply {
            every { getString(any()) } returns "some_string"
        }

        val chosenDateFormatter = TransactionChosenDateFormatter(resourceManger.apply {
            every { getString(R.string.transaction_date_today) } returns "Today"
            every { getString(R.string.transaction_date_yesterday) } returns "Yesterday"
        })

        val categoryRepository = mockk<CategoryRepository>()
        val transactionRepository = mockk<TransactionRepository>().apply {
            every { createOrUpdate(any()) } just runs
            every { deleteTransactions(any()) } just runs
        }
        val userRepository = mockk<UserRepository>().apply {
            coEvery { getCurrentUser() } returns testUser
        }

        val moneyAccountRepository = mockMoneyAccountRepository(testMoneyAccounts)

        val actionExecutor = TransactionActionExecutor(
            amountParser = amountParser,
            resourceManger = resourceManger,
            screenArguments = screenArguments,
            categoryRepository = categoryRepository,
            chosenDateFormatter = chosenDateFormatter,
            moneyAccountRepository = moneyAccountRepository,
            categoryNameFormatter = CategoryWithParentFormatterTestImpl(),
            editTransactionUseCase = EditTransactionUseCase(transactionRepository),
            createTransactionUseCase = CreateTransactionUseCase(transactionRepository),
            deleteTransactionUseCase = DeleteTransactionUseCase(transactionRepository)
        )

        val bootstrapper = TransactionStoreBootstrapper(
            userRepository = userRepository,
            screenArguments = screenArguments,
            transactionRepository = transactionRepository,
            moneyAccountRepository = moneyAccountRepository
        )

        val store = TransactionStoreFactory(actionExecutor, bootstrapper).test()
        return TransactionStoreTestData(store, { testMoneyAccounts })
    }

    private fun mockMoneyAccountRepository(
        testMoneyAccounts: List<MoneyAccount>
    ): MoneyAccountRepository {
        return mockk<MoneyAccountRepository>().apply {
            coEvery { accounts(any()) } returns testMoneyAccounts

            val moneyAccountIdSlot = slot<String>()
            every {
                // It's a hack. MockK can't capture inline class.
                // This way slot with wrapped value is used and inline class is manually created.
                accountFlow(Id(capture(moneyAccountIdSlot)))
            } answers {
                flowOf(testMoneyAccounts.find { it.id.value == moneyAccountIdSlot.captured })
            }
        }
    }

    private fun createTestMoneyAccounts(ownerId: Id): List<MoneyAccount> {
        val currencies = Currency.getAvailableCurrencies().toList()
        return (0..5).map { index ->
            MoneyAccount(
                id = Id.createNew(),
                name = "Account_$index",
                ownerId = ownerId,
                isFavorite = index % 2 == 0,
                currencyCode = currencies[index].currencyCode
            )
        }
    }

    private fun createTestUser(): User {
        return User(
            id = Id.existing("1"),
            email = "email",
            photoUrl = EMPTY_STRING,
            displayName = EMPTY_STRING,
            dataPeriodType = DataPeriodType.MONTH,
            defaultCurrencyCode = "RUB"
        )
    }

}