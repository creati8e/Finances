package serg.chuprin.finances.core.api.presentation.view.adapter.renderer

import android.content.res.ColorStateList
import kotlinx.android.synthetic.main.cell_transaction.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.domain.model.transaction.TransactionType
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.core.api.presentation.view.extensions.getColorInt
import serg.chuprin.finances.core.api.presentation.view.extensions.getPrimaryTextColor
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class TransactionCellRenderer : ContainerRenderer<TransactionCell>() {

    override val type: Int = R.layout.cell_transaction

    override fun bindView(holder: ContainerHolder, model: TransactionCell) {
        with(holder) {
            timeTextView.text = model.time
            subcategoryTextView.text = model.subcategoryName
            parentCategoryTextView.text = model.parentCategoryName
            transactionColorDot.imageTintList = ColorStateList.valueOf(model.color)
            subcategoryTextView.makeVisibleOrGone(model.subcategoryName.isNotEmpty())

            with(amountTextView) {
                text = model.amount

                when (model.transaction.type) {
                    TransactionType.PLAIN -> {
                        if (model.transaction.isIncome) {
                            setTextColor(context.getColorInt(R.color.colorGreen))
                        } else {
                            setTextColor(context.getColorInt(R.color.colorRed))
                        }
                    }
                    TransactionType.BALANCE -> {
                        setTextColor(context.getPrimaryTextColor())
                    }
                }
            }
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            itemView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

}