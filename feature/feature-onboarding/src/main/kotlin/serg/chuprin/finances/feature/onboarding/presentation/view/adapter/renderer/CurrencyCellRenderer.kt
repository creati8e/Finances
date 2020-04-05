package serg.chuprin.finances.feature.onboarding.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_currency.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.model.cells.CurrencyCell

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
class CurrencyCellRenderer : ContainerRenderer<CurrencyCell>() {
    override val type: Int = R.layout.cell_currency

    override fun bindView(holder: ContainerHolder, model: CurrencyCell) {
        holder.textView.text = model.displayName
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