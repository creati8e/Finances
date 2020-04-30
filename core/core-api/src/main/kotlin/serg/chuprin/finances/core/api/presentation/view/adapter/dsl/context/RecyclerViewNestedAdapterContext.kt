package serg.chuprin.finances.core.api.presentation.view.adapter.dsl.context

import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.marker.RecyclerViewNestedAdapterContextDslMarker

/**
 * Created by Sergey Chuprin on 29.04.2020.
 */
@RecyclerViewNestedAdapterContextDslMarker
class RecyclerViewNestedAdapterContext(
    adapter: DiffMultiViewAdapter<BaseCell>,
    private val containerHolder: ContainerHolder
) : RecyclerViewAdapterContext(adapter) {

    /**
     * This method is useful when you need to setup recycler view after
     * setting up adapter, layout manager and etc.
     */
    fun setupRecyclerView(block: ContainerHolder.() -> Unit) {
        containerHolder.apply(block)
    }

}