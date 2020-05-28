package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.transactions

import android.content.res.ColorStateList
import kotlinx.android.synthetic.main.cell_dashboard_transaction.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.transactions.DashboardTransactionCell

/**
 * Created by Sergey Chuprin on 28.05.2020.
 */
class DashboardRecentTransactionCellRenderer : ContainerRenderer<DashboardTransactionCell>() {

    override val type: Int = R.layout.cell_dashboard_transaction

    override fun bindView(holder: ContainerHolder, model: DashboardTransactionCell) {
        with(holder) {
            amountTextView.text = model.amount
            dateTextView.text = model.formattedDate
            amountTextView.isActivated = model.isIncome
            subcategoryTextView.text = model.subcategoryName
            parentCategoryTextView.text = model.parentCategoryName
            transactionColorDot.imageTintList = ColorStateList.valueOf(model.color)
            subcategoryTextView.makeVisibleOrGone(model.subcategoryName.isNotEmpty())
        }
    }

}