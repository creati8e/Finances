package serg.chuprin.finances.feature.dashboard.domain.usecase

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.feature.dashboard.domain.builder.DashboardWidgetBuilder
import serg.chuprin.finances.feature.dashboard.domain.model.Dashboard
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
                            dashboard.copy(widgets = dashboard.widgets.add(widget))
                        }
                    )
            }
    }

}