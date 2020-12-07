package serg.chuprin.finances.feature.dashboard.presentation.model.builder

import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.12.2020.
 */
class DashboardPeriodSelectorWidgetCellBuilder @Inject constructor(
    private val dataPeriodFormatter: DataPeriodFormatter
) : DashboardWidgetCellBuilder {

    override fun build(widget: DashboardWidget): DashboardWidgetCell? {
        if (widget !is DashboardWidget.PeriodSelector) {
            return null
        }
        return DashboardWidgetCell.PeriodSelector(
            widget = widget,
            currentPeriod = dataPeriodFormatter.formatAsCurrentPeriod(widget.dataPeriod)
        )
    }

}