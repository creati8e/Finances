package serg.chuprin.finances.core.piechart

import android.graphics.Color
import serg.chuprin.finances.core.piechart.model.PieChartData
import serg.chuprin.finances.core.piechart.model.PieChartDataPart

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
internal object PieChartPreviewData {

    val data = PieChartData(
        listOf(
            PieChartDataPart(10f, Color.parseColor("#e57373")),
            PieChartDataPart(20f, Color.parseColor("#29b6f6")),
            PieChartDataPart(60f, Color.parseColor("#9ccc65")),
            PieChartDataPart(41f, Color.parseColor("#78909c")),
            PieChartDataPart(100f, Color.parseColor("#ffca28"))
        )
    )

}