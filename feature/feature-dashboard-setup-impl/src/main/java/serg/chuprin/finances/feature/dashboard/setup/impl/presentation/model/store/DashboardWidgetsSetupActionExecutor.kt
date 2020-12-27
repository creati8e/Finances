package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.dashboard.setup.domain.model.CustomizableDashboardWidget
import serg.chuprin.finances.feature.dashboard.setup.impl.domain.usecase.UpdateDashboardWidgetsUseCase
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.cells.DraggableDashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.formatter.DashboardWidgetNameFormatter
import java.util.*
import javax.inject.Inject
import kotlin.comparisons.reversed as kotlinReversed

/**
 * Created by Sergey Chuprin on 27.11.2019.
 */
@ScreenScope
class DashboardWidgetsSetupActionExecutor @Inject constructor(
    private val widgetNameFormatter: DashboardWidgetNameFormatter,
    private val updateWidgetsUseCase: UpdateDashboardWidgetsUseCase
) : StoreActionExecutor<DashboardWidgetsSetupAction, DashboardWidgetsSetupState, DashboardWidgetsSetupEffect, DashboardWidgetsSetupEvent> {

    private object DraggableDashboardWidgetDisabledCellSorter :
        Comparator<DraggableDashboardWidgetCell> {

        override fun compare(
            cell1: DraggableDashboardWidgetCell,
            cell2: DraggableDashboardWidgetCell
        ): Int {
            return when {
                cell1.isChecked && cell1.isChecked -> 0
                cell1.isChecked.not() && cell2.isChecked -> -1
                cell1.isChecked.not() && cell2.isChecked.not() -> 0
                else -> 1
            }
        }

    }

    override fun invoke(
        action: DashboardWidgetsSetupAction,
        state: DashboardWidgetsSetupState,
        eventConsumer: Consumer<DashboardWidgetsSetupEvent>,
        actionsFlow: Flow<DashboardWidgetsSetupAction>
    ): Flow<DashboardWidgetsSetupEffect> {
        return when (action) {
            is DashboardWidgetsSetupAction.BuildCells -> {
                handleBuildCellsAction(action)
            }
            is DashboardWidgetsSetupAction.Execute -> {
                when (val intent = action.intent) {
                    is DashboardWidgetsSetupIntent.MoveWidget -> {
                        handleMoveWidgetIntent(state, intent)
                    }
                    DashboardWidgetsSetupIntent.ClickOnSaveIcon -> {
                        handleSaveChangesIntent(state, eventConsumer)
                    }
                    is DashboardWidgetsSetupIntent.ToggleWidget -> {
                        handleToggleWidgetIntent(state, intent)
                    }
                    DashboardWidgetsSetupIntent.ResortWidgets -> handleResortWidgetsIntent(state)
                }
            }
        }
    }

    private fun handleResortWidgetsIntent(
        state: DashboardWidgetsSetupState
    ): Flow<DashboardWidgetsSetupEffect> {
        return flowOfSingleValue {
            DashboardWidgetsSetupEffect.CellsBuilt(
                scrollToMovedWidget = true,
                cells = state.widgetCells.sortDisabled()
            )
        }
    }

    private fun handleToggleWidgetIntent(
        state: DashboardWidgetsSetupState,
        intent: DashboardWidgetsSetupIntent.ToggleWidget
    ): Flow<DashboardWidgetsSetupEffect> {
        return flowOfSingleValue {
            val widgetCell = intent.widgetCell
            val widgetCells = state.widgetCells.toMutableList()

            val updatedWidgetCells = widgetCells.run {
                set(
                    widgetCells.indexOf(widgetCell),
                    widgetCell.copy(isChecked = widgetCell.isChecked.not())
                )
                sortDisabled()
            }

            DashboardWidgetsSetupEffect.CellsBuilt(
                cells = updatedWidgetCells,
                scrollToMovedWidget = false
            )
        }
    }

    private fun handleMoveWidgetIntent(
        state: DashboardWidgetsSetupState,
        intent: DashboardWidgetsSetupIntent.MoveWidget
    ): Flow<DashboardWidgetsSetupEffect> {
        return flowOfSingleValue {
            val widgetCells = state.widgetCells.toMutableList()
            Collections.swap(widgetCells, intent.fromPosition, intent.toPosition)
            DashboardWidgetsSetupEffect.CellsBuilt(widgetCells, scrollToMovedWidget = true)
        }
    }

    private fun handleSaveChangesIntent(
        state: DashboardWidgetsSetupState,
        eventConsumer: Consumer<DashboardWidgetsSetupEvent>
    ): Flow<DashboardWidgetsSetupEffect> {
        return emptyFlowAction {
            val updatedWidgets = state.widgetCells
                .mapIndexedTo(mutableSetOf()) { index, widgetCell ->
                    CustomizableDashboardWidget(
                        order = index,
                        widgetType = widgetCell.type,
                        isEnabled = widgetCell.isChecked
                    )
                }
            updateWidgetsUseCase.execute(updatedWidgets)
            eventConsumer(DashboardWidgetsSetupEvent.CloseScreen)
        }
    }

    private fun handleBuildCellsAction(
        action: DashboardWidgetsSetupAction.BuildCells
    ): Flow<DashboardWidgetsSetupEffect> {
        return flow {
            val widgetCells = action.widgets.map { widget ->
                DraggableDashboardWidgetCell(
                    type = widget.widgetType,
                    isChecked = widget.isEnabled,
                    name = widgetNameFormatter.format(widget.widgetType)
                )
            }
            emit(DashboardWidgetsSetupEffect.DefaultCellsBuilt(widgetCells))
            emit(DashboardWidgetsSetupEffect.CellsBuilt(widgetCells, scrollToMovedWidget = false))
        }
    }

    private fun List<DraggableDashboardWidgetCell>.sortDisabled(): List<DraggableDashboardWidgetCell> {
        return sortedWith(DraggableDashboardWidgetDisabledCellSorter.kotlinReversed())
    }

}