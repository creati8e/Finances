package serg.chuprin.finances.feature.dashboard.domain.model

import java.util.*

/**
 * Created by Sergey Chuprin on 19.04.2020.
 *
 * Immutable wrapper for SortedMap<DashboardWidget.Type, DashboardWidget> with predefined comparator.
 * It keeps [DashboardWidget] sorted by order ascending.
 */
class DashboardWidgets :
    SortedMap<DashboardWidget.Type, DashboardWidget> by TreeMap<DashboardWidget.Type, DashboardWidget>(
        WIDGETS_COMPARATOR
    ) {

    companion object {
        private val WIDGETS_COMPARATOR = compareBy(DashboardWidget.Type::order)
    }

    fun add(newWidget: DashboardWidget): DashboardWidgets {
        return DashboardWidgets().apply {
            putAll(this@DashboardWidgets)
            put(newWidget.type, newWidget)
        }
    }

}