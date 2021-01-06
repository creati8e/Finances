package serg.chuprin.finances.feature.dashboard.presentation.model.store

import serg.chuprin.finances.core.mvi.mapper.StoreIntentToActionMapper

/**
 * Created by Sergey Chuprin on 16.04.2020.
 */
class DashboardIntentToActionMapper : StoreIntentToActionMapper<DashboardIntent, DashboardAction> {

    override fun invoke(intent: DashboardIntent): DashboardAction {
        return DashboardAction.ExecuteIntent(intent)
    }

}