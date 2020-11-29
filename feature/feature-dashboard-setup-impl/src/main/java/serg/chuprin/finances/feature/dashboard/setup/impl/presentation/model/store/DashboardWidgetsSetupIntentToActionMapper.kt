package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store

import serg.chuprin.finances.core.mvi.mapper.StoreIntentToActionMapper

/**
 * Created by Sergey Chuprin on 27.11.2019.
 */
class DashboardWidgetsSetupIntentToActionMapper :
    StoreIntentToActionMapper<DashboardWidgetsSetupIntent, DashboardWidgetsSetupAction> {

    override fun invoke(intent: DashboardWidgetsSetupIntent): DashboardWidgetsSetupAction {
        return DashboardWidgetsSetupAction.Execute(intent)
    }

}