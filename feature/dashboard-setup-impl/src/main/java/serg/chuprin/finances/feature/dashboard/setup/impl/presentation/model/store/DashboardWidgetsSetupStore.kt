package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.BaseStateStore
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 27.11.2019.
 */
@ScreenScope
class DashboardWidgetsSetupStore @Inject constructor(
    actionExecutor: DashboardWidgetsSetupActionExecutor,
    bootstrapper: DashboardWidgetsSetupStoreBootstrapper
) : BaseStateStore<DashboardWidgetsSetupIntent, DashboardWidgetsSetupEffect, DashboardWidgetsSetupAction, DashboardWidgetsSetupState, DashboardWidgetsSetupEvent>(
    executor = actionExecutor,
    bootstrapper = bootstrapper,
    initialState = DashboardWidgetsSetupState(),
    reducer = DashboardWidgetsSetupStateReducer(),
    intentToActionMapper = DashboardWidgetsSetupIntentToActionMapper()
)