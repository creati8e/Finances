package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.mvi.reducer.StoreStateReducer

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class DashboardStateReducer : StoreStateReducer<DashboardEffect, DashboardState> {

    override fun invoke(effect: DashboardEffect, state: DashboardState): DashboardState {
        return when (effect) {
            is DashboardEffect.DashboardUpdated -> {
                state.copy(cells = effect.widgetCells, dashboard = effect.dashboard)
            }
        }
    }

}