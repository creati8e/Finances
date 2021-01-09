package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
@ScreenScope
class DashboardStoreFactory @Inject constructor(
    actionExecutor: DashboardActionExecutor,
    bootstrapper: DashboardStoreBootstrapper
) : AbsStoreFactory<DashboardIntent, DashboardEffect, DashboardAction, DashboardState, DashboardEvent, DashboardStore>(
    DashboardState(),
    DashboardStateReducer(),
    bootstrapper,
    actionExecutor,
    DashboardAction::ExecuteIntent
)