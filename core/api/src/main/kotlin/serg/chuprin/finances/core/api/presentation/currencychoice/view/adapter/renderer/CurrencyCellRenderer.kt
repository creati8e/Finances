package serg.chuprin.finances.core.api.presentation.currencychoice.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_currency.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.currencychoice.model.cells.CurrencyCell
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
class CurrencyCellRenderer : ContainerRenderer<CurrencyCell>() {

    override val type: Int = R.layout.cell_currency

    override fun bindView(viewHolder: ContainerHolder, cell: CurrencyCell) {
        with(viewHolder.textView) {
            text = cell.displayName
            isActivated = cell.isChosen
        }
    }

    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(viewHolder) {
            itemView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

}