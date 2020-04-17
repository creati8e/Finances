package serg.chuprin.finances.feature.dashboard.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.feature.dashboard.presentation.model.builder.DashboardWidgetCellBuilder
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class DashboardActionExecutor @Inject constructor(
    private val widgetCellBuilders: @JvmSuppressWildcards Set<DashboardWidgetCellBuilder>
) : StoreActionExecutor<DashboardAction, DashboardState, DashboardEffect, DashboardEvent> {

    override fun invoke(
        action: DashboardAction,
        state: DashboardState,
        eventConsumer: Consumer<DashboardEvent>,
        actionsFlow: Flow<DashboardAction>
    ): Flow<DashboardEffect> {
        return when (action) {
            is DashboardAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    else -> TODO()
                }
            }
            is DashboardAction.UpdateUser -> {
                flowOf(DashboardEffect.UserUpdated(action.user))
            }
            is DashboardAction.FormatDashboard -> {
                handleFormatDashboardAction(action)
            }
        }
    }

    private fun handleFormatDashboardAction(
        action: DashboardAction.FormatDashboard
    ): Flow<DashboardEffect> {
        return flow {
            val widgetCells = action.dashboard.widgetsMap.mapNotNull { (_, widget) ->
                // TODO: It is not suspendable.
                // TODO: And this is bad.
                var cell: DashboardWidgetCell?
                widgetCellBuilders.forEach { builder ->
                    cell = builder.build(widget)
                    if (cell != null) {
                        return@mapNotNull cell
                    }
                }
                null
            }
            emit(DashboardEffect.WidgetCellsUpdated(widgetCells))
        }
    }

}