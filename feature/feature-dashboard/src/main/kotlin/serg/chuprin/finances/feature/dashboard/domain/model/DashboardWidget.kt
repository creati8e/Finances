package serg.chuprin.finances.feature.dashboard.domain.model

import serg.chuprin.finances.core.api.domain.model.DataPeriod
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
sealed class DashboardWidget(val type: Type) {

    enum class Type(val order: Int) {
        HEADER(1)
    }

    /**
     * Contains current period, balance and money statistic.
     */
    data class Header(
        val dataPeriod: DataPeriod,
        val balance: BigDecimal,
        val currentPeriodIncomes: BigDecimal,
        val currentPeriodExpenses: BigDecimal
    ) : DashboardWidget(type = Type.HEADER)

}