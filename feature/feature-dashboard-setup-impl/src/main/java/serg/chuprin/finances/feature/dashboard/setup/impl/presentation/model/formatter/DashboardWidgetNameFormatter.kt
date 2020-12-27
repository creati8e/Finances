package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.formatter

import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import serg.chuprin.finances.feature.dashboard.setup.impl.R
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
class DashboardWidgetNameFormatter @Inject constructor(
    private val resourceManger: ResourceManger
) {

    fun format(widgetType: DashboardWidgetType): String {
        val stringRes = when (widgetType) {
            DashboardWidgetType.BALANCE -> R.string.dashboard_widget_balance
            DashboardWidgetType.CATEGORIES -> R.string.dashboard_widget_categories
            DashboardWidgetType.MONEY_ACCOUNTS -> R.string.dashboard_widget_money_accounts
            DashboardWidgetType.PERIOD_SELECTOR -> R.string.dashboard_widget_period_selector
            DashboardWidgetType.RECENT_TRANSACTIONS -> R.string.dashboard_widget_recent_transactions
        }
        return resourceManger.getString(stringRes)
    }

}