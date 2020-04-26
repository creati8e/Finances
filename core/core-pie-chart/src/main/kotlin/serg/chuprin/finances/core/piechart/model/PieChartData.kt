package serg.chuprin.finances.core.piechart.model

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
data class PieChartData(
    val parts: List<PieChartDataPart>,
    private val _maxValue: Float? = null
) {

    val maxValue: Float
        get() = _maxValue ?: parts.fold(0f, { acc, v -> acc + v.value })

}