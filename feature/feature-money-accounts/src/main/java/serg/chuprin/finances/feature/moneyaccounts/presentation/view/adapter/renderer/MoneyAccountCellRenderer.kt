package serg.chuprin.finances.feature.moneyaccounts.presentation.view.adapter.renderer

import kotlinx.android.synthetic.main.cell_money_account.*
import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.feature.moneyaccounts.R
import serg.chuprin.finances.feature.moneyaccounts.presentation.model.cells.MoneyAccountCell

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
class MoneyAccountCellRenderer : ContainerRenderer<MoneyAccountCell>() {

    override val type: Int = R.layout.cell_money_account

    override fun bindView(holder: ContainerHolder, model: MoneyAccountCell) {
        with(holder) {
            nameTextView.text = model.name
            balanceTextView.text = model.balance
            cardView.isActivated = model.favoriteIconIsVisible
            favoriteImageView.makeVisibleOrGone(model.favoriteIconIsVisible)
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            cardView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
    }

}