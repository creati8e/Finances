package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 27.11.2019.
 */
class DashboardWidgetsSetupStateReducer :
    StoreStateReducer<DashboardWidgetsSetupEffect, DashboardWidgetsSetupState> {

    override fun invoke(
        effect: DashboardWidgetsSetupEffect,
        state: DashboardWidgetsSetupState
    ): DashboardWidgetsSetupState {
        return when (effect) {
            is DashboardWidgetsSetupEffect.CellsBuilt -> {
                state.copy(
                    widgetCells = effect.cells,
                    scrollToMovedWidget = effect.scrollToMovedWidget
                )
            }
            is DashboardWidgetsSetupEffect.DefaultCellsBuilt -> {
                state.copy(defaultWidgetCells = effect.cells)
            }
        }
    }

}