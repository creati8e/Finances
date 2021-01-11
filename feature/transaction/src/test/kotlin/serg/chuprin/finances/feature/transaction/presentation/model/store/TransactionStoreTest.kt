package serg.chuprin.finances.feature.transaction.presentation.model.store

import com.github.ajalt.timberkt.Timber
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.gherkin.ScenarioBody
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryIdToCategory
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.extensions.TimberConsoleTree
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import serg.chuprin.finances.feature.transaction.presentation.model.store.factory.TransactionStoreTestFactory
import serg.chuprin.finances.feature.transaction.presentation.model.store.factory.TransactionTestStore
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNull
import java.math.BigDecimal
import java.time.LocalDate

/**
 * Created by Sergey Chuprin on 11.01.2021.
 */
object TransactionStoreTest : Spek({

    Timber.plant(TimberConsoleTree())

    Feature("Transaction store test") {

        Scenario("Transaction creation mode") {

            val (testStore, moneyAccounts, categories) =
                TransactionStoreTestFactory.testStore(
                    TransactionScreenArguments.Creation(transitionName = EMPTY_STRING)
                )

            When("Store is started") {
                testStore.start()
            }

            Then("Correct initial state is produced") {
                expectThat(testStore.state) {
                    get { enteredAmount }
                        .describedAs("Entered amount")
                        .isEqualTo(BigDecimal.ZERO)

                    get { chosenDate.localDate }
                        .describedAs("Chosen date")
                        .isEqualTo(LocalDate.now())

                    get { saveButtonIsEnabled }
                        .describedAs("Save transaction button is enabled")
                        .isFalse()

                    get { transactionDeletionButtonIsVisible }
                        .describedAs("Transaction deletion button is visible")
                        .isFalse()

                    get { chosenMoneyAccount.account }
                        .describedAs("Chosen money account")
                        .isEqualTo(moneyAccounts().first(MoneyAccount::isFavorite))

                    get { chosenCategory.category }
                        .describedAs("Chosen category")
                        .isEqualTo(null)

                    get { operation }
                        .describedAs("Operation")
                        .isEqualTo(TransactionChosenOperation.Plain(PlainTransactionType.EXPENSE))

                    get { transactionDefaultData }.isNull()
                }
            }

            clickOnChooseMoneyAccountButton(testStore)

            chooseMoneyAccount(testStore, moneyAccounts)

            clickOnChooseCategoryButton(testStore)

            chooseCategory(testStore, categories)

        }

    }

})

private fun ScenarioBody.chooseCategory(
    testStore: TransactionTestStore,
    categories: () -> CategoryIdToCategory
) {
    lateinit var chosenCategory: Category

    When("Choose category") {
        chosenCategory = categories().entries.last().value.category
        testStore.dispatch(TransactionIntent.ChooseCategory(chosenCategory.id))
    }

    Then("This category became chosen") {
        expectThat(testStore.state) {
            get { this.chosenCategory.category }
                .describedAs("Chosen category")
                .isEqualTo(chosenCategory)

            get { saveButtonIsEnabled }
                .describedAs("Save transaction button is enabled")
                .isFalse()
        }
    }
}

private fun ScenarioBody.clickOnChooseCategoryButton(testStore: TransactionTestStore) {
    When("Click on choose category button") {
        testStore.dispatch(TransactionIntent.ClickOnCategory)
    }

    Then("Categories list navigation event is produced") {
        expectThat(testStore.capturedEvents.last)

            .describedAs("Transaction event")
            .isA<TransactionEvent.NavigateToCategoryPickerScreen>()

            .get { screenArguments }
            .describedAs("Categories list screen arguments")
            .isA<CategoriesListScreenArguments.Picker>()

            .get { categoryType }
            .describedAs("Category type")
            .isEqualTo(CategoryType.EXPENSE)
    }
}

private fun ScenarioBody.chooseMoneyAccount(
    testStore: TransactionTestStore,
    moneyAccounts: () -> Collection<MoneyAccount>
) {
    lateinit var chosenMoneyAccount: MoneyAccount

    When("Choose money account") {
        chosenMoneyAccount = moneyAccounts().first { account ->
            account != testStore.state.chosenMoneyAccount.account
        }
        testStore.dispatch(TransactionIntent.ChooseMoneyAccount(chosenMoneyAccount.id))
    }

    Then("This account became chosen") {
        expectThat(testStore.state) {
            get { this.chosenMoneyAccount.account }
                .describedAs("Chosen money account")
                .isEqualTo(chosenMoneyAccount)

            get { saveButtonIsEnabled }
                .describedAs("Save transaction button is enabled")
                .isFalse()
        }

    }
}

private fun ScenarioBody.clickOnChooseMoneyAccountButton(testStore: TransactionTestStore) {
    When("Click on money account selection button") {
        testStore.dispatch(TransactionIntent.ClickOnMoneyAccount)
    }

    Then("Money accounts navigation event is produced") {
        expectThat(testStore.capturedEvents.last)
            .describedAs("Transaction event")
            .isA<TransactionEvent.NavigateToMoneyAccountPickerScreen>()
            .get { screenArguments }
            .describedAs("Money accounts list screen arguments")
            .isA<MoneyAccountsListScreenArguments.Picker>()
    }
}