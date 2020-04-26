package serg.chuprin.finances.feature.dashboard.domain.model

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import strikt.api.expectThat
import strikt.assertions.containsKey
import strikt.assertions.isNotSameInstanceAs
import strikt.assertions.isSorted
import java.math.BigDecimal


/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
object DashboardMoneyAccountsTest : Spek({

    Feature("Dashboard money accounts") {

        Scenario("Adding new account") {

            val originalAccounts = DashboardMoneyAccounts()

            lateinit var accounts1: DashboardMoneyAccounts
            val firstAccount = createAccount("account", true)

            When("New account is added to list") {
                accounts1 = originalAccounts.add(firstAccount, BigDecimal.ONE)
            }

            Then("Original accounts does not contain this account") {
                expectThat(originalAccounts).not().containsKey(firstAccount)
            }

            And("New accounts contains added account") {
                expectThat(accounts1).containsKey(firstAccount)
            }

            And("Original accounts is not same instance as new accounts") {
                expectThat(originalAccounts).isNotSameInstanceAs(accounts1)
            }

        }

        Scenario("Preserving sort order after new accounts adding") {

            lateinit var accounts: DashboardMoneyAccounts

            When("Accounts are added") {
                accounts = DashboardMoneyAccounts()
                    .add(createAccount("a", isFavorite = true), BigDecimal.ONE)
                    .add(createAccount("b", isFavorite = false), BigDecimal.ONE)
                    .add(createAccount("c", isFavorite = false), BigDecimal.ONE)
                    .add(createAccount("d", isFavorite = true), BigDecimal.ONE)
            }

            Then("Keys are properly sorted") {
                expectThat(accounts)
                    .get { keys }
                    .isSorted(DashboardMoneyAccounts.accountsComparator)
            }

        }

    }

})

private fun createAccount(name: String, isFavorite: Boolean): MoneyAccount {
    return MoneyAccount(Id.createNew(), name, Id.createNew(), isFavorite, "rub")
}