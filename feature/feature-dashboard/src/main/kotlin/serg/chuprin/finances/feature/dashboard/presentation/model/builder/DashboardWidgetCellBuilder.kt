package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
interface DashboardWidgetCellBuilder {

    fun build(widget: DashboardWidget): DashboardWidgetCell?

}