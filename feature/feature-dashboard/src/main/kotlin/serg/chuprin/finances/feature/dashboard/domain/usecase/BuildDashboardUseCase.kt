package serg.chuprin.finances.feature.dashboard.domain.usecase

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import serg.chuprin.finances.core.api.domain.model.Dashboard
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.repository.DashboardDataPeriodRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class BuildDashboardUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val dataPeriodRepository: DashboardDataPeriodRepository,
    private val widgetBuilders: Set<@JvmSuppressWildcards DashboardWidgetBuilder<*>>
) {

    @OptIn(FlowPreview::class)
    fun execute(): Flow<Dashboard> {
        return userRepository
            .currentUserSingleFlow()
            .combine(
                dataPeriodRepository
                    .currentDataPeriodFlow
                    .distinctUntilChanged()
            ) { currentUser, currentPeriod ->
                currentUser to currentPeriod
            }
            .flatMapLatest { (currentUser, currentPeriod) ->
                val flows = widgetBuilders.mapNotNull { builder ->
                    builder.build(currentUser, currentPeriod)
                }
                combine(flows) { arr ->
                    arr.fold(
                        Dashboard(currentUser, currentDataPeriod = currentPeriod),
                        { dashboard, widget ->
                            dashboard.copy(widgets = dashboard.widgets.add(widget))
                        }
                    )
                }
            }
    }

}