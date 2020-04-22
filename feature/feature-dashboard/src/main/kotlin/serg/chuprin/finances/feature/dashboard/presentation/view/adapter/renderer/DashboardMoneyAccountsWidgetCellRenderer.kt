package serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cell_widget_dashboard_money_accounts.*
import serg.chuprin.adapter.*
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardMoneyAccountCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.payload.DashboardMoneyAccountsExpansionChangedPayload


/**
 * Created by Sergey Chuprin on 21.04.2020.
 */
class DashboardMoneyAccountsWidgetCellRenderer :
    ContainerRenderer<DashboardWidgetCell.MoneyAccounts>() {

    override val type: Int = R.layout.cell_widget_dashboard_money_accounts

    private val moneyAccountCellsAdapter =
        DiffMultiViewAdapter(DiffCallback<DashboardMoneyAccountCell>()).apply {
            registerRenderer(DashboardMoneyAccountCellRenderer())
        }

    // TODO: Add payload.
    override fun bindView(holder: ContainerHolder, model: DashboardWidgetCell.MoneyAccounts) {
        moneyAccountCellsAdapter.setItems(model.cells)
        showExpansionIcon(holder, model.isExpanded)
        holder.expandableLayout.makeVisibleOrGone(model.isExpanded)
    }

    override fun bindView(
        holder: ContainerHolder,
        model: DashboardWidgetCell.MoneyAccounts,
        payloads: MutableList<Any>
    ) {
        if (DashboardMoneyAccountsExpansionChangedPayload in payloads) {
            showExpansionIcon(holder, model.isExpanded)

            val expandableLayout = holder.expandableLayout.also { it.animation?.cancel() }
            if (model.isExpanded) {
                collapse(expandableLayout)
            } else {
                expand(expandableLayout)
            }
        }
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        with(holder) {
            subtitleTextView.onViewClick { view ->
                clickListener?.onClick(view, adapterPosition)
            }
        }
        with(holder.recyclerView) {
            adapter = moneyAccountCellsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showExpansionIcon(holder: ContainerHolder, isExpanded: Boolean) {
        with(holder.subtitleTextView) {
            val drawableRes = if (isExpanded) R.drawable.ic_collapse else R.drawable.ic_expand
            drawableEnd = context.drawable(drawableRes)
        }
    }

    private fun expand(expandableLayout: LinearLayoutCompat) {
        animate(expandableLayout) { view, interpolatedTime, viewHeight ->
            if (interpolatedTime == 1f) {
                view.makeGone()
            } else {
                with(view) {
                    alpha = 1f - interpolatedTime
                    layoutParams.height = viewHeight - (viewHeight * interpolatedTime).toInt()
                    requestLayout()
                }
            }
        }
    }

    private fun collapse(expandableLayout: LinearLayoutCompat) {
        with(expandableLayout) {
            // Measure first to get measured height.
            measure(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.height = 0
            makeVisible()
        }
        animate(expandableLayout) { view, interpolatedTime, viewHeight ->
            with(view) {
                alpha = interpolatedTime
                layoutParams.height = ((viewHeight * interpolatedTime).toInt())
                requestLayout()
            }
        }
    }

    private inline fun animate(
        view: View,
        crossinline block: (view: View, interpolatedTime: Float, viewHeight: Int) -> Unit
    ) {
        with(view) {
            // Remember initial view height before changing it.
            val viewHeight = measuredHeight
            val density = context.resources.displayMetrics.density
            startAnimation(
                object : Animation() {
                    override fun applyTransformation(
                        interpolatedTime: Float,
                        transformation: Transformation
                    ) {
                        block(view, interpolatedTime, viewHeight)
                    }
                }.apply<Animation> {
                    interpolator = FastOutSlowInInterpolator()
                    duration = (viewHeight / density).toLong() + 150
                }
            )
        }

    }

}