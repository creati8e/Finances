package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
typealias DashboardStore = StateStore<DashboardIntent, DashboardState, DashboardEvent>