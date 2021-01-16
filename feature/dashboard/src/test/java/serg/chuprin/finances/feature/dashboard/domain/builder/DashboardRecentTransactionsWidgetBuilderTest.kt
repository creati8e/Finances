package serg.chuprin.finances.feature.dashboard.domain.builder

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import strikt.api.expectThat
import strikt.assertions.isNull

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
object DashboardRecentTransactionsWidgetBuilderTest : Spek({

    Feature("Dashboard recent transactions widget build") {

        val moneyAccountRepository = mockk<MoneyAccountRepository>()
        val dashboardDataPeriodRepository = mockk<DashboardDataPeriodRepository>()
        val transactionCategoryRetrieverService = mockk<TransactionCategoryRetrieverService>()

        val builder = DashboardRecentTransactionsWidgetBuilder(
            moneyAccountRepository,
            dashboardDataPeriodRepository,
            transactionCategoryRetrieverService
        )

        Scenario("Building widget for non-default period") {

            val defaultPeriod = DataPeriod.from(DataPeriodType.MONTH)

            Given("Current period is not default") {
                every { dashboardDataPeriodRepository.defaultDataPeriod } returns defaultPeriod
            }

            var widgetFlow: Flow<DashboardWidget.RecentTransactions>? = emptyFlow()

            When("Builder is called") {
                widgetFlow = builder.build(
                    currentUser = User.EMPTY,
                    currentPeriod = defaultPeriod.minusPeriods(1)
                )
            }

            Then("Returned flow is null") {
                expectThat(widgetFlow).isNull()
            }

        }

    }

})