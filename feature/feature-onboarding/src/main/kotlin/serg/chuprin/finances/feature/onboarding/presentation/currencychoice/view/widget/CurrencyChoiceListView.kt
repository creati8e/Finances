package serg.chuprin.finances.feature.onboarding.presentation.currencychoice.view.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_currency_choice.view.*
import serg.chuprin.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.core.api.presentation.view.extensions.getBackgroundColor
import serg.chuprin.finances.core.api.presentation.view.extensions.hideKeyboard
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.model.cells.CurrencyCell
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.view.adapter.renderer.CurrencyCellRenderer

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
class CurrencyChoiceListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    lateinit var onCloseClicked: () -> Unit
    lateinit var onSearchQueryChanged: (String) -> Unit
    lateinit var onCurrencyCellChosen: (cell: CurrencyCell) -> Unit

    private val view = View.inflate(context, R.layout.view_currency_choice, this)
    private val cellsAdapter = DiffMultiViewAdapter(DiffCallback<CurrencyCell>()).apply {
        registerRenderer(CurrencyCellRenderer())
        clickListener = { cell, _, _ ->
            hideKeyboard()
            onCurrencyCellChosen.invoke(cell)
        }
    }

    init {
        with(view.recyclerView) {
            setHasFixedSize(true)
            adapter = cellsAdapter
            layoutManager = LinearLayoutManager(context)
            // Fading edge is not working from XML.
            isVerticalFadingEdgeEnabled = true
            setFadingEdgeLength(context.dpToPx(16))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        hideKeyboard()
                    }
                }
            })
        }
        background = ColorDrawable(context.getBackgroundColor())
        view.searchEditText.doAfterTextChanged { editable ->
            editable?.toString()?.let(onSearchQueryChanged)
        }
        view.closeImageView.onClick {
            hideKeyboard()
            onCloseClicked.invoke()
        }
    }

    fun setCells(cells: List<CurrencyCell>) = cellsAdapter.setItems(cells)

    private fun hideKeyboard() {
        with(view.searchEditText) {
            if (isFocused) {
                hideKeyboard()
            }
        }
    }

}