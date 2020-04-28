package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories

import android.content.res.ColorStateList
import kotlinx.android.synthetic.main.cell_dashboard_category.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.DashboardCategoryChipCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.diff.payload.DashboardCategoryChipCellChangedPayload

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
class DashboardCategoryChipCellRenderer : ContainerRenderer<DashboardCategoryChipCell>() {

    override val type: Int = R.layout.cell_dashboard_category

    override fun bindView(holder: ContainerHolder, model: DashboardCategoryChipCell) {
        showData(holder, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardCategoryChipCell,
        payloads: MutableList<Any>
    ) {
        if (DashboardCategoryChipCellChangedPayload in payloads) {
            showData(holder, model)
        }
    }

    private fun showData(
        holder: ContainerHolder,
        model: DashboardCategoryChipCell
    ) {
        with(holder.categoryChip) {
            text = model.chipText
            chipBackgroundColor = ColorStateList.valueOf(model.colorInt)
        }
    }

}