package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer.transactions

import kotlinx.android.synthetic.main.cell_dashboard_transaction.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions.DashboardTransactionCell

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
class DashboardTransactionCellRenderer : ContainerRenderer<DashboardTransactionCell>() {

    override val type: Int = R.layout.cell_dashboard_transaction

    override fun bindView(holder: ContainerHolder, model: DashboardTransactionCell) {
        with(holder) {
            amountTextView.text = model.amount
            dateTextView.text = model.formattedDate
            categoryTextView.text = model.categoryName
            amountTextView.setTextColor(model.amountColor)
        }
    }

}