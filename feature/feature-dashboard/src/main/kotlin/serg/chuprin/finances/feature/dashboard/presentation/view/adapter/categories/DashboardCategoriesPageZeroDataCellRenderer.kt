package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories

import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.categories.page.DashboardCategoriesPageZeroDataCell

/**
 * Created by Sergey Chuprin on 28.04.2020.
 */
class DashboardCategoriesPageZeroDataCellRenderer :
    ContainerRenderer<DashboardCategoriesPageZeroDataCell>() {

    override val type: Int = R.layout.cell_dashboard_categories_page_zero_data

}