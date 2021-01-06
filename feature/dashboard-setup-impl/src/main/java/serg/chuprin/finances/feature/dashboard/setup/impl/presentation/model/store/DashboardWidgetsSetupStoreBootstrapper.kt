package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.dashboard.setup.impl.domain.usecase.GetActiveDashboardWidgetsUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 27.11.2019.
 */
@ScreenScope
class DashboardWidgetsSetupStoreBootstrapper @Inject constructor(
    private val getActiveDashboardWidgetsUseCase: GetActiveDashboardWidgetsUseCase
) : StoreBootstrapper<DashboardWidgetsSetupAction> {

    override fun invoke(): Flow<DashboardWidgetsSetupAction> {
        return flowOfSingleValue {
            DashboardWidgetsSetupAction.BuildCells(getActiveDashboardWidgetsUseCase.execute())
        }
    }

}