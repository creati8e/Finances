package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.feature.dashboard.domain.model.DashboardWidget
import serg.chuprin.finances.feature.dashboard.setup.domain.model.DashboardWidgetType
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.12.2020.
 */
class DashboardPeriodSelectorWidgetBuilder @Inject constructor() :
    DashboardWidgetBuilder<DashboardWidget.PeriodSelector> {

    override fun build(
        currentUser: User,
        currentPeriod: DataPeriod
    ): Flow<DashboardWidget.PeriodSelector> {
        return flowOfSingleValue {
            DashboardWidget.PeriodSelector(currentPeriod)
        }
    }

    override fun isForType(widgetType: DashboardWidgetType): Boolean {
        return widgetType == DashboardWidgetType.PERIOD_SELECTOR
    }

}