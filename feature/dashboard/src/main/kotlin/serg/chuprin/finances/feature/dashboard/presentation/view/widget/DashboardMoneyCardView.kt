package serg.chuprin.finances.feature.dashboard.presentation.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.view_dashboard_money_card.view.*
import serg.chuprin.finances.core.api.presentation.view.extensions.getDimenDpFloat
import serg.chuprin.finances.feature.dashboard.R

/**
 * Created by Sergey Chuprin on 17.04.2020.
 */
class DashboardMoneyCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_dashboard_money_card, this)
        titleTextView.text = attrs?.let(::getTitleText)

        radius = context.getDimenDpFloat(R.dimen.radiusCornerBig)
        cardElevation = context.getDimenDpFloat(R.dimen.elevationCardView)
        isClickable = true
        isFocusable = true
    }

    fun setAmountText(text: String) {
        amountTextView.text = text
    }

    private fun getTitleText(attributeSet: AttributeSet): String {
        return context.obtainStyledAttributes(
            attributeSet,
            R.styleable.DashboardMoneyCardView,
            0,
            0
        ).run {
            getString(R.styleable.DashboardMoneyCardView_titleText).orEmpty().also { recycle() }
        }
    }

}