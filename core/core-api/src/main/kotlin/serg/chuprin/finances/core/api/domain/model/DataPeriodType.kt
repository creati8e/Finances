package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
enum class DataPeriodType {
    MONTH;

    companion object {
        val DEFAULT = MONTH
        val cachedValues = values().toList()
    }

}