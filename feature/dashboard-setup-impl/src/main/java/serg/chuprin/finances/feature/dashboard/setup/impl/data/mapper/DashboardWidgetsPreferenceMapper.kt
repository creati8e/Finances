package serg.chuprin.finances.feature.dashboard.setup.impl.data.mapper

import serg.chuprin.finances.core.api.data.datasource.preferences.mapper.PreferenceMapper
import serg.chuprin.finances.feature.dashboard.setup.domain.model.CustomizableDashboardWidget
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
class DashboardWidgetsPreferenceMapper
@Inject constructor() : PreferenceMapper<Set<CustomizableDashboardWidget>> {

    override val defaultModel: Set<CustomizableDashboardWidget> =
        LinkedHashSet(CustomizableDashboardWidget.default)

    override fun toStringValue(model: Set<CustomizableDashboardWidget>): String {
        return model.joinToString(
            separator = "|",
            transform = { widget ->
                "${widget.toValue()}-${widget.order}-${widget.isEnabled}"
            }
        )
    }

    override fun toModel(stringValue: String): Set<CustomizableDashboardWidget> {
        if (stringValue.isEmpty()) {
            return defaultModel
        }
        return stringValue
            .split("|")
            .mapTo(LinkedHashSet()) { part ->
                val values = part.split("-")
                CustomizableDashboardWidget(
                    order = values[1].toInt(),
                    isEnabled = values[2].toBoolean(),
                    widgetType = values[0].toWidgetType()
                )
            }
    }

    private fun String.toWidgetType(): DashboardWidgetType {
        return when (this) {
            "balance" -> DashboardWidgetType.BALANCE
            "categories" -> DashboardWidgetType.CATEGORIES
            "money_accounts" -> DashboardWidgetType.MONEY_ACCOUNTS
            "period_selector" -> DashboardWidgetType.PERIOD_SELECTOR
            "recent_transactions" -> DashboardWidgetType.RECENT_TRANSACTIONS
            else -> throw IllegalArgumentException("Unknown widget type: $this")
        }
    }

    private fun CustomizableDashboardWidget.toValue(): String {
        return when (this.widgetType) {
            DashboardWidgetType.BALANCE -> "balance"
            DashboardWidgetType.CATEGORIES -> "categories"
            DashboardWidgetType.MONEY_ACCOUNTS -> "money_accounts"
            DashboardWidgetType.PERIOD_SELECTOR -> "period_selector"
            DashboardWidgetType.RECENT_TRANSACTIONS -> "recent_transactions"
        }
    }

}