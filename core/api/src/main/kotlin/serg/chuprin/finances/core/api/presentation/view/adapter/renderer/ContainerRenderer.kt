package serg.chuprin.finances.core.api.presentation.view.adapter.renderer

import serg.chuprin.adapter.Click
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.adapter.ContainerRenderer
import serg.chuprin.adapter.LongClick
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
abstract class ContainerRenderer<C : BaseCell> : ContainerRenderer<C>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun bindView(viewHolder: ContainerHolder, cell: C) {
        super.bindView(viewHolder, cell)
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun bindView(viewHolder: ContainerHolder, cell: C, payloads: MutableList<Any>) {
        super.bindView(viewHolder, cell, payloads)
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun onVhCreated(
        viewHolder: ContainerHolder,
        clickListener: Click?,
        longClickListener: LongClick?
    ) {
        super.onVhCreated(viewHolder, clickListener, longClickListener)
    }

}