package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories

import android.content.res.ColorStateList
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.cell_dashboard_category.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.DashboardCategoryChipCell

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
class DashboardCategoryChipCellRenderer : ContainerRenderer<DashboardCategoryChipCell>() {
    override val type: Int = R.layout.cell_dashboard_category

    override fun bindView(holder: ContainerHolder, model: DashboardCategoryChipCell) {
        Timber.d { "Bind cells: ${model.chipText}" }
        with(holder.categoryChip) {
            text = model.chipText
            chipBackgroundColor = ColorStateList.valueOf(model.colorInt)
        }
    }

}