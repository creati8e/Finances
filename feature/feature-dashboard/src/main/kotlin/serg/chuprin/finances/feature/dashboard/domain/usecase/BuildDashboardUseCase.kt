package serg.chuprin.finances.feature.dashboard.domain.usecase

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.model.Dashboard
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class BuildDashboardUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val dataPeriodRepository: DashboardDataPeriodRepository,
    private val widgetBuilders: Set<@JvmSuppressWildcards DashboardWidgetBuilder<*>>
) {

    private companion object {
        private val WIDGETS_COMPARATOR = compareBy(DashboardWidget.Type::order)
    }

    @OptIn(FlowPreview::class)
    fun execute(): Flow<Dashboard> {
        return userRepository
            .currentUserSingleFlow()
            .combine(dataPeriodRepository.currentDataPeriodFlow) { currentUser, currentPeriod ->
                currentUser to currentPeriod
            }
            .flatMapLatest { (currentUser, currentPeriod) ->
                widgetBuilders
                    .map { builder ->
                        builder.build(currentUser, currentPeriod)
                    }
                    .merge()
                    .scan(
                        Dashboard(currentUser),
                        { dashboard, widget ->
                            dashboard.copy(widgetsMap = addWidget(dashboard.widgetsMap, widget))
                        }
                    )
            }
    }

    private fun addWidget(
        existingWidgets: Map<DashboardWidget.Type, DashboardWidget>,
        newWidget: DashboardWidget
    ): Map<DashboardWidget.Type, DashboardWidget> {
        return TreeMap<DashboardWidget.Type, DashboardWidget>(WIDGETS_COMPARATOR).apply {
            putAll(existingWidgets)
            put(newWidget.type, newWidget)
        }
    }

}