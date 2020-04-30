package serg.chuprin.finances.core.api.presentation.view.adapter.dsl

import androidx.recyclerview.widget.RecyclerView
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.context.RecyclerViewAdapterContext

/**
 * Created by Sergey Chuprin on 29.04.2020.
 */
fun RecyclerView.setup(
    diffCallback: DiffCallback<BaseCell> = DiffCallback(),
    block: RecyclerViewAdapterContext.() -> Unit
): DiffMultiViewAdapter<BaseCell> {
    return DiffMultiViewAdapter(diffCallback).apply {
        RecyclerViewAdapterContext(this).apply(block)
        this@setup.adapter = this
    }
}

fun RecyclerView.setup(
    adapter: DiffMultiViewAdapter<BaseCell>,
    block: RecyclerViewAdapterContext.() -> Unit
): DiffMultiViewAdapter<BaseCell> {
    return adapter.apply {
        RecyclerViewAdapterContext(this).apply(block)
        this@setup.adapter = this
    }
}