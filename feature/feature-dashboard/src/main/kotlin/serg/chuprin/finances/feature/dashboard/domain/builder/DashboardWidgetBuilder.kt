package serg.chuprin.finances.feature.dashboard.domain.builder

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.DashboardWidget
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.period.DataPeriod

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
interface DashboardWidgetBuilder<W : DashboardWidget> {

    /**
     * Method can return null if building widget is not possible or not required.
     */
    fun build(currentUser: User, currentPeriod: DataPeriod): Flow<W>?

}