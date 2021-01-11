package serg.chuprin.finances.feature.transaction.presentation.model.store

import com.github.ajalt.timberkt.Timber
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.extensions.TimberConsoleTree
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import serg.chuprin.finances.feature.transaction.presentation.model.store.factory.TransactionStoreTestFactory
import strikt.api.expectThat
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

            val (testStore, moneyAccounts) = TransactionStoreTestFactory.testStore(
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

    }

})