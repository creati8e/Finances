package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.dashboard.setup.presentation.domain.model.DashboardWidgetType
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
class DashboardWidgetNameFormatter @Inject constructor(
    private val resourceManger: ResourceManger
) {

    fun format(widgetType: DashboardWidgetType): String {
        return when (widgetType) {
            // TODO: Translate.
            DashboardWidgetType.BALANCE -> "Balance"
            DashboardWidgetType.CATEGORIES -> "Categories"
            DashboardWidgetType.MONEY_ACCOUNTS -> "Money accounts"
            DashboardWidgetType.RECENT_TRANSACTIONS -> "Recent transactions"
        }
    }

}