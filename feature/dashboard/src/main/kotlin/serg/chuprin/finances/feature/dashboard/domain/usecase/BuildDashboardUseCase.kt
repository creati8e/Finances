package serg.chuprin.finances.feature.dashboard.domain.usecase

import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.moneyaccount.query.MoneyAccountsQuery
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.model.Dashboard
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidgets
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import serg.chuprin.finances.feature.dashboard.setup.domain.model.CustomizableDashboardWidget
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import serg.chuprin.finances.feature.dashboard.setup.domain.repository.DashboardWidgetsRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class BuildDashboardUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val moneyAccountRepository: MoneyAccountRepository,
    private val dataPeriodRepository: DashboardDataPeriodRepository,
    private val dashboardWidgetsRepository: DashboardWidgetsRepository,
    private val widgetBuilders: Set<@JvmSuppressWildcards DashboardWidgetBuilder<*>>
) {

    fun execute(): Flow<Dashboard> {
        return userRepository
            .currentUserSingleFlow()
            .flatMapLatest { user ->
                combine(
                    flowOf(user),
                    moneyAccountRepository.accountsFlow(MoneyAccountsQuery(ownerId = user.id)),
                    ::Pair
                )
            }
            // Don't bother downstream with money account updates
            // but only if new account created or all accounts deleted.
            .distinctUntilChanged { (_, oldAccounts), (_, newAccounts) ->
                oldAccounts.size == newAccounts.size
                        || !(oldAccounts.isEmpty() || newAccounts.isEmpty())
            }
            .flatMapLatest { (user, moneyAccounts) ->
                if (moneyAccounts.isEmpty()) {
                    flowOf(Dashboard(user = user, hasNoMoneyAccounts = true))
                } else {
                    combine(
                        flowOf(user),
                        dataPeriodRepository
                            .currentDataPeriodFlow
                            .distinctUntilChanged(),
                        getOrderedWidgetsFlow(),
                        transform = ::Triple
                    ).flatMapLatest { (currentUser, currentPeriod, orderedWidgets) ->
                        buildDashboard(currentUser, currentPeriod, orderedWidgets)
                    }
                }
            }
    }

    private fun buildDashboard(
        currentUser: User,
        currentPeriod: DataPeriod,
        orderedWidgets: Map<DashboardWidgetType, Int>
    ): Flow<Dashboard> {
        val flows = orderedWidgets.mapNotNull { (widgetType) ->
            widgetBuilders
                .firstOrNull { builder -> builder.isForType(widgetType) }
                ?.build(currentUser, currentPeriod)
        }

        return combine(flows) { flowsArray ->
            flowsArray.fold(
                initial = Dashboard(
                    user = currentUser,
                    hasNoMoneyAccounts = false,
                    currentDataPeriod = currentPeriod,
                    widgets = DashboardWidgets(orderedWidgets)
                ),
                { dashboard, widget ->
                    dashboard.copy(widgets = dashboard.widgets.put(widget))
                }
            )
        }.debounce(100)
    }

    private fun getOrderedWidgetsFlow(): Flow<Map<DashboardWidgetType, Int>> {
        return dashboardWidgetsRepository
            .widgetsFlow
            .map { widgets ->
                widgets
                    .filter(CustomizableDashboardWidget::isEnabled)
                    .sortedBy(CustomizableDashboardWidget::order)
                    .mapIndexed { index, customizableDashboardWidget ->
                        customizableDashboardWidget.widgetType to index
                    }
                    .toMap()
            }
    }

}