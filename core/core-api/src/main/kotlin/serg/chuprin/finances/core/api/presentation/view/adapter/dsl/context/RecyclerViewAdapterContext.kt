package serg.chuprin.finances.core.api.presentation.view.adapter.dsl.context

import androidx.annotation.LayoutRes
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.RecyclerViewBlockRenderer
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.marker.RecyclerViewAdapterContextDslMarker

/**
 * Created by Sergey Chuprin on 29.04.2020.
 */
@RecyclerViewAdapterContextDslMarker
open class RecyclerViewAdapterContext(
    open val adapter: DiffMultiViewAdapter<BaseCell>
) {

    /**
     * Adds cell of type [T] to adapter and provides context to setting it up.
     * @param T - type of cell
     * @param layoutRes - layout resource for this cell.
     */
    inline fun <reified T : BaseCell> add(
        @LayoutRes layoutRes: Int,
        block: RecyclerViewRendererContext<T>.() -> Unit
    ) {
        adapter.registerRenderer(
            RecyclerViewBlockRenderer(
                type = layoutRes,
                context = RecyclerViewRendererContext(adapter, T::class.java).apply(block)
            )
        )
    }

    /**
     * Adds cell of type [T] to adapter and provides context to setting it up.
     * @param T - type of cell
     */
    inline fun <reified T : BaseCell> add(@LayoutRes layoutRes: Int) {
        adapter.registerRenderer(
            RecyclerViewBlockRenderer(
                type = layoutRes,
                context = RecyclerViewRendererContext(adapter, T::class.java)
            )
        )
    }

}