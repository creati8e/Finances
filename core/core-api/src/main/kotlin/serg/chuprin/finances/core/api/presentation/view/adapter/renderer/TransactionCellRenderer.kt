package serg.chuprin.finances.core.api.presentation.view.adapter.renderer

import android.content.res.ColorStateList
import kotlinx.android.synthetic.main.cell_transaction.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class TransactionCellRenderer : ContainerRenderer<TransactionCell>() {

    override val type: Int = R.layout.cell_transaction

    override fun bindView(holder: ContainerHolder, model: TransactionCell) {
        with(holder) {
            timeTextView.text = model.time
            amountTextView.text = model.amount
            amountTextView.isActivated = model.isIncome
            subcategoryTextView.text = model.subcategoryName
            parentCategoryTextView.text = model.parentCategoryName
            transactionColorDot.imageTintList = ColorStateList.valueOf(model.color)
            subcategoryTextView.makeVisibleOrGone(model.subcategoryName.isNotEmpty())
        }
    }
}