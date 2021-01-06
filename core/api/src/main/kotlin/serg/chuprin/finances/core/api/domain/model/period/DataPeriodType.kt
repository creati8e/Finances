package serg.chuprin.finances.core.api.domain.model.period

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
enum class DataPeriodType {
    DAY,
    WEEK,
    MONTH,
    YEAR;

    companion object {
        val DEFAULT = MONTH
        val cachedValues = values().toList()
    }

}