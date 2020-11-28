package serg.chuprin.finances.feature.dashboard.setup.impl.data.mapper

import serg.chuprin.finances.core.api.data.datasource.preferences.mapper.PreferenceMapper
import serg.chuprin.finances.feature.dashboard.setup.presentation.domain.model.DashboardWidgetType
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
class DashboardWidgetsPreferenceMapper
@Inject constructor() : PreferenceMapper<Set<DashboardWidgetType>> {

    override val defaultModel: Set<DashboardWidgetType> =
        LinkedHashSet(DashboardWidgetType.default)

    override fun toStringValue(model: Set<DashboardWidgetType>): String {
        return model.joinToString(
            separator = "|",
            transform = { type -> type.toValue() }
        )
    }

    override fun toModel(stringValue: String): Set<DashboardWidgetType> {
        if (stringValue.isEmpty()) {
            return defaultModel
        }
        return stringValue
            .split("|")
            .mapTo(LinkedHashSet()) { part ->
                part.substringBefore("-").toWidgetType()
            }
    }

    private fun String.toWidgetType(): DashboardWidgetType {
        return when (this) {
            "balance" -> DashboardWidgetType.BALANCE
            "categories" -> DashboardWidgetType.CATEGORIES
            "money_accounts" -> DashboardWidgetType.MONEY_ACCOUNTS
            "recent_transactions" -> DashboardWidgetType.RECENT_TRANSACTIONS
            else -> throw IllegalArgumentException("Unknown widget type: $this")
        }
    }

    private fun DashboardWidgetType.toValue(): String {
        return when (this) {
            DashboardWidgetType.BALANCE -> "balance"
            DashboardWidgetType.CATEGORIES -> "categories"
            DashboardWidgetType.MONEY_ACCOUNTS -> "money_accounts"
            DashboardWidgetType.RECENT_TRANSACTIONS -> "recent_transactions"
        }
    }

}