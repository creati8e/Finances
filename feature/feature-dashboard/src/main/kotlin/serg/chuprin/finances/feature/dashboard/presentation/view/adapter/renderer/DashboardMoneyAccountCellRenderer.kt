package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer

import android.content.res.ColorStateList
import kotlinx.android.synthetic.main.cell_dashboard_money_account.*
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.getAttributeColor
import serg.chuprin.finances.core.api.presentation.view.extensions.getColorInt
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardMoneyAccountCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.payload.DashboardMoneyAccountCellChangedPayload

/**
 * Created by Sergey Chuprin on 21.04.2020.
 */
class DashboardMoneyAccountCellRenderer : ContainerRenderer<DashboardMoneyAccountCell>() {

    override val type: Int = R.layout.cell_dashboard_money_account

    override fun bindView(holder: ContainerHolder, model: DashboardMoneyAccountCell) {
        bindViews(holder, model)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardMoneyAccountCell,
        payloads: MutableList<Any>
    ) {
        if (DashboardMoneyAccountCellChangedPayload in payloads) {
            bindViews(holder, model)
        }
    }

    private fun bindViews(
        holder: ContainerHolder,
        model: DashboardMoneyAccountCell
    ) {
        with(holder) {
            nameTextView.text = model.name
            balanceTextView.text = model.balance
            favoriteImageView.makeVisibleOrGone(model.favoriteIconIsVisible)

            val context = cardView.context
            if (model.favoriteIconIsVisible) {
                val color = context.getColorInt(R.color.colorFavoriteOrangeRipple)
                cardView.rippleColor = ColorStateList.valueOf(color)
            } else {
                val color = context.getAttributeColor(android.R.attr.colorControlHighlight)
                cardView.rippleColor = ColorStateList.valueOf(color)
            }
        }
    }

}