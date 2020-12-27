package serg.chuprin.finances.core.impl.presentation.debug

import android.app.Application
import com.github.ajalt.timberkt.Timber
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.common.configuration.Appearance
import com.pandulapeter.beagle.modules.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.OnboardingStep
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.category.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.moneyaccount.query.MoneyAccountsQuery
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.domain.model.transaction.query.TransactionsQuery
import serg.chuprin.finances.core.api.domain.repository.*
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.impl.BuildConfig
import serg.chuprin.finances.core.impl.R
import serg.chuprin.finances.core.impl.di.initializer.AppDebugMenuInitializer
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 27.04.2020.
 */
internal class AppDebugMenuImpl @Inject constructor(
    private val resourceManger: ResourceManger,
    private val userRepository: UserRepository,
    private val onboardingRepository: OnboardingRepository,
    private val transactionRepository: TransactionRepository,
    private val moneyAccountRepository: MoneyAccountRepository,
    private val transactionCategoryRepository: TransactionCategoryRepository
) : AppDebugMenuInitializer {

    private companion object {

        private val moneyAccountNames = listOf(
            "Debit card",
            "Debts",
            "Investition",
            "Bank",
            "Business",
            "Cash",
            "Personal",
            "Wife",
            "Family",
            "Broker",
            "Deposit",
            "Loan",
            "Tinkoff",
            "Sberbank",
            "Tochka bank",
            "Modulbank",
            "Alfa Bank",
            "Checking"
        )

    }

    override fun initialize(application: Application) {
        Timber.d { "Debug menu is initialized" }
        Beagle.initialize(
            application,
            Appearance(themeResourceId = R.style.DebugMenuTheme)
        )
        Beagle.set(
            HeaderModule(
                title = resourceManger.getString(CoreR.string.app_name),
                text = "v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
            ),
            AppInfoButtonModule(),
            AnimationDurationSwitchModule(),
            KeylineOverlaySwitchModule(),
            PaddingModule(),
            TextModule(
                text = "Data generation",
                type = TextModule.Type.SECTION_HEADER
            ),
            TextModule(
                text = "Create income transaction",
                type = TextModule.Type.BUTTON,
                onItemSelected = {
                    launch { createTransaction(TransactionCategoryType.INCOME) }
                }
            ),
            TextModule(
                text = "Create expense transaction",
                type = TextModule.Type.BUTTON,
                onItemSelected = {
                    launch { createTransaction(TransactionCategoryType.EXPENSE) }
                }
            ),
            TextModule(
                text = "Create money account",
                type = TextModule.Type.BUTTON,
                onItemSelected = {
                    launch { createMoneyAccount() }
                }
            ),
            PaddingModule(),
            TextModule(
                text = "Dangerous",
                type = TextModule.Type.SECTION_HEADER
            ),
            TextModule(
                text = "Delete all account data",
                type = TextModule.Type.BUTTON,
                onItemSelected = {
                    launch { deleteAccountData() }
                }
            )
        )
    }

    override fun open() {
        Beagle.show()
    }

    private suspend fun createTransaction(categoryType: TransactionCategoryType) {
        val currentUser = userRepository.getCurrentUser()

        val categoryWithParent = getRandomCategory(currentUser, categoryType)
        val moneyAccount = getRandomMoneyAccount(currentUser)

        val amount = ThreadLocalRandom.current().nextInt(100, 2000).run {
            when (categoryType) {
                TransactionCategoryType.INCOME -> this
                TransactionCategoryType.EXPENSE -> this * -1
            }
        }

        val transaction = Transaction(
            id = Id.createNew(),
            ownerId = currentUser.id,
            _amount = amount.toString(),
            type = TransactionType.PLAIN,
            moneyAccountId = moneyAccount.id,
            currencyCode = moneyAccount.currencyCode,
            categoryId = categoryWithParent?.category?.id
        )
        transactionRepository.createTransaction(transaction)
        Timber.d { "New test transaction created: $transaction" }
    }

    private suspend fun createMoneyAccount() {
        val currentUser = userRepository.getCurrentUser()
        val moneyAccount = MoneyAccount(
            id = Id.createNew(),
            ownerId = currentUser.id,
            name = moneyAccountNames.shuffled().first(),
            currencyCode = currentUser.defaultCurrencyCode,
            isFavorite = ThreadLocalRandom.current().nextBoolean()
        )
        moneyAccountRepository.createAccount(moneyAccount)
        Timber.d { "New test money account created: $moneyAccount" }
    }

    private fun launch(block: suspend () -> Unit) {
        GlobalScope.launch {
            withContext(Dispatchers.Default) {
                block()
            }
        }
    }

    private suspend fun getRandomMoneyAccount(currentUser: User): MoneyAccount {
        return moneyAccountRepository
            .accountsFlow(MoneyAccountsQuery(ownerId = currentUser.id))
            .first()
            .shuffled()
            .first()
    }

    private suspend fun getRandomCategory(
        currentUser: User,
        type: TransactionCategoryType
    ): TransactionCategoryWithParent? {
        return if (ThreadLocalRandom.current().nextBoolean()) {
            transactionCategoryRepository
                .categoriesFlow(TransactionCategoriesQuery(ownerId = currentUser.id, type = type))
                .first()
                .entries
                .shuffled()
                .first()
                .value
        } else {
            null
        }
    }

    private suspend fun deleteAccountData() {
        val currentUser = userRepository.getCurrentUser()

        val transactions = transactionRepository
            .transactionsFlow(TransactionsQuery(ownerId = currentUser.id))
            .first()
        transactionRepository.deleteTransactions(transactions)

        val accounts = moneyAccountRepository
            .accountsFlow(MoneyAccountsQuery(ownerId = currentUser.id))
            .first()
        moneyAccountRepository.deleteAccounts(accounts)

        val categories = transactionCategoryRepository
            .categoriesFlow(TransactionCategoriesQuery(ownerId = currentUser.id))
            .first()
        transactionCategoryRepository.deleteCategories(categories.values.map { it.category })

        onboardingRepository.onboardingStep = OnboardingStep.INITIAL
    }

}