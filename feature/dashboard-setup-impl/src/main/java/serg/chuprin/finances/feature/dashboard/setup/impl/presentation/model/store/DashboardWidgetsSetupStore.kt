package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import serg.chuprin.finances.core.mvi.store.StateStore

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
typealias DashboardWidgetsSetupStore = StateStore<DashboardWidgetsSetupIntent, DashboardWidgetsSetupState, DashboardWidgetsSetupEvent>