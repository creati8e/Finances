package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
@ScreenScope
class DashboardStore @Inject constructor(
    executor: DashboardActionExecutor,
    bootstrapper: DashboardStoreBootstrapper
) : BaseStateStore<DashboardIntent, DashboardEffect, DashboardAction, DashboardState, DashboardEvent>(
    DashboardState(),
    DashboardStateReducer(),
    bootstrapper,
    executor,
    DashboardIntentToActionMapper()
)