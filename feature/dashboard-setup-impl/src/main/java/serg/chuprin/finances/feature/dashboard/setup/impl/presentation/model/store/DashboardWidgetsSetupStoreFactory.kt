package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.store.factory.AbsStoreFactory
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 27.11.2019.
 */
@ScreenScope
class DashboardWidgetsSetupStoreFactory @Inject constructor(
    actionExecutor: DashboardWidgetsSetupActionExecutor,
    bootstrapper: DashboardWidgetsSetupStoreBootstrapper
) : AbsStoreFactory<DashboardWidgetsSetupIntent, DashboardWidgetsSetupEffect, DashboardWidgetsSetupAction, DashboardWidgetsSetupState, DashboardWidgetsSetupEvent, DashboardWidgetsSetupStore>(
    actionExecutor = actionExecutor,
    bootstrapper = bootstrapper,
    initialState = DashboardWidgetsSetupState(),
    reducer = DashboardWidgetsSetupStateReducer(),
    intentToActionMapper = DashboardWidgetsSetupAction::Execute
)