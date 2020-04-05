package serg.chuprin.finances.feature.onboarding.presentation.view.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.view_currency_choice.view.*
import serg.chuprin.adapter.TypedMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.core.api.presentation.view.extensions.getBackgroundColor
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.model.cells.CurrencyCell
import serg.chuprin.finances.feature.onboarding.presentation.view.adapter.renderer.CurrencyCellRenderer

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
class CurrencyChoiceListCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var callback: ((cell: CurrencyCell) -> Unit)? = null

    private val view = View.inflate(context, R.layout.view_currency_choice, this)
    private val cellsAdapter = TypedMultiViewAdapter<CurrencyCell>().apply {
        registerRenderer(CurrencyCellRenderer())
        clickListener = { cell, _, _ -> callback?.invoke(cell) }
    }

    init {
        with(view.recyclerView) {
            setHasFixedSize(true)
            adapter = cellsAdapter
            layoutManager = LinearLayoutManager(context)
            // Fading edge is not working from XML.
            isVerticalFadingEdgeEnabled = true
            setFadingEdgeLength(context.dpToPx(16))
        }
        orientation = VERTICAL
        background = ColorDrawable(context.getBackgroundColor())
    }

    fun setCells(cells: List<CurrencyCell>) = cellsAdapter.setItems(cells)

}