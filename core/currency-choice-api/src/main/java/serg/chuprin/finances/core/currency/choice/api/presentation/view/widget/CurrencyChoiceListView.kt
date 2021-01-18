package serg.chuprin.finances.core.currency.choice.api.presentation.view.widget

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
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.core.currency.choice.api.R
import serg.chuprin.finances.core.currency.choice.api.presentation.model.cells.CurrencyCell
import serg.chuprin.finances.core.currency.choice.api.presentation.view.adapter.renderer.CurrencyCellRenderer

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

    private val cellsAdapter = DiffMultiViewAdapter(DiffCallback()).apply {
        registerRenderer(CurrencyCellRenderer())
        registerRenderer(ZeroDataCellRenderer())
        clickListener = { cell, _, _ ->
            hideKeyboard()
            if (cell is CurrencyCell) {
                onCurrencyCellChosen.invoke(cell)
            }
        }
    }

    init {
        View.inflate(context, R.layout.view_currency_choice, this)
        with(recyclerView) {
            setHasFixedSize(true)
            adapter = cellsAdapter
            layoutManager = LinearLayoutManager(context)
            // Fading edge is not working from XML.
            isVerticalFadingEdgeEnabled = true
            setFadingEdgeLength(context.dpToPx(16))

            onScrollStateChanged { _, newState ->
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hideKeyboard()
                }
            }
        }
        background = ColorDrawable(context.getBackgroundColor())
        searchEditText.doAfterTextChanged { editable ->
            if (!searchEditText.shouldIgnoreChanges) {
                editable?.toString()?.let(onSearchQueryChanged)
            }
        }
        closeImageView.onClick {
            hideKeyboard()
            onCloseClicked.invoke()
        }
    }

    fun setCells(cells: List<BaseCell>) = cellsAdapter.setItems(cells)

    fun resetScroll() = recyclerView.layoutManager!!.scrollToPosition(0)

    fun clearSearchQuery() {
        if (!searchEditText.text.isNullOrEmpty()) {
            searchEditText.doIgnoringChanges {
                setText(EMPTY_STRING)
            }
        }
    }

    fun showKeyboard() = searchEditText.showKeyboard()

    private fun hideKeyboard() {
        with(searchEditText) {
            if (isFocused) {
                hideKeyboard()
            }
        }
    }

}