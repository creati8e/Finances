package serg.chuprin.finances.core.api.presentation.view.adapter.dsl

import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.context.RecyclerViewRendererContext

/**
 * Created by Sergey Chuprin on 29.04.2020.
 */
open class RecyclerViewBlockRenderer<T : BaseCell>(
    override val type: Int,
    private val context: RecyclerViewRendererContext<T>
) : ContainerRenderer<T>() {

    private companion object {
        private val emptyPayload = mutableListOf<Any>()
    }

    override fun bindView(holder: ContainerHolder, model: T) {
        context.onBind?.invoke(holder, model, emptyPayload)
    }

    override fun bindView(holder: ContainerHolder, model: T, payloads: MutableList<Any>) {
        context.onBind?.invoke(holder, model, payloads)
    }

    override fun onVhCreated(
        holder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        context.onViewHolderCreated.invoke(holder)
    }

}