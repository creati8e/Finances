package serg.chuprin.finances.feature.dashboard.domain.model

import serg.chuprin.finances.core.api.domain.model.MoneyAccountBalances
import java.util.*

/**
 * Created by Sergey Chuprin on 19.04.2020.
 *
 * Immutable wrapper for SortedMap<DashboardWidget.Type, DashboardWidget> with predefined comparator.
 * It keeps [DashboardWidget] sorted by order ascending.
 */
class DashboardWidgets :
    SortedMap<DashboardWidget.Type, DashboardWidget> by TreeMap<DashboardWidget.Type, DashboardWidget>(
        widgetsComparator
    ) {

    companion object {
        private val widgetsComparator = compareBy(DashboardWidget.Type::order)
    }

    fun add(newWidget: DashboardWidget): DashboardWidgets {
        return DashboardWidgets()
            .apply {
                putAll(this@DashboardWidgets)
                put(newWidget.type, newWidget)
            }
    }

    override fun hashCode(): Int = entries.hashCode()

    override fun equals(other: Any?): Boolean {
        return (other as? MoneyAccountBalances)?.entries == entries
    }

}