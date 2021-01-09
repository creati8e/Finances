package serg.chuprin.finances.feature.dashboard.domain.model

import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccountToBalance
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import java.util.*

/**
 * Created by Sergey Chuprin on 19.04.2020.
 *
 * Immutable wrapper for SortedMap<DashboardWidget.Type, DashboardWidget> with predefined comparator.
 * It keeps [DashboardWidget] sorted by order specified in [orderedWidgets].
 */
class DashboardWidgets(
    private val orderedWidgets: Map<DashboardWidgetType, Int> = emptyMap()
) : SortedMap<DashboardWidgetType, DashboardWidget> by TreeMap(
    compareBy<DashboardWidgetType>(orderedWidgets::getValue)
) {

    /**
     * Add or update widget in map with existing order preserving.
     */
    fun put(newWidget: DashboardWidget): DashboardWidgets {
        return DashboardWidgets(orderedWidgets)
            .apply {
                putAll(this@DashboardWidgets)
                put(newWidget.type, newWidget)
            }
    }

    override fun hashCode(): Int = entries.hashCode()

    override fun equals(other: Any?): Boolean {
        return (other as? MoneyAccountToBalance)?.entries == entries
    }

}